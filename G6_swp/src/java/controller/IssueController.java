/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.IssueDB;
import dal.TeamDB;
import extension.GitlabSync;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Issue;
import model.Team;
import model.User;

/**
 *
 * @author Admin
 */
public class IssueController extends HttpServlet {

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
            out.println("<title>Servlet IssueController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet IssueController at " + request.getContextPath() + "</h1>");
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
        IssueDB db = new IssueDB();
        TeamDB teamDB = new TeamDB();
        String tag = request.getParameter("tag") == null ? " " : request.getParameter("tag");
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("useraccount");
        String aSuccess = (String) session.getAttribute("add_success");
        session.removeAttribute("add_success");
        if(aSuccess != null) request.setAttribute("success", "aSuccess");
        if (tag.equals("update")) {
            Issue issue = db.getIssue(id);
            List listSetting = db.getAllSetting();
            List listTeam = teamDB.getAllTeam();
            List listMile = new ArrayList();
            if (user.getRoleId().equals("trainer")) {
                listMile = db.getAllMile();
            } else {
                listMile = db.getMileStudent(user.getUserId());
            }
            request.setAttribute("issue", issue);
            request.setAttribute("listSetting", listSetting);
            request.setAttribute("listTeam", listTeam);
            request.setAttribute("listMile", listMile);
            request.setAttribute("title", "Issue Detail");
            request.setAttribute("btn", "Update");
            request.setAttribute("action", "update");
            request.getRequestDispatcher("issue/IssueDetail.jsp").forward(request, response);
        } else if (tag.equals("changeStatus")) {
            String isActive = request.getParameter("isActive").equals("1") ? " 0" : "1";
            db.updateStatus(id, isActive);
            String url = (String) session.getAttribute("pre_url");
            response.sendRedirect(url);
        } else if (tag.equals("add")) {
            List listMile = new ArrayList();
            if (user.getRoleId().equals("trainer")) {
                listMile = db.getAllMile();
            } else {
                listMile = db.getMileStudent(user.getUserId());
            }
            List listTeam = teamDB.getAllTeam();

            request.setAttribute("action", "add");
            request.setAttribute("title", "Add New");
            request.setAttribute("btn", "Create");
            request.setAttribute("listMile", listMile);
            request.setAttribute("listTeam", listTeam);

            request.getRequestDispatcher("issue/IssueDetail.jsp").forward(request, response);
        }
        else if (tag.equals("sync"))
        { 
            List<Team> list;
            if(user.getRoleId().equals("student")){
                list = db.getTeamByUser(user.getUserId(),false);
               
            }
            else{
                 list = db.getTeamByUser(user.getUserId(),true);
            }
            request.setAttribute("team", list);
            request.getRequestDispatcher("issue/Sync.jsp").forward(request, response);
        }
        else {
            String search_value = request.getParameter("search_value");
            String search_status = request.getParameter("search_status");
            int curPage = 1;
            int size = 5;
            try {
                curPage = Integer.parseInt(request.getParameter("curPage"));
            } catch (Exception e) {
            }
            List<Issue> list = db.Search(search_status, search_value);
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
            request.setAttribute("search_value", search_value);
            request.setAttribute("search_status", search_status);
            request.setAttribute("curPage", curPage);
            request.setAttribute("NoPage", NoPage);
            request.getRequestDispatcher("issue/IssueList.jsp").forward(request, response);
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
          String tag = request.getParameter("tag") == null ? " " : request.getParameter("tag");
          if(tag.equals("sync")){
              HttpSession session = request.getSession();
              User user = (User) session.getAttribute("useraccount");
              String team = request.getParameter("team");
              Team t = new TeamDB().getTeam(team);
             GitlabSync.getIssuesByTeam(t.getClassId().getClassCode(), t.getTeamCode(), t.getTeamId(), user.getUserId());
             List<Issue> list = new IssueDB().getIssueByTeam(t.getTeamId());
             for(Issue i :list){
                 GitlabSync.UploadIssue(t.getClassCode(), t.getTeamCode(), i.getTitle(), i.getDescript(), i.getDueDate().toString(), i.getLabel(),i.getId());
             }
              session.setAttribute("add_success", "add_success");
             //        UploadIssue("SE1549", "G3", "testUpload", "test", "2022-07-31", "to do");
             response.sendRedirect("issue");
             return;
          }
        int setting = Integer.parseInt(request.getParameter("setting"));
        int assignId = Integer.parseInt(request.getParameter("assignId"));
        String title = request.getParameter("title");

        Date dueDate = Date.valueOf(request.getParameter("dueDate"));
        int milestone = Integer.parseInt(request.getParameter("milestone"));
        String functionIds = request.getParameter("functionIds");
        int teamId = Integer.parseInt(request.getParameter("teamId"));
        String label = request.getParameter("label");
        String descript = request.getParameter("descript");
        int status = Integer.parseInt(request.getParameter("status"));
        IssueDB db = new IssueDB();
        if (tag.equals("update")) {
            int id = (Integer.parseInt(request.getParameter("id")));
            int gitlabId = Integer.parseInt(request.getParameter("gitlabId"));
            String gitlabUrl = request.getParameter("gitlabUrl");
            Date createAt = Date.valueOf(request.getParameter("createAt"));
            db.update(id, assignId, setting, title, gitlabId, gitlabUrl, createAt, dueDate, milestone, functionIds, teamId, label, descript, status);
            response.sendRedirect("./issue");
        } else if (tag.equals("add")) {
            db.add(assignId, setting, title, "", "", dueDate, milestone, functionIds, teamId, label, descript, status);
            response.sendRedirect("./issue");
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
