/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.SettingDB;
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
import model.Setting;

/**
 *
 * @author 03623
 */
public class SettingController extends HttpServlet {

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
            out.println("<title>Servlet SettingController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SettingController at " + request.getContextPath() + "</h1>");
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
        String uSuccess = (String) session.getAttribute("update_success");
        String aSuccess = (String) session.getAttribute("add_success");
        session.removeAttribute("update_success");
        session.removeAttribute("add_success");
        if(uSuccess != null) request.setAttribute("success", "uSuccess");
        if(aSuccess != null) request.setAttribute("success", "aSuccess");
        if (tag == null) {
            String id_raw = request.getParameter("id");
            String type = request.getParameter("type");
            String status = request.getParameter("status");
            String name = request.getParameter("name");
            List<Setting> list;
            String xpage = request.getParameter("page");
            SettingDB s = new SettingDB();
            int page, size, limit = 5, start, end;                     //paging support
            if (xpage == null) {
                page = 1;
            } else {
                page = Integer.parseInt(xpage);
            }
            /////////////////////////////////////////
            if (type == null || status == null || name == null) {
                list = s.Search("", "", "");
            } else {
                list = s.Search(type, status, name);                  //search and display
            }        /////////////////////////////////////////                 //paging
            size = list.size();
            int numpage = size / limit + (size % limit == 0 ? 0 : 1);
            start = (page - 1) * limit;
            if (page * limit > size) {
                end = size;
            } else {
                end = page * limit;
            }
            if (id_raw != "") {                                            //update status
                try {
                    int id = Integer.parseInt(id_raw);
                    Setting setting = s.getSettingById(id);                         //change status of setting
                    setting.setStatus(!setting.isStatus());                         //
                    s.updateSetting(setting);                    
                    String url = (String) session.getAttribute("pre_url");
                    response.sendRedirect(url);
                    return;
                } catch (Exception e) {

                }
            }
            List<String> listTypeID = s.getListSettingType();                        // <select> data
            LinkedHashMap<String, String> listType = new LinkedHashMap<>();
            ServletContext sc = getServletContext();
            for (String i : listTypeID) {
                listType.put(i, sc.getInitParameter(i));
            }

            request.setAttribute("settingType", listType);
            request.setAttribute("selectedType", type);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("listSetting", s.getListByPage(list, start, end));
            request.setAttribute("page", page);
            request.setAttribute("numpage", numpage);
            request.getRequestDispatcher("setting/SettingList.jsp").forward(request, response);
        } else if (tag.equals("add")) {
            SettingDB sdb = new SettingDB();
            List<String> listTypeID = sdb.getListSettingType();
            LinkedHashMap<String, String> listType = new LinkedHashMap<>();
            ServletContext sc = getServletContext();
            for (String i : listTypeID) {
                listType.put(i, sc.getInitParameter(i));
            }
            request.setAttribute("listType", listType);
            request.setAttribute("tag", tag);
            request.getRequestDispatcher("setting/SettingDetail.jsp").forward(request, response);
        } else if (tag.equals("update")) {
            String id = request.getParameter("id");
            SettingDB sdb = new SettingDB();
            Setting s = sdb.getSettingById(Integer.parseInt(id));
            List<String> listTypeID = sdb.getListSettingType();
            LinkedHashMap<String, String> listType = new LinkedHashMap<>();
            ServletContext sc = getServletContext();
            for (String i : listTypeID) {
                listType.put(i, sc.getInitParameter(i));
            }
            request.setAttribute("listType", listType);
            request.setAttribute("setting", s);
            request.setAttribute("id", id);
            request.setAttribute("tag", tag);
            request.getRequestDispatcher("setting/SettingDetail.jsp").forward(request, response);
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
        String typeId = request.getParameter("typeId");
        String displayOrder = request.getParameter("displayOrder");
        String settingValue_raw = request.getParameter("settingValue");
        String settingTitle_raw = request.getParameter("settingTitle");
        LogicalProcess lp = new LogicalProcess();
        String settingTitle = lp.filterString(settingTitle_raw);
        String settingValue = lp.filterString(settingValue_raw);
        String status_raw = request.getParameter("status");
        String note = request.getParameter("note");
        SettingDB sdb = new SettingDB();
        boolean status = status_raw.equals("1");
        HttpSession session = request.getSession();
        if (tag.equals("update")) {
            String settingId = request.getParameter("settingId");

            Setting s = new Setting(Integer.parseInt(settingId), Integer.parseInt(typeId),settingTitle,
                    settingValue, Integer.parseInt(displayOrder), note, status);
            sdb.updateSetting(s);
            session.setAttribute("update_success", "update_success");
            String url = (String) session.getAttribute("pre_url");
            response.sendRedirect(url);
        }
        if (tag.equals("add")) {
            Setting s = new Setting(0, Integer.parseInt(typeId),settingTitle,
                    settingValue, Integer.parseInt(displayOrder), note, status);
            sdb.addSetting(s);
            session.setAttribute("add_success", "add_success");
            response.sendRedirect("setting");
        }
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
