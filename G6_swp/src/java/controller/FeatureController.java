/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.FeatureDB;
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
import model.Feature;
import model.Team;

/**
 *
 * @author phamt
 */
public class FeatureController extends HttpServlet {

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

            String id_raw = request.getParameter("id");
            String teamId = request.getParameter("teamId");
            String featureName = request.getParameter("featureName");
            String status = request.getParameter("status");
            String search_value = request.getParameter("search_value");

            List<Feature> list;
            FeatureDB fdb = new FeatureDB();
            //paging
            int size = fdb.count(search_value, status);
            int curPage, numPerPage = 4;
            int NoPage = (size % numPerPage == 0 ? (size / numPerPage) : (size / numPerPage + 1));
            String curPage_raw = request.getParameter("curPage");
            if (curPage_raw == null) {
                curPage = 1;
            } else {
                curPage = Integer.parseInt(curPage_raw);
            }
            if (search_value == null || status == null) {
                list = fdb.seach("", "", curPage, numPerPage);
            } else {
                list = fdb.seach(search_value, status, curPage, numPerPage);                  //search and display
            }
            if (!"".equals(id_raw)) {                                            //update status
                try {
                    int id = Integer.parseInt(id_raw);
                    Feature feature = fdb.getFeatureByID(id);                //change status of setting
                    feature.setStatus(!feature.isStatus());
                    fdb.updateFeature(feature);
                    HttpSession session = request.getSession();
                    String url = (String) session.getAttribute("pre_url");
                    response.sendRedirect(url);
                    return;
                } catch (Exception e) {

                }
            }

            request.setAttribute("curPage", curPage);
            request.setAttribute("NoPage", NoPage);


            request.setAttribute("status", status);
            request.setAttribute("search_value", search_value);
            request.setAttribute("listFeature", list);
            request.getRequestDispatcher("feature/FeatureList.jsp").forward(request, response);
        } else if (action.equals("add")) {
            FeatureDB fdb = new FeatureDB();
            List<String> listFeaturename = fdb.getListFeatureName();
            LinkedHashMap<String, String> listSub = new LinkedHashMap<>();
            ServletContext sc = getServletContext();
            for (String i : listFeaturename) {
                listSub.put(i, sc.getInitParameter(i));
            }
            request.setAttribute("listSub", listSub);
            request.setAttribute("action", action);
            request.getRequestDispatcher("feature/FeatureDetails.jsp").forward(request, response);
        } else if (action.equals("update")) {
            String id = request.getParameter("id");
            FeatureDB fdb = new FeatureDB();
            Feature f = fdb.getFeatureByID(Integer.parseInt(id));
            request.setAttribute("feature", f);
            request.setAttribute("action", action);
            request.getRequestDispatcher("feature/FeatureDetails.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String featureId = request.getParameter("featureId");
        String teamId = request.getParameter("teamId");
        String featureName = request.getParameter("featureName");
        String status_raw = request.getParameter("status");
        String topicCode = request.getParameter("topicCode");
        String topicName = request.getParameter("topicName");
        

        LogicalProcess lp = new LogicalProcess();
        FeatureDB fdb = new FeatureDB();
        boolean status = status_raw.equals("1");
        try {
            Feature f = new Feature(Integer.parseInt(featureId), new Team(Integer.parseInt(teamId),""), featureName, status);
            fdb.updateFeature(f);
            HttpSession sesion = request.getSession();
            String url = (String) sesion.getAttribute("pre_url");
            response.sendRedirect(url);
        } catch (NumberFormatException e) {
            Feature f = new Feature(1, new Team(Integer.parseInt(teamId),""), featureName, status);
            fdb.addFeature(f);
            response.sendRedirect("featurelist");
        }

    }

}
