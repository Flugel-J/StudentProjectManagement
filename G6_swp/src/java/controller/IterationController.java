/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.IterationDB;
import dal.SubjectDB;
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
import model.Iteration;

/**
 *
 * @author Admin
 */
public class IterationController extends HttpServlet {

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
        if (action == null) {
            
            String id_raw = request.getParameter("iterationId");
            String subjectid = request.getParameter("subjectId");
            String iterName = request.getParameter("iterName");
            String status = request.getParameter("status");
            
            List<Iteration> list;
            String xpage = request.getParameter("page");
            IterationDB idb = new IterationDB();

            int page, size, limit = 5, start, end;                     //paging support
            if (xpage == null) {
                page = 1;
            } else {
                page = Integer.parseInt(xpage);
            }
            /////////////////////////////////////////
            if (iterName == null || subjectid == null || status == null) {
                list = idb.Search("", "", "");
            } else {
                list = idb.Search(iterName, status,subjectid);                  //search and display
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
                    Iteration iteration = idb.getIterationByID(id);                //change status of setting
                    iteration.setStatus(!iteration.isStatus());
                    idb.updateIteration(iteration);
                    HttpSession session = request.getSession();
                    String url = (String) session.getAttribute("pre_url");
                    response.sendRedirect(url);
                    return;
                } catch (Exception e) {

                }
            }
            List<String> listIterName = idb.getListIterName();                        // <select> data
            ServletContext sc = getServletContext();
           

            request.setAttribute("itername", listIterName);
            request.setAttribute("selectedItername", iterName);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("selectedSubjectid", subjectid);
            request.setAttribute("listIteration", idb.getListByPage(list, start, end));
            request.setAttribute("page", page);
            request.setAttribute("numpage", numpage);
            request.getRequestDispatcher("iteration/IterationList.jsp").forward(request, response);

        } else if (action.equals("add")) {
            IterationDB idb = new IterationDB();
            List<String> listItername = idb.getListIterName();
            LinkedHashMap<String, String> listSub = new LinkedHashMap<>();
            ServletContext sc = getServletContext();
            for (String i : listItername) {
                listSub.put(i, sc.getInitParameter(i));
            }
            request.setAttribute("listSub", listSub);
            request.setAttribute("action", action);
            request.getRequestDispatcher("iteration/IterationAdd.jsp").forward(request, response);
        } else if (action.equals("update")) {
            String id = request.getParameter("id");
            IterationDB idb = new IterationDB();
            Iteration i = idb.getIterationByID(Integer.parseInt(id));
            
            request.setAttribute("iteration", i);
            request.setAttribute("action", action);
            request.getRequestDispatcher("iteration/IterationAdd.jsp").forward(request, response);
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
//        String tag = request.getParameter("tag");

        String iterationId = request.getParameter("iterationId");
        String subjectId = request.getParameter("subjectId");
        String iterName = request.getParameter("iterName");
        String duration = request.getParameter("duration");
        String status_raw = request.getParameter("status");
        LogicalProcess lp = new LogicalProcess();
        IterationDB idb = new IterationDB();
        boolean status = status_raw.equals("1");
         try{
            Iteration i = new Iteration(Integer.parseInt(iterationId), Integer.parseInt(subjectId), iterName, Integer.parseInt(duration), status);
            idb.updateIteration(i);
            HttpSession sesion = request.getSession();
            String url = (String) sesion.getAttribute("pre_url");
            response.sendRedirect(url);
        }
         catch (NumberFormatException e){
            Iteration i = new Iteration(Integer.parseInt(subjectId), iterName, Integer.parseInt(duration), status);
            idb.insertIter(i);
            response.sendRedirect("iterationlist");
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
