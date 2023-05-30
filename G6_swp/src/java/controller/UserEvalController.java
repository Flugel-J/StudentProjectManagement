/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.ClassDB;
import dal.EvalCriteriaDB;
import dal.IterEvalDB;
import dal.IterationDB;
import dal.MemEvalDB;
import dal.TeamDB;
import dal.TeamEvalDB;
import dal.UserDB;
import dal.UserEvalDB;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Iteration;
import model.Class;
import model.EvalCriteria;
import model.IterEval;
import model.MemEval;
import model.Team;
import model.User;
import model.UserEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@MultipartConfig(
        maxFileSize = 1024 * 1024 * 15 // 15 MB
)
/**
 *
 * @author KHANHHERE
 */
public class UserEvalController extends HttpServlet {

    // Get cell value
    private Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }
        return cellValue;
    }

    private List<UserEval> readExcel(InputStream inputStream, String import_class, String import_iter)
            throws IOException {
        ClassDB cdb = new ClassDB();
        TeamDB tdb = new TeamDB();
        IterationDB idb = new IterationDB();
        IterEvalDB iedb = new IterEvalDB();
        EvalCriteriaDB ecdb = new EvalCriteriaDB();
        UserDB udb = new UserDB();

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        // Get all rows
        Iterator<Row> iterator = sheet.iterator();

        List<UserEval> list = new ArrayList<>();
        List<EvalCriteria> listEC = ecdb.getECOfIter(import_iter);
        int noEC = listEC.size();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }
            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            IterEval ie = new IterEval();
            ie.setClassObj(cdb.getClassById(import_class));
            ie.setIteration(idb.getIterationByID(Integer.parseInt(import_iter)));

            List<MemEval> listME = new ArrayList<>();

            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                String value;
                if (cellValue == null) {
                    value = null;
                } else {
                    value = cellValue.toString();
                }
                // Set value
                int columnIndex = cell.getColumnIndex();
//                System.out.println(columnIndex + "----" + value);
                for (int i = 4; i < 4 + noEC; i++) {
                    if (columnIndex == i) {
                        if (value != null && !value.isEmpty()) {
                            try {
                                double grade = Double.parseDouble(value);
                                int stt = columnIndex - 4;
                                MemEval me = new MemEval(0, null, listEC.get(stt), 0, grade, null, true);
                                listME.add(me);
                            } catch (NumberFormatException e) {
                                System.out.println("MemEval error");
                            }
                        }
                    }
                }
                if (columnIndex == 4 + noEC) {
                    try {
                        double bonus = Double.parseDouble(value);
                        ie.setBonus(bonus);
                    } catch (Exception e) {
                    }
                }
                if (columnIndex == 6 + noEC) {
                    String note = value;
                    ie.setNote(note);
                }
                if (columnIndex == 7 + noEC) {
                    try {
                        double userId = Double.parseDouble(value);
                        ie.setUser(udb.getUser((int) userId));
                    } catch (Exception e) {
                        System.out.println("userId error");
                    }
                }
                if (columnIndex == 8 + noEC) {
                    String teamId = value;
                    ie.setTeam(tdb.getTeam(teamId));
                }

            }
            int checkIE = iedb.isIEExist(ie);
            ie.setId(checkIE);
            for (MemEval mee : listME) {
                mee.setIterEval(ie);
            }
            list.add(new UserEval(ie, listME));
        }
//        System.out.println("--------------------------");
//        System.out.println(list.get(0).getIe().getId());
//        System.out.println(list.get(1).getMe().get(0).getEvalCriteria().getEvalTitle());
//        System.out.println("--------------------------");
        return list;
    }

    private void exportData(HttpServletResponse response, List<UserEval> listExport, List<EvalCriteria> listEC) throws IOException {
        MemEvalDB medb = new MemEvalDB();
        IterEvalDB iedb = new IterEvalDB();
        int noEC = listEC.size();
        DecimalFormat df = new DecimalFormat("#.##");
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            // create excel
            XSSFSheet sheet = wb.createSheet();
            sheet.setColumnWidth(0, 256 * 12);
            sheet.setColumnWidth(1, 256 * 20);
            sheet.setColumnWidth(3, 256 * 14);
            for (int i = 4; i < 4 + noEC; i++) {
                sheet.setColumnWidth(i, 256 * 23);
            }
            sheet.setColumnWidth(5 + noEC, 256 * 14);
            sheet.setColumnWidth(6 + noEC, 256 * 25);
            sheet.setColumnHidden(7 + noEC, true);
            sheet.setColumnHidden(8 + noEC, true);
            // font style
            XSSFFont font = wb.createFont();
            font.setBold(true);
            font.setFontName("Arial");
            //cell style
            XSSFCellStyle cs = wb.createCellStyle();
            cs.setFont(font);
            XSSFCellStyle csValue = wb.createCellStyle();
            csValue.setAlignment(HorizontalAlignment.LEFT);
            // 1. header
            int rowNo = 0;
            Row row = sheet.createRow(rowNo++);
            int cellNum = 0;
            Cell cell = row.createCell(cellNum++);
            cell.setCellValue("Roll Number");
            cell.setCellStyle(cs);
            cell = row.createCell(cellNum++);
            cell.setCellValue("Username");
            cell.setCellStyle(cs);
            cell = row.createCell(cellNum++);
            cell.setCellValue("Team");
            cell.setCellStyle(cs);
            cell = row.createCell(cellNum++);
            cell.setCellValue("Team Grade");
            cell.setCellStyle(cs);
            for (EvalCriteria ec : listEC) {
                cell = row.createCell(cellNum++);
                cell.setCellValue(ec.getEvalTitle() + " (" + ec.getEvalWeight() + "%)");
                cell.setCellStyle(cs);
            }
            cell = row.createCell(cellNum++);
            cell.setCellValue("Bonus");
            cell.setCellStyle(cs);
            cell = row.createCell(cellNum++);
            cell.setCellValue("Iteration Grade");
            cell.setCellStyle(cs);
            cell = row.createCell(cellNum++);
            cell.setCellValue("Note");
            cell.setCellStyle(cs);
            cell = row.createCell(cellNum++);
            cell.setCellValue("UserId");
            cell.setCellStyle(cs);
            cell = row.createCell(cellNum++);
            cell.setCellValue("TeamId");
            cell.setCellStyle(cs);
            // 2. Value
            for (UserEval ue : listExport) {
                cellNum = 0;
                row = sheet.createRow(rowNo++);
                cell = row.createCell(cellNum++);
                cell.setCellValue(ue.getIe().getUser().getRollNumber());
                cell.setCellStyle(csValue);
                cell = row.createCell(cellNum++);
                cell.setCellValue(ue.getIe().getUser().getFullName());
                cell.setCellStyle(csValue);
                cell = row.createCell(cellNum++);
                cell.setCellValue(ue.getIe().getTeam().getTeamCode());
                cell.setCellStyle(csValue);
                cell = row.createCell(cellNum++);
                double teamGrade = iedb.teamGradeOfIE(ue.getIe());
                if (teamGrade == -1) {
                    cell.setCellValue("");
                } else {
                    double gradeEx = teamGrade / iedb.weightOfTeamGradeOfIE(ue.getIe()) * 100;
                    cell.setCellValue(Double.parseDouble(df.format(gradeEx)));
                }
                cell.setCellStyle(csValue);
                for (EvalCriteria ec : listEC) {
                    cell = row.createCell(cellNum++);
                    double grade = medb.getGrade(ue.getIe().getId() + "", ec.getCriteriaId() + "");
                    if (grade == -1) {
                        cell.setCellValue("");
                    } else {
                        cell.setCellValue(grade);
                    }
                    cell.setCellStyle(csValue);
                }
                cell = row.createCell(cellNum++);
                cell.setCellValue(ue.getIe().getBonus());
                cell.setCellStyle(csValue);
                cell = row.createCell(cellNum++);
                double iterGrade = ue.getIe().getGrade() + ue.getIe().getBonus() > 10 ? 10 : ue.getIe().getGrade() + ue.getIe().getBonus();
                cell.setCellValue(iterGrade);
                cell.setCellStyle(csValue);
                cell = row.createCell(cellNum++);
                cell.setCellValue(ue.getIe().getNote());
                cell.setCellStyle(csValue);
                cell = row.createCell(cellNum++);
                cell.setCellValue(ue.getIe().getUser().getUserId());
                cell.setCellStyle(csValue);
                cell = row.createCell(cellNum++);
                cell.setCellValue(ue.getIe().getTeam().getTeamId());
                cell.setCellStyle(csValue);
            }
            // end excel
            wb.write(response.getOutputStream());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String tag = request.getParameter("tag");
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("useraccount");
        String url = (String) session.getAttribute("pre_url");
        String uSuccess = (String) session.getAttribute("update_success");
        String iSuccess = (String) session.getAttribute("import_success");
        session.removeAttribute("update_success");
        session.removeAttribute("import_success");
        if (uSuccess != null) {
            request.setAttribute("success", "uSuccess");
        }
        if (iSuccess != null) {
            request.setAttribute("success", "iSuccess");
        }
        UserEvalDB uedb = new UserEvalDB();
        ClassDB cdb = new ClassDB();
        TeamDB tdb = new TeamDB();
        IterationDB idb = new IterationDB();
        IterEvalDB iedb = new IterEvalDB();
        EvalCriteriaDB ecdb = new EvalCriteriaDB();
        String stu = "", tra = "";
        if (u.getRoleId().equalsIgnoreCase("student")) {
            stu = u.getUserId() + "";
        } else {
            tra = u.getUserId() + "";
        }

        String search_class = request.getParameter("search_class");
        String search_team = request.getParameter("search_team");
        String search_iter = request.getParameter("search_iter");
        String search_value = request.getParameter("search_value");
        List<Class> listClass = cdb.getListClass(stu, tra);
        if (search_class == null) {
            search_class = listClass.get(0).getClassId() + "";
        }
        List<Team> listTeam = tdb.getListTeamOfClass(search_class, stu);
        if (search_team == null) {
            search_team = "";
        }
        List<Iteration> listIter = idb.getListIterOfClass(search_class);
        if (search_iter == null) {
            search_iter = listIter.get(0).getIterationId() + "";
        }

        if (search_value == null) {
            search_value = "";
        }
        if (tag == null) {
            int size = uedb.count(search_class, search_team, search_iter, search_value,
                    stu, tra);
            int curPage, numPerPage = 4;
            int NoPage = (size % numPerPage == 0 ? (size / numPerPage) : (size / numPerPage + 1));
            if (NoPage == 0) {
                NoPage = 1;
            }
            String curPage_raw = request.getParameter("curPage");
            if (curPage_raw == null) {
                curPage = 1;
            } else {
                curPage = Integer.parseInt(curPage_raw);
            }
            List<UserEval> listPerPage = uedb.search(search_class, search_team, search_iter, search_value,
                    stu, tra, curPage, numPerPage);

            request.setAttribute("curPage", curPage);
            request.setAttribute("listPerPage", listPerPage);
            request.setAttribute("NoPage", NoPage);
            request.setAttribute("search_class", search_class);
            request.setAttribute("search_team", search_team);
            request.setAttribute("search_iter", search_iter);
            request.setAttribute("search_value", search_value);
            request.setAttribute("listTeam", listTeam);
            request.setAttribute("listClass", listClass);
            request.setAttribute("listIter", listIter);

            request.getRequestDispatcher("evaluation/UserEvaluation.jsp").forward(request, response);
        } else {
            if (tag.equals("details")) {
                IterEval ie = iedb.getIEById(id);
                request.setAttribute("ie", ie);
                request.setAttribute("tag", tag);
                request.getRequestDispatcher("evaluation/UEDetails.jsp").forward(request, response);
            }
            if (tag.equals("export")) {
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=UserEval "
                        + cdb.getClassById(search_class).getClassCode() + "-"
                        + idb.getIterationByID(Integer.parseInt(search_iter)).getIterName() + ".xlsx");
                List<UserEval> listExport = uedb.search(search_class, search_team, search_iter, search_value,
                        stu, tra, 0, 0);
                List<EvalCriteria> listEC = ecdb.getECOfIter(search_iter);
                exportData(response, listExport, listEC);
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String url = (String) session.getAttribute("pre_url");
        IterEvalDB iedb = new IterEvalDB();
        UserEvalDB uedb = new UserEvalDB();
        String id = request.getParameter("id");
        String bonus = request.getParameter("bonus");
        String note_raw = request.getParameter("note");
        String tag = request.getParameter("tag");
        if (tag == null) {
            tag = "";
        }
        String note;
        if (note_raw != null) {
            note = new extension.LogicalProcess().filterString(note_raw);
        } else {
            note = null;
        }
        if (tag.equals("details")) {
            IterEval ie = iedb.getIEById(id);
            ie.setNote(note);
            ie.setBonus(Double.parseDouble(bonus));
            iedb.update(ie);
            session.setAttribute("update_success", "update_success");
        }

        String function = request.getParameter("function");
        if (function == null) {
            function = "";
        }
        String import_class = request.getParameter("import_class");
        String import_iter = request.getParameter("import_iter");

        if (function.equals("import")) {
            PrintWriter out = response.getWriter();
            Part fileimport = request.getPart("fileImport");
            if (fileimport.getSize() > 0) {
                InputStream is = fileimport.getInputStream();
                List<UserEval> listUE = readExcel(is, import_class, import_iter);
                uedb.importData(listUE);
                session.setAttribute("import_success", "import_success");
            }
        }

        response.sendRedirect(url);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
