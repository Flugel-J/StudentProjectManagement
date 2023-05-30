/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.TeamDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Team;

/**
 *
 * @author Admin
 */
public class TeamController extends HttpServlet {

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
            out.println("<title>Servlet TeamController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TeamController at " + request.getContextPath() + "</h1>");
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
        String tag = request.getParameter("tag");
        String id = request.getParameter("id");
        String search_class = request.getParameter("search_class");
        String search_status = request.getParameter("search_status");
        String search_value = request.getParameter("search_value");
        String isActive = request.getParameter("isActive");
        HttpSession session = request.getSession();
        if (tag == null) {
            tag = "";
        }
//        System.out.println(tag);
//        System.out.println(id);
//        System.out.println(search_class);
//        System.out.println(search_status);
//        System.out.println(search_value);
        TeamDB db = new TeamDB();
        List listClass = db.getAllClass();
        request.setAttribute("listClass", listClass);
        if (tag.equals("update")) {
            Team team = db.getTeam(id);
            session.setAttribute("teamCode", team.getTeamCode());
            request.setAttribute("team", team);
            request.setAttribute("btn", "Update");
            request.setAttribute("title", "Team Detail");
            request.setAttribute("action", "update");
            request.getRequestDispatcher("team/TeamDetail.jsp").forward(request, response);
        } else if (tag.equals("changeStatus")) {
            db.updateStatus(id, isActive);
            String url = (String) session.getAttribute("pre_url");
            response.sendRedirect(url);
        } else if (tag.equals("add")) {
            request.setAttribute("action", "add");
            request.setAttribute("title", "Add New");
            request.setAttribute("btn", "Create");
            request.getRequestDispatcher("team/TeamDetail.jsp").forward(request, response);
        } else {
            int curPage = 1;
            int size = 5;
            try {
                curPage = Integer.parseInt(request.getParameter("curPage"));
            } catch (Exception e) {
            }
            List<Team> list = db.Search(search_class, search_status, search_value);
            int total = list.size();
            int NoPage = total / size;
            if (total % size != 0) {
                NoPage++;
            }
            int start = (curPage - 1) * size;
            int end;
            if (curPage * size > total) {
                end = total;
            } else {
                end = size * curPage;
            }
            request.setAttribute("listPerPage", db.getListByPage(list, start, end));
            request.setAttribute("search_class", search_class);
            request.setAttribute("search_status", search_status);
            request.setAttribute("search_value", search_value);
            request.setAttribute("curPage", curPage);
            request.setAttribute("NoPage", NoPage);
            request.getRequestDispatcher("team/TeamList.jsp").forward(request, response);
        }

// if (tag == null) {
//            List list = db.getAllTeam();
//            List listClass = db.getListClassCode();
//            request.setAttribute("list", list);
//            request.setAttribute("listClass", listClass);
//            request.getRequestDispatcher("team/TeamList.jsp").forward(request, response);
//        } else
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
        TeamDB db = new TeamDB();
        String tag = request.getParameter("tag");
        String teamId = request.getParameter("id");
        String teamCode = request.getParameter("teamCode");
        String className = request.getParameter("class");
        String topicN = request.getParameter("topicN");
        String topC = request.getParameter("topC");
        String git = request.getParameter("git");
        String status = request.getParameter("status");
//        System.out.println(teamId);
        System.out.println(teamCode);
        System.out.println(className);
//        System.out.println(topicN);ss
//        System.out.println(topC);
//        System.out.println(git);
//        System.out.println(status);
        System.out.println(tag);
        HttpSession session = request.getSession();
        String url = (String) session.getAttribute("pre_url");

        List<String> list = db.getTeamById(session.getAttribute("teamCode").toString(), className);
        if (list.contains(teamCode)) {
            response.sendRedirect(url);
        } else if (tag.equals("update")) {
            db.updateTeam(teamId, teamCode, className, topicN, topC, git, status);
            response.sendRedirect("./team");
        } else if (tag.equals("add")) {
            db.add(teamCode, className, topicN, topC, git, status);
            response.sendRedirect("./team");
        }
//        if (db.validateTeam(teamCode, className)) {
//            
//        }else{
//            String mess = "Team code is exist";
//            HttpSession session = request.getSession();
//            session.setAttribute("mess", mess);
//            response.sendRedirect("./team?tag=update&id="+ teamId);
//        }
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
