/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import extension.Encode;
import dal.UserDB;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author 03623
 */
public class LoginController extends HttpServlet {

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("user/Login.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password2 = null;
        String remember = request.getParameter("remember");
        if(remember==null){
            remember ="fase";
        }
        Encode en = new Encode();
        try {
            password2 = en.encrypt(password);
        } catch (Exception e) {
            password2=null;
        }
        UserDB ur = new UserDB();
        User user1 = ur.getUserAccount(email, password2);
        if(user1==null){
            String Notification2 = "Wrong login or password!";
            request.setAttribute("notification", Notification2);
            request.getRequestDispatcher("user/Login.jsp").forward(request, response);
        }
            

               if(remember.equals("fase")){// ko nhớ
                request.getSession().setAttribute("useraccount", user1);
                
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
                }else{
                // nhớ 7 ngày
                Cookie c_email = new Cookie("email", user1.getEmail());
                Cookie c_password = new Cookie("password", user1.getPassword());
                Cookie c_password_decrypt = new Cookie("passworddecrypt", password);
                c_email.setMaxAge(24*3600*7);// 7 ngày
                c_password.setMaxAge(24*3600*7);// 7 ngày
                c_password_decrypt.setMaxAge(24*3600*7);// 7 ngày
                response.addCookie(c_email);
                response.addCookie(c_password);
                response.addCookie(c_password_decrypt);
                
                request.getSession().setAttribute("useraccount", user1);
                response.sendRedirect("home");
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

