/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author 03623
 */
public class LogoutController extends HttpServlet {




    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        
        User user = null;
        request.getSession().setAttribute("useraccount", user);
        
                Cookie c_email = new Cookie("email", null);
                Cookie c_password = new Cookie("password", null);
                Cookie c_password_decrypt = new Cookie("passworddecrypt", null);
                c_email.setMaxAge(24*3600*7);// 7 ngày
                c_password.setMaxAge(24*3600*7);// 7 ngày
                c_password_decrypt.setMaxAge(24*3600*7);// 7 ngày
                response.addCookie(c_email);
                response.addCookie(c_password);
                response.addCookie(c_password_decrypt);
        
        response.sendRedirect("home");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
