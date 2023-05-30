/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.SubjectDB;
import dal.SubjectSettingDB;
import extension.LogicalProcess;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Subject;
import model.SubjectSetting;
import model.User;

/**
 *
 * @author KHANHHERE
 */
public class SubjectSettingController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SubjectSettingController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SubjectSettingController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String tag = request.getParameter("tag");
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("useraccount");
        String uSuccess = (String) session.getAttribute("update_success");
        String aSuccess = (String) session.getAttribute("add_success");
        session.removeAttribute("update_success");
        session.removeAttribute("add_success");
        if (uSuccess != null) {
            request.setAttribute("success", "uSuccess");
        }
        if (aSuccess != null) {
            request.setAttribute("success", "aSuccess");
        }
        if (tag == null) {
            String id = request.getParameter("id");
            SubjectSettingDB ssdb = new SubjectSettingDB();
            if (id != null) {
                String user_id = null;
                if (u.getRoleId().equalsIgnoreCase("admin")) {
                    user_id = "";
                }
                if (u.getRoleId().equalsIgnoreCase("author")) {
                    user_id = u.getUserId() + "";
                }
                SubjectSetting ss = ssdb.getSSById(id, user_id);
                if (ss == null) {
                    response.sendRedirect("subjectsetting");
                } else {
                    ss.setStatus(!ss.isStatus());
                    ssdb.updateSubjectSetting(ss);
                    String url = (String) session.getAttribute("pre_url");
                    response.sendRedirect(url);
                }
                return;
            }

            String search_type = request.getParameter("search_type");
            String search_status = request.getParameter("search_status");
            String search_value = request.getParameter("search_value");
            if (search_type == null) {
                search_type = "";
            }
            if (search_status == null) {
                search_status = "";
            }
            if (search_value == null) {
                search_value = "";
            }
            String author = null;
            if (u.getRoleId().equalsIgnoreCase("admin")) {
                author = "";
            }
            if (u.getRoleId().equalsIgnoreCase("author")) {
                author = u.getUserId() + "";
            }
            int size = ssdb.count(search_type, search_status, search_value, author);
            int curPage, numPerPage = 4;
            int NoPage = (size % numPerPage == 0 ? (size / numPerPage) : (size / numPerPage + 1));
            String curPage_raw = request.getParameter("curPage");
            if (curPage_raw == null) {
                curPage = 1;
            } else {
                curPage = Integer.parseInt(curPage_raw);
            }
            List<SubjectSetting> listPerPage = ssdb.search(search_type, search_status, search_value, author, curPage, numPerPage);

            List<String> listTypeID = ssdb.getListType();
            LinkedHashMap<String, String> listType = new LinkedHashMap<>();
            ServletContext sc = getServletContext();
            for (String i : listTypeID) {
                listType.put(i, sc.getInitParameter(i));
            }

            request.setAttribute("curPage", curPage);
            request.setAttribute("listPerPage", listPerPage);
            request.setAttribute("NoPage", NoPage);
            request.setAttribute("search_type", search_type);
            request.setAttribute("search_status", search_status);
            request.setAttribute("search_value", search_value);
            request.setAttribute("listType", listType);
            request.getRequestDispatcher("subject_setting/SubjectSettingList.jsp").forward(request, response);
        } else if (tag.equals("add")) {
            SubjectSettingDB ssdb = new SubjectSettingDB();
            String author = null;
            if (u.getRoleId().equalsIgnoreCase("admin")) {
                author = "";
            }
            if (u.getRoleId().equalsIgnoreCase("author")) {
                author = u.getUserId() + "";
            }
            List<Subject> listS = ssdb.getAllSubject(author);
            List<String> listTypeID = ssdb.getListType();
            LinkedHashMap<String, String> listType = new LinkedHashMap<>();
            ServletContext sc = getServletContext();
            for (String i : listTypeID) {
                listType.put(i, sc.getInitParameter(i));
            }
            request.setAttribute("listS", listS);
            request.setAttribute("listType", listType);
            request.setAttribute("tag", tag);
            request.getRequestDispatcher("subject_setting/SubjectSettingDetail.jsp").forward(request, response);
        } else if (tag.equals("update")) {
            String id = request.getParameter("id");
            SubjectSettingDB ssdb = new SubjectSettingDB();
            String user_id = null;
            if (u.getRoleId().equalsIgnoreCase("admin")) {
                user_id = "";
            }
            if (u.getRoleId().equalsIgnoreCase("author")) {
                user_id = u.getUserId() + "";
            }
            SubjectSetting ss = ssdb.getSSById(id, user_id);
            if (ss == null) {
                response.sendRedirect("subjectsetting");
            } else {
                ServletContext sc = getServletContext();
                String ssType = sc.getInitParameter(ss.getTypeId() + "");
                SubjectDB sdb = new SubjectDB();
                Subject s = sdb.getSubjectById(ss.getSubjectId() + "");
                request.setAttribute("ss", ss);
                request.setAttribute("ssType", ssType);
                request.setAttribute("tag", tag);
                request.setAttribute("subject", s);
                request.getRequestDispatcher("subject_setting/SubjectSettingDetail.jsp").forward(request, response);
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
        String tag = request.getParameter("tag");
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("useraccount");
        String subjectId = request.getParameter("subjectId");
        String typeId = request.getParameter("typeId");
        String settingValue_raw = request.getParameter("settingValue");
        String title_raw = request.getParameter("settingTitle");
        LogicalProcess lp = new LogicalProcess();
        String settingTitle = lp.filterString(title_raw);
        String settingValue = lp.filterString(settingValue_raw);
        String displayOrder = request.getParameter("displayOrder");
        String status_raw = request.getParameter("status");
        boolean status = status_raw.equals("1");

        SubjectSetting ss = new SubjectSetting();
        ss.setSubjectId(Integer.parseInt(subjectId));
        ss.setTypeId(Integer.parseInt(typeId));
        ss.setSettingTitle(settingTitle);
        ss.setSettingValue(settingValue);
        ss.setDisplayOrder(Integer.parseInt(displayOrder));
        ss.setStatus(status);

        SubjectSettingDB ssdb = new SubjectSettingDB();
        String url = (String) session.getAttribute("pre_url");
        if (tag.equals("update")) {
            String id = request.getParameter("id");
            ss.setSettingId(Integer.parseInt(id));
            ssdb.updateSubjectSetting(ss);
            session.setAttribute("update_success", "update_success");
        }
        if (tag.equals("add")) {
            ssdb.addSubjectSetting(ss);
            session.setAttribute("add_success", "add_success");
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
