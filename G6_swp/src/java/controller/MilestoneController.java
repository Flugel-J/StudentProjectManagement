/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.MilestoneDB;
import extension.LogicalProcess;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Iteration;
import model.Milestone;
import model.User;

/**
 *
 * @author admin
 */
public class MilestoneController extends HttpServlet {

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
            out.println("<title>Servlet MilestoneController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MilestoneController at " + request.getContextPath() + "</h1>");
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

        String id = request.getParameter("id");
        String a = request.getParameter("a");
        MilestoneDB mdb = new MilestoneDB();
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("useraccount");
         String uSuccess = (String) session.getAttribute("update_success");
        String aSuccess = (String) session.getAttribute("add_success");
        session.removeAttribute("update_success");
        session.removeAttribute("add_success");
        if(uSuccess != null) request.setAttribute("success", "uSuccess");
        if(aSuccess != null) request.setAttribute("success", "aSuccess");
        if (a == null) {
            String xpage = request.getParameter("page");
            String Class = request.getParameter("class");
            String status = request.getParameter("status");
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            List<Milestone> list;
            if (mdb.getMilestoneById(id, u.getUserId()) != null) {
                mdb.changeStatus(id);
                String url = (String) session.getAttribute("pre_url");
                session.setAttribute("update_success", "update_success");
                response.sendRedirect(url);
                return;
            }
            int page, size, limit = 4;                     //paging support
            if (xpage == null) {
                page = 1;
            } else {
                page = Integer.parseInt(xpage);
            }

            if (Class == null || status == null || from == null || to == null) {
                size = mdb.count(Integer.toString(u.getUserId()), "", "", "", "");
                list = mdb.Search(Integer.toString(u.getUserId()), "", "", "", "", page, limit);
            } else {
                size = mdb.count(Integer.toString(u.getUserId()), Class, from, to, status);
                list = mdb.Search(Integer.toString(u.getUserId()), Class, from, to, status, page, limit);
            }
            int numpage = size / limit + (size % limit == 0 ? 0 : 1);
            request.setAttribute("listMilestone", list);
            request.setAttribute("classList", mdb.ClassList(u.getUserId()));
            request.setAttribute("selectedClass", Class);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("selectedFrom", from);
            request.setAttribute("selectedTo", to);
            request.setAttribute("page", page);
            request.setAttribute("numpage", numpage);
            request.getRequestDispatcher("milestone/Milestone.jsp").forward(request, response);
        } else {
            if ("update".equals(a)) {
                Milestone m = mdb.getMilestoneById(id, u.getUserId());
                request.setAttribute("milestone", m);
                request.setAttribute("tag", a);
                request.getRequestDispatcher("milestone/Detail.jsp").forward(request, response);

            } else if ("add".equals(a)) {
                request.setAttribute("classList", mdb.ClassList(u.getUserId()));
                request.setAttribute("tag", a);
                request.getRequestDispatcher("milestone/Detail.jsp").forward(request, response);
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
        LogicalProcess lp = new LogicalProcess();
        MilestoneDB mdb = new MilestoneDB();
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("useraccount");
        if (id != null) {
            String name = lp.filterString(request.getParameter("milestone"));
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            String status = request.getParameter("status");
            Milestone m = mdb.getMilestoneById(id, u.getUserId());
            String oldName = m.getMilestoneName();
            if (mdb.checkUniqueMilestoneName(name, Integer.toString(m.getIteration().getIterationId()), Integer.toString(m.getClassId().getClassId())) && !name.equals(oldName)) {
                m.setMilestoneName(name);
                request.setAttribute("milestone", m);
                request.setAttribute("tag", "update");
                request.setAttribute("err", "Class " + m.getClassId().getClassCode() + " in " + m.getIteration().getIterName() + " already have milestone:" + name);
                request.getRequestDispatcher("milestone/Detail.jsp").forward(request, response);
            } else {
                mdb.update(id, name, from, to, status);
                 session.setAttribute("update_success", "update_success");
                String url = (String) session.getAttribute("pre_url");
                response.sendRedirect(url);
                return;
            }
        } else {
            String Class = request.getParameter("class");
            if (Class == "") {
                request.setAttribute("classList", mdb.ClassList(u.getUserId()));
                request.setAttribute("tag", "add");
                request.getRequestDispatcher("milestone/Detail.jsp").forward(request, response);
            } else {
                
                String action= request.getParameter("act");
                if (action == null) {
                    List<Iteration> list = mdb.getIterationByClass(Class);
                    request.setAttribute("selectedClass", Class);
                    request.setAttribute("classList", mdb.ClassList(u.getUserId()));
                    request.setAttribute("iteration", list);
                    request.setAttribute("tag", "add");
                    request.getRequestDispatcher("milestone/Detail.jsp").forward(request, response);
                } else {
                    String name = lp.filterString(request.getParameter("milestone"));
                    String iteration = request.getParameter("iteration");
                    String from = request.getParameter("from");
                    String to = request.getParameter("to");
                    String status = request.getParameter("status");
                    if (mdb.checkUniqueMilestoneName(name, iteration, Class)) {
                        request.setAttribute("classList", mdb.ClassList(u.getUserId()));
                        request.setAttribute("tag", "add");
                        List<Iteration> list = mdb.getIterationByClass(Class);
                        request.setAttribute("iteration", list);
                        request.setAttribute("selectedClass", Class);
                        request.setAttribute("classList", mdb.ClassList(u.getUserId()));
                        request.setAttribute("err", "duplicate Milestone name!");
                        request.getRequestDispatcher("milestone/Detail.jsp").forward(request, response);
                    } else {
                        mdb.Add(name, iteration, Class, from, to, status);
                        session.setAttribute("add_success", "add_success");
                        String url = (String) session.getAttribute("pre_url");
                        response.sendRedirect(url);
                    }

                }

            }
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
