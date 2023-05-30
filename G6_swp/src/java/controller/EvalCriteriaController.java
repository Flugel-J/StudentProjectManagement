/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.EvalCriteriaDB;
import dal.IterationDB;
import extension.LogicalProcess;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.EvalCriteria;
import model.Iteration;
import model.User;

/**
 *
 * @author KHANHHERE
 */
public class EvalCriteriaController extends HttpServlet {

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
            out.println("<title>Servlet EvalCriteriaController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EvalCriteriaController at " + request.getContextPath() + "</h1>");
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
        EvalCriteriaDB edb = new EvalCriteriaDB();
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
                String author = null;
                if (u.getRoleId().equalsIgnoreCase("admin")) {
                    author = "";
                }
                if (u.getRoleId().equalsIgnoreCase("author")) {
                    author = u.getUserId() + "";
                }
                EvalCriteria e = edb.getEvalCriById(id, author);
                if (e == null) {
                    response.sendRedirect("evalcriteria");
                } else {
                    e.setStatus(!e.isStatus());
                    edb.updateEvalCri(e);
                    String url = (String) session.getAttribute("pre_url");
                    response.sendRedirect(url);
                }
                return;
            }
            // list
            String search_teamEval = request.getParameter("search_teamEval");
            String search_status = request.getParameter("search_status");
            String search_value = request.getParameter("search_value");
            if (search_teamEval == null) {
                search_teamEval = "";
            }
            if (search_status == null) {
                search_status = "";
            }
            if (search_value == null) {
                search_value = "";
            }
            String author = "";
            if (u.getRoleId().equalsIgnoreCase("admin")) {
                author = "";
            }
            if (u.getRoleId().equalsIgnoreCase("author")) {
                author = u.getUserId() + "";
            }

            int size = edb.count(search_teamEval, search_status, search_value, author);
            int curPage, numPerPage = 4;
            int NoPage = (size % numPerPage == 0 ? (size / numPerPage) : (size / numPerPage + 1));
            String curPage_raw = request.getParameter("curPage");
            if (curPage_raw == null) {
                curPage = 1;
            } else {
                curPage = Integer.parseInt(curPage_raw);
            }
            List<EvalCriteria> listPerPage = edb.search(search_teamEval, search_status, search_value, author, curPage, numPerPage);

            request.setAttribute("curPage", curPage);
            request.setAttribute("listPerPage", listPerPage);
            request.setAttribute("NoPage", NoPage);
            request.setAttribute("search_teamEval", search_teamEval);
            request.setAttribute("search_status", search_status);
            request.setAttribute("search_value", search_value);
            request.getRequestDispatcher("eval_criteria/CriteriaList.jsp").forward(request, response);
        } else {
            String author = null;
                if (u.getRoleId().equalsIgnoreCase("admin")) {
                    author = "";
                }
                if (u.getRoleId().equalsIgnoreCase("author")) {
                    author = u.getUserId() + "";
                }
            List<Iteration> listI = edb.getIterList(author);           
            request.setAttribute("tag", tag);           
            request.setAttribute("listI", listI);
            if (tag.equals("add")) {
                request.setAttribute("iterationId", listI.get(0).getIterationId()); 
                request.setAttribute("maxWeight", 100 - edb.getTotalWeight(listI.get(0).getIterationId()));
            }
            if (tag.equals("details")) {        
                EvalCriteria e = edb.getEvalCriById(id, author);
                if (e == null) {
                    response.sendRedirect("evalcriteria");
                } else {                 
                    request.setAttribute("e", e);
                    request.setAttribute("maxWeight", edb.getMaxPossibleWeight(e.getCriteriaId(), e.getIteration().getIterationId()));
                    request.setAttribute("iterationId", e.getIteration().getIterationId());                   
                }
            }
            request.getRequestDispatcher("eval_criteria/CriteriaDetails.jsp").forward(request, response);
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
        EvalCriteriaDB edb = new EvalCriteriaDB();
        IterationDB idb = new IterationDB();
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("useraccount");
        String url = (String) session.getAttribute("pre_url");

        String tag = request.getParameter("tag");
        String action = request.getParameter("act");

        String id = request.getParameter("id");
        String iterationId = request.getParameter("iterationId");
        String evalTitle = request.getParameter("evalTitle");
        String evalWeight = request.getParameter("evalWeight").trim();
        String criteriaOrder = request.getParameter("criteriaOrder");
        String maxLoc = request.getParameter("maxLoc");
        String teamEval_raw = request.getParameter("teamEval");
        String status_raw = request.getParameter("status");
        boolean teamEval = teamEval_raw.equals("1");
        boolean status = status_raw.equals("1");

        EvalCriteria e = new EvalCriteria();
        e.setCriteriaId(Integer.parseInt(id));
        e.setIteration(idb.getIterationByID(Integer.parseInt(iterationId)));
        e.setEvalTitle(new LogicalProcess().filterString(evalTitle));
        e.setEvalWeight(Double.parseDouble(evalWeight));
        e.setTeamEval(teamEval);
        e.setCriteriaOrder(Integer.parseInt(criteriaOrder));
        e.setMaxLoc(Integer.parseInt(maxLoc));   
        e.setStatus(status);
        
        if (action == null) {
            String author = null;
            if (u.getRoleId().equalsIgnoreCase("admin")) {
                author = "";
            }
            if (u.getRoleId().equalsIgnoreCase("author")) {
                author = u.getUserId() + "";
            }
            List<Iteration> listI = edb.getIterList(author);
            request.setAttribute("e", e);
            request.setAttribute("listI", listI);
            request.setAttribute("iterationId", iterationId);
            request.setAttribute("tag", tag);
            request.setAttribute("maxWeight", edb.getMaxPossibleWeight(Integer.parseInt(id), Integer.parseInt(iterationId)));

            request.getRequestDispatcher("eval_criteria/CriteriaDetails.jsp").forward(request, response);
        } else {
            if (tag.equals("details")) {
                edb.updateEvalCri(e);
                session.setAttribute("update_success", "update_success");
            }
            if (tag.equals("add")) {
                edb.addEC(e);
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
