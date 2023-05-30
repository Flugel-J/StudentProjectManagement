/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.MilestoneDB;
import extension.GitlabSync;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Milestone;
import model.User;

/**
 *
 * @author admin
 */
public class MilestoneSyncController extends HttpServlet {

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
            out.println("<title>Servlet MilestonesSyncController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MilestonesSyncController at " + request.getContextPath() + "</h1>");
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
        String code = request.getParameter("code");
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("useraccount");
        String aSuccess = (String) session.getAttribute("add_success");
        session.removeAttribute("update_success");
        session.removeAttribute("add_success");
        if(aSuccess != null) request.setAttribute("success", "aSuccess");
        List<Milestone> list = new MilestoneDB().Search(u.getUserId()+"", id, "", "", "", 1, 10);
        request.setAttribute("aclass", code);
        request.setAttribute("id", id);
        request.setAttribute("listMilestone", list);
        request.setAttribute("listGitLab", GitlabSync.getMilestones(code));
        request.getRequestDispatcher("milestone/Sync.jsp").forward(request, response);
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
        String id = request.getParameter("id");
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("useraccount");
        MilestoneDB mdb= new MilestoneDB();
        List<Milestone> spm = mdb.Search(u.getUserId()+"", id, "", "", "1", 1, 10);
        List<Milestone> gitlab = GitlabSync.getMilestones(code);
        for(Milestone m : gitlab){
            if(!mdb.checkUniqueMilestoneName(m.getMilestoneName(), "", id)){
                mdb.Add(m.getMilestoneName(), "", id, m.getFromDate()+"", m.getToDate()+"", m.getStatus()+"");
            }
        }
        for(Milestone m: spm){
            GitlabSync.UploadMilestones(code, m);
        }
            
        session.setAttribute("add_success", "add_success");
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
