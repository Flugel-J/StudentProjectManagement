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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author 03623
 */
public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("user/ChangePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        UserDB ur = new UserDB();
        String oldpassword = request.getParameter("oldpassword");
        String newpassword1 = request.getParameter("newpassword1");
        String newpassword2 = request.getParameter("newpassword2");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("useraccount");
        if (user == null) {
            String Notification1 = "You are not logged in.";
            request.setAttribute("notification1", Notification1);
            request.getRequestDispatcher("user/ChangePassword.jsp").forward(request, response);
        } else {
            Encode en2 = new Encode();
            String oldpassword_encode = null;
            try {
                oldpassword_encode = en2.encrypt(oldpassword);
            } catch (Exception e) {
                oldpassword_encode = null;
            }
            if (!user.getPassword().equals(oldpassword_encode)) {
                String Notification1 = "Wrong old password.";
                request.setAttribute("notification1", Notification1);
                request.getRequestDispatcher("user/ChangePassword.jsp").forward(request, response);
            } else {
                if (!newpassword1.equals(newpassword2)) {
                    String Notification1 = "Password incorrect.";
                    request.setAttribute("notification1", Notification1);
                    request.getRequestDispatcher("user/ChangePassword.jsp").forward(request, response);
                } else {
                    if (oldpassword.equals(newpassword1)) {
                        String Notification1 = "The new password cannot be the same as the old password!";
                        request.setAttribute("notification1", Notification1);
                        request.getRequestDispatcher("user/ChangePassword.jsp").forward(request, response);
                    } else {
                        String newpassword_encode = null;
                        try {
                            newpassword_encode = en2.encrypt(newpassword1);
                        } catch (Exception e) {
                            newpassword_encode = null;
                        }

                        ur.updatepassword(user.getUserId(), newpassword_encode);
                        request.getSession().setAttribute("useraccount", null);
                        String Notification1 = "Password change successful!";
                        request.setAttribute("notification", Notification1);
                        request.getRequestDispatcher("user/Login.jsp").forward(request, response);
                    }

                }

            }

        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
