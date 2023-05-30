/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.EvalCriteriaDB;
import dal.TeamDB;
import dal.TeamEvalDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.EvalCriteria;
import model.Team;
import model.TeamEval;
import model.User;

/**
 *
 * @author KHANHHERE
 */
public class TeamEvalController extends HttpServlet {

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
            out.println("<title>Servlet TeamEvalController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TeamEvalController at " + request.getContextPath() + "</h1>");
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
        TeamEvalDB tedb = new TeamEvalDB();
        String id = request.getParameter("id");
        String uSuccess = (String) session.getAttribute("update_success");
        String aSuccess = (String) session.getAttribute("add_success");
        session.removeAttribute("update_success");
        session.removeAttribute("add_success");
        if(uSuccess != null) request.setAttribute("success", "uSuccess");
        if(aSuccess != null) request.setAttribute("success", "aSuccess");
        if (tag == null) {
            // change status
            if (id != null) {
                TeamEval te = tedb.getTEById(id);
                te.setStatus(!te.isStatus());
                tedb.update(te);
                String url = (String) session.getAttribute("pre_url");
                response.sendRedirect(url);
                return;
            }
            // list
            String search_team = request.getParameter("search_team");
            String search_value = request.getParameter("search_value");
            if (search_team == null) {
                search_team = "";
            }
            if (search_value == null) {
                search_value = "";
            }
            String stu = "", tra = "";
            if (u.getRoleId().equalsIgnoreCase("student")) {
                stu = u.getUserId() + "";
            } else {
                tra = u.getUserId() + "";
            }

            int size = tedb.count(search_team, search_value, stu, tra);
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
            List<TeamEval> listPerPage = tedb.search(search_team, search_value, stu, tra, curPage, numPerPage);
            List<Team> listTeam = tedb.getListTeam(stu, tra);

            request.setAttribute("curPage", curPage);
            request.setAttribute("listPerPage", listPerPage);
            request.setAttribute("NoPage", NoPage);
            request.setAttribute("search_team", search_team);
            request.setAttribute("search_value", search_value);
            request.setAttribute("listTeam", listTeam);
            request.getRequestDispatcher("evaluation/TeamEvalList.jsp").forward(request, response);
        } else {
            request.setAttribute("tag", tag);
            String tra = u.getUserId() + "";
            TeamDB tdb = new TeamDB();
            List<Team> listTeam = tdb.getListTeamOfTrainer(tra);

            if (tag.equals("add")) {
                request.setAttribute("listTeam", listTeam);
            }
            if (tag.equals("details")) {
                TeamEval te = tedb.getTEById(id);
                if (te == null) {
                    response.sendRedirect("teameval");
                } else {
                    request.setAttribute("te", te);
                }
            }
            request.getRequestDispatcher("evaluation/TeamEvalDetail.jsp").forward(request, response);
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
        User u = (User) session.getAttribute("useraccount");
        String tra = u.getUserId() + "";

        String url = (String) session.getAttribute("pre_url");
        TeamEvalDB tedb = new TeamEvalDB();
        String id = request.getParameter("id");
        TeamEval te = tedb.getTEById(id);
        TeamDB tdb = new TeamDB();
        EvalCriteriaDB ecdb = new EvalCriteriaDB();
        String grade = request.getParameter("grade");
        String tag = request.getParameter("tag");
        String action = request.getParameter("act");
        String note_raw = request.getParameter("note");
        String note;
        if (note_raw != null) {
            note = new extension.LogicalProcess().filterString(note_raw);
        } else {
            note = null;
        }
        String teamId = request.getParameter("teamId");
        String criteriaId = request.getParameter("criteriaId");
        if (action == null) {
            List<Team> listTeam = tdb.getListTeamOfTrainer(tra);
            List<EvalCriteria> listEC = ecdb.getECOfTeam(teamId);
            if (teamId.isEmpty()) {
                teamId = null;
            }

            request.setAttribute("grade", grade);
            request.setAttribute("note", note);
            request.setAttribute("listEC", listEC);
            request.setAttribute("team", tdb.getTeam(teamId));
            request.setAttribute("teamId", teamId);
            request.setAttribute("criteriaId", criteriaId);
            request.setAttribute("listTeam", listTeam);
            request.setAttribute("tag", tag);
            request.getRequestDispatcher("evaluation/TeamEvalDetail.jsp").forward(request, response);
        } else {
            PrintWriter out = response.getWriter();
            if (tag.equals("details")) {
                te.setGrade(Double.parseDouble(grade));
                te.setNote(note);
                tedb.update(te);
                session.setAttribute("update_success", "update_success");
            }
            if (tag.equals("add")) {
                int check = tedb.isTEExist(criteriaId, teamId);
                if (check == -1) {
                    // add
                    TeamEval te2 = new TeamEval(1, ecdb.getEvalCriById(criteriaId, ""), tdb.getTeam(teamId),
                            Double.parseDouble(grade), note, true);
                    tedb.add(te2);
                } else {
                    // replace
                    TeamEval te1 = tedb.getTEById(check + "");
                    te1.setGrade(Double.parseDouble(grade));
                    te1.setNote(note);
                    te1.setStatus(true);
                    tedb.update(te1);
                }
                session.setAttribute("add_success", "add_success");
            }
            response.sendRedirect(url);
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
