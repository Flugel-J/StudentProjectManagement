/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import dal.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author Admin
 */
public class UserController extends HttpServlet {

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
            out.println("<title>Servlet UserController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserController at " + request.getContextPath() + "</h1>");
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
        UserDB db = new UserDB();
        String isActive = request.getParameter("isActive");
        String id = request.getParameter("id");
        String tag = request.getParameter("tag");
        String roleId = request.getParameter("roleId");
        String status = request.getParameter("status");
        String name = request.getParameter("name");
//        System.out.println("------------------------------------");
//        System.out.println(roleId);
//        System.out.println(status);
//        System.out.println(name);
        if (isActive != null) {
            db.updateStatus(id, isActive);
            response.sendRedirect("userlist");
        } else if (tag != null) {
            if (tag.equals("update")) {
                User user = db.getUser(Integer.parseInt(id));
                request.setAttribute("user", user);
                request.setAttribute("listRole", db.getAllRole());
                request.getRequestDispatcher("user/UserDetail.jsp").forward(request, response);
            }
            if (tag.equals("create")) {
                request.getRequestDispatcher("user/UserCreate.jsp").forward(request, response);
            }
        } else {
            int index = 1;
            int pageSize = 5;

            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
            }
            List<User> list = db.Search(roleId, status, name);
            int total = list.size();
            int pageNumber = total / pageSize;
            if (total % pageSize != 0) {
                pageNumber++;
            }
            int start = (index - 1) * pageSize;
            int end;
            if (index * pageSize > total) {
                end = total;
            } else {
                end = pageSize * index;
            }
            request.setAttribute("list", db.getListByPage(list, start, end));
            request.setAttribute("role", roleId);
            request.setAttribute("status", status);
            request.setAttribute("name", name);
            request.setAttribute("index", index);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("listRole", db.getAllRole());
            request.getRequestDispatcher("user/UserList.jsp").forward(request, response);
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
        UserDB db = new UserDB();
        String role = request.getParameter("role");
            String roll = request.getParameter("roll");
        String tag = request.getParameter("tag");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String facebook = request.getParameter("facebook");
        String gender = request.getParameter("gender");
        String status = request.getParameter("status");
        String roleId = request.getParameter("roleId");
        String dob = request.getParameter("dob");
        System.out.println(gender);
        if (tag.equals("update")) {
            db.update(id, name, email, mobile, facebook, gender, Integer.parseInt(status), roleId);
        }
//        if (tag.equals("create")) {
//            
//            
//            db.insertUser(roll, name, gender, dob, email, mobile,facebook,  );
//            // pass, avt, dob, rollnumber
//        }
        response.sendRedirect("userlist");
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
