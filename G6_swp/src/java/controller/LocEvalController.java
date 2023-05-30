/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.LocEvalDB;
import dal.SubjectDB;
import dal.SubjectSettingDB;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.LocEval;
import model.Subject;
import model.SubjectSetting;
import model.User;

/**
 *
 * @author KHANHHERE
 */
public class LocEvalController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        String trackingId = request.getParameter("id");
        LocEvalDB ledb = new LocEvalDB();
        SubjectDB sdb = new SubjectDB();
        SubjectSettingDB ssdb = new SubjectSettingDB();
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("useraccount");
        String stu = "", tra = "";
        if (u.getRoleId().equalsIgnoreCase("student")) {
            stu = u.getUserId() + "";
        } else {
            tra = u.getUserId() + "";
        }

        if (ledb.getTrackingById(Integer.parseInt(trackingId), stu, tra) == null) {
            response.sendRedirect("home/404.jsp");
            return;
        }

        LocEval le = ledb.getLocEvalByTrackingId(Integer.parseInt(trackingId), stu, tra);
        String tag;
        if (u.getRoleId().equalsIgnoreCase("student")) {
            tag = "view";
        } else {
            if (le != null) {
                tag = "update";
            } else {
                tag = "add";
            }
        }
        Subject s = sdb.getSubjectOfTracking(Integer.parseInt(trackingId));
        List<SubjectSetting> complexity = ssdb.getPropertiesOfSubject(s.getSubjectId() + "", 4);
        List<SubjectSetting> quality = ssdb.getPropertiesOfSubject(s.getSubjectId() + "", 5);

        request.setAttribute("complexList", complexity);
        request.setAttribute("qualityList", quality);
        request.setAttribute("tag", tag);
        request.setAttribute("locEval", le);
        request.setAttribute("trackingId", trackingId);
        request.getRequestDispatcher("evaluation/LocEval.jsp").forward(request, response);
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
        String complexId = request.getParameter("complexity");
        String qualityId = request.getParameter("quality");
        String note = new extension.LogicalProcess().filterString(request.getParameter("note"));
        String status_raw = request.getParameter("status");
        boolean status = status_raw.equals("1");
        String tag = request.getParameter("tag");
        String locEvalId = request.getParameter("id");
        String trackingId =request.getParameter("trackingId");
        SubjectSettingDB ssdb = new SubjectSettingDB();
        LocEvalDB ledb = new LocEvalDB();
        LocEval le = new LocEval(Integer.parseInt(locEvalId),null, note, 
                ssdb.getSSById(complexId, ""), ssdb.getSSById(qualityId, ""),
                ledb.getTrackingById(Integer.parseInt(trackingId), "", ""), status);
        
        if(tag.equals("update")){
            ledb.update(le);
        }
        if(tag.equals("add")){
            ledb.add(le);
        }
        // need url form tracking table to redirect
        response.sendRedirect("home");
        
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
