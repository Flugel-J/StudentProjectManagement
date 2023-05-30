/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.SubjectDB;
import extension.LogicalProcess;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Subject;

/**
 *
 * @author admin
 */
public class SubjectController extends HttpServlet {

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
            out.println("<title>Servlet SubjectListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SubjectListController at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("a");
        HttpSession session = request.getSession();
        String uSuccess = (String) session.getAttribute("update_success");
        String aSuccess = (String) session.getAttribute("add_success");
        session.removeAttribute("update_success");
        session.removeAttribute("add_success");
        if(uSuccess != null) request.setAttribute("success", "uSuccess");
        if(aSuccess != null) request.setAttribute("success", "aSuccess");
        if (action != null) {
            SubjectDB s = new SubjectDB();
            if (action.equals("add")) {                                                           ///add subject
                request.setAttribute("authorList", s.AuthorList());
                request.setAttribute("action", "add new subject");
                request.getRequestDispatcher("subject/Detail.jsp").forward(request, response);
            } else if (action.equals("update")) {                                                ///update subject

                String id = request.getParameter("id");
                request.setAttribute("Subject", s.getSubjectById(id));
                request.setAttribute("authorList", s.AuthorList());
                request.setAttribute("action", "update subject");
                request.getRequestDispatcher("subject/Detail.jsp").forward(request, response);
            }
        } 
        else {
            String id = request.getParameter("id");
            String code = request.getParameter("code");
            String status = request.getParameter("status");
            String name = request.getParameter("name");
            String xpage = request.getParameter("page");
            SubjectDB sdb = new SubjectDB();
            if (id != null) {                                            //update status
                Subject sub = sdb.getSubjectById(id);                         //change status of setting
                sub.setStatus(!sub.isStatus());                         //
                sdb.changeStatus(sub);
                String url = (String) session.getAttribute("pre_url");
                session.setAttribute("update_success", "update_success");
                response.sendRedirect(url);
                return;
            }
            int page, size, limit = 5, start, end;                     //paging support
            if (xpage == null) {
                page = 1;
            } else {
                page = Integer.parseInt(xpage);
            }
            
            List<Subject> listSubject;
            if (code == null || status == null || name == null) {
                listSubject = sdb.Search("", "", "");
            } else {
                listSubject = sdb.Search(code, status, name);                  //search and display
            }        /////////////////////////////////////////                 //paging
            size = listSubject.size();
            int numpage = size / limit + (size % limit == 0 ? 0 : 1);
            start = (page - 1) * limit;
            if (page * limit > size) {
                end = size;
            } else {
                end = page * limit;
            }

            request.setAttribute("selectedCode", code);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("selectedName", name);
            request.setAttribute("listSubject", sdb.getListByPage(listSubject, start, end));
            request.setAttribute("page", page);
            request.setAttribute("numpage", numpage);
            request.getRequestDispatcher("subject/Subject.jsp").forward(request, response);
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
        String id = request.getParameter("id");
        String code_raw = request.getParameter("code");
        String name_raw = request.getParameter("name");
        LogicalProcess lp = new LogicalProcess();
        String code = lp.filterString(code_raw);
        String name = lp.filterString(name_raw);
        String status = request.getParameter("status");
        String author = request.getParameter("author");
        SubjectDB sdb = new SubjectDB();
         HttpSession session = request.getSession();
        if (sdb.getSubjectById(id) != null) {
            
          sdb.updateSubject(code, name, author, status, id);
           session.setAttribute("update_success", "update_success");
        } else {
            sdb.AddSubject(code, name, author, status);
             session.setAttribute("add_success", "add_success");
        }
        String url = (String) session.getAttribute("pre_url");
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
