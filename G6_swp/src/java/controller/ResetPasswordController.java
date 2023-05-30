/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import extension.CheckMail;
import extension.Encode;
import dal.UserDB;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 03623
 */
public class ResetPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String mail1 = request.getParameter("email2");
        if (mail1 != null) {
            String email2 = request.getParameter("email2").replaceAll(" ", "+");
            Encode en2 = new Encode();
            String email3 = null;
            try {
                email3 = en2.decrypt(email2);
            } catch (Exception e) {
                email3 = null;
            }

//            UserDB u = new UserDB();
//            u.updatepassword(u.getUseridbyemail(email3), password_random_encode);
//            String Notification1 = "Sent a new password to your email!";
//            request.setAttribute("notification", Notification1);
            request.setAttribute("email", email3);
            request.getRequestDispatcher("user/ResetPassword2.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("user/ResetPassword.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String check1 = request.getParameter("check1");
        if (check1 != null) {
            String mail1 = request.getParameter("mail");
            String newpassword1 = request.getParameter("newpassword1");
            String newpassword2 = request.getParameter("newpassword2");
            if (newpassword1.equals(newpassword2)) {
                Encode en2 = new Encode();
                String password_random_encode = null;
                try {
                    password_random_encode = en2.encrypt(newpassword1);
                } catch (Exception e) {
                    password_random_encode = null;
                }
                UserDB u = new UserDB();
                u.updatepassword(u.getUseridbyemail(mail1), password_random_encode);
                String Notification1 = "Password setup successful!";
                request.setAttribute("notification", Notification1);
                request.getRequestDispatcher("user/Login.jsp").forward(request, response);
            } else {
                String Notification1 = "New password does not match!";
                request.setAttribute("notification1", Notification1);
                request.setAttribute("email", mail1);
                request.getRequestDispatcher("user/ResetPassword2.jsp").forward(request, response);
            }
        } else {
            String mail1 = request.getParameter("mail");
            String emailcode = "";
            Encode en = new Encode();
            try {
                emailcode = en.encrypt(mail1);
            } catch (Exception e) {
                emailcode = null;
            }
            String subject = "Confirmation email reset account password.";
            String message = "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "\n"
                    + "<head>\n"
                    + "    <title></title>\n"
                    + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n"
                    + "    <style type=\"text/css\">\n"
                    + "        @media screen {\n"
                    + "            @font-face {\n"
                    + "                font-family: 'Lato';\n"
                    + "                font-style: normal;\n"
                    + "                font-weight: 400;\n"
                    + "                src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\n"
                    + "            }\n"
                    + "\n"
                    + "            @font-face {\n"
                    + "                font-family: 'Lato';\n"
                    + "                font-style: normal;\n"
                    + "                font-weight: 700;\n"
                    + "                src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\n"
                    + "            }\n"
                    + "\n"
                    + "            @font-face {\n"
                    + "                font-family: 'Lato';\n"
                    + "                font-style: italic;\n"
                    + "                font-weight: 400;\n"
                    + "                src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\n"
                    + "            }\n"
                    + "\n"
                    + "            @font-face {\n"
                    + "                font-family: 'Lato';\n"
                    + "                font-style: italic;\n"
                    + "                font-weight: 700;\n"
                    + "                src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\n"
                    + "            }\n"
                    + "        }\n"
                    + "\n"
                    + "        /* CLIENT-SPECIFIC STYLES */\n"
                    + "        body,\n"
                    + "        table,\n"
                    + "        td,\n"
                    + "        a {\n"
                    + "            -webkit-text-size-adjust: 100%;\n"
                    + "            -ms-text-size-adjust: 100%;\n"
                    + "        }\n"
                    + "\n"
                    + "        table,\n"
                    + "        td {\n"
                    + "            mso-table-lspace: 0pt;\n"
                    + "            mso-table-rspace: 0pt;\n"
                    + "        }\n"
                    + "\n"
                    + "        img {\n"
                    + "            -ms-interpolation-mode: bicubic;\n"
                    + "        }\n"
                    + "\n"
                    + "        /* RESET STYLES */\n"
                    + "        img {\n"
                    + "            border: 0;\n"
                    + "            height: auto;\n"
                    + "            line-height: 100%;\n"
                    + "            outline: none;\n"
                    + "            text-decoration: none;\n"
                    + "        }\n"
                    + "\n"
                    + "        table {\n"
                    + "            border-collapse: collapse !important;\n"
                    + "        }\n"
                    + "\n"
                    + "        body {\n"
                    + "            height: 100% !important;\n"
                    + "            margin: 0 !important;\n"
                    + "            padding: 0 !important;\n"
                    + "            width: 100% !important;\n"
                    + "        }\n"
                    + "\n"
                    + "        /* iOS BLUE LINKS */\n"
                    + "        a[x-apple-data-detectors] {\n"
                    + "            color: inherit !important;\n"
                    + "            text-decoration: none !important;\n"
                    + "            font-size: inherit !important;\n"
                    + "            font-family: inherit !important;\n"
                    + "            font-weight: inherit !important;\n"
                    + "            line-height: inherit !important;\n"
                    + "        }\n"
                    + "\n"
                    + "        /* MOBILE STYLES */\n"
                    + "        @media screen and (max-width:600px) {\n"
                    + "            h1 {\n"
                    + "                font-size: 32px !important;\n"
                    + "                line-height: 32px !important;\n"
                    + "            }\n"
                    + "        }\n"
                    + "\n"
                    + "        /* ANDROID CENTER FIX */\n"
                    + "        div[style*=\"margin: 16px 0;\"] {\n"
                    + "            margin: 0 !important;\n"
                    + "        }\n"
                    + "    </style>\n"
                    + "</head>\n"
                    + "\n"
                    + "<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">\n"
                    + "    <!-- HIDDEN PREHEADER TEXT -->\n"
                    + "    <div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Lato', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\"> We're thrilled to have you here! Get ready to dive into your new account.\n"
                    + "    </div>\n"
                    + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                    + "        <!-- LOGO -->\n"
                    + "        <tr>\n"
                    + "            <td bgcolor=\"#FFA73B\" align=\"center\" style=\"background-color: #0e69af;\">\n"
                    + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "                    <tr>\n"
                    + "                        <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\n"
                    + "                    </tr>\n"
                    + "                </table>\n"
                    + "            </td>\n"
                    + "        </tr>\n"
                    + "        <tr>\n"
                    + "            <td bgcolor=\"#FFA73B\" align=\"center\" style=\"padding: 0px 10px 0px 10px; background-color: #0e69af;\" >\n"
                    + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "                    <tr>\n"
                    + "                        <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\n"
                    + "                            <h1 style=\"font-size: 48px; font-weight: 400; margin: 2;\">Confirm reset password!</h1> <img src=\"https://cdn-icons-png.flaticon.com/512/6195/6195699.png\" width=\"125\" height=\"120\" style=\"display: block; border: 0px;\" />\n"
                    + "                        </td>\n"
                    + "                    </tr>\n"
                    + "                </table>\n"
                    + "            </td>\n"
                    + "        </tr>\n"
                    + "        <tr>\n"
                    + "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\" >\n"
                    + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "                    <tr>\n"
                    + "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n"
                    + "                            \n"
                    + "                        </td>\n"
                    + "                    </tr>\n"
                    + "                    <tr>\n"
                    + "                        <td bgcolor=\"#ffffff\" align=\"left\">\n"
                    + "                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                <tr>\n"
                    + "                                    <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 60px 30px;\">\n"
                    + "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                            <tr>\n"
                    + "                                                <td align=\"center\" style=\"border-radius: 3px; background-color: #0e69af;\" bgcolor=\"#FFA73B\">\n"
                    + "                                                    <a href=\"http://localhost:8084/G6_swp/resetpassword?email2=" + emailcode + "\" target=\"_blank\" style=\"font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 2px; border: 1px solid #0e69af; display: inline-block;\">\n"
                    + "                                                    Confirm reset password</a></td>\n"
                    + "                                            </tr>\n"
                    + "                                        </table>\n"
                    + "                                    </td>\n"
                    + "                                </tr>\n"
                    + "                            </table>\n"
                    + "                        </td>\n"
                    + "                    </tr> <!-- COPY -->\n"
                    + " <!-- COPY -->\n"
                    + "\n"
                    + "                    </tr>\n"
                    + "                </table>\n"
                    + "            </td>\n"
                    + "        </tr>\n"
                    + "        <tr>\n"
                    + "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 30px 10px 0px 10px;\">\n"
                    + "                <table style=\"margin-left:228px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "                    <tr>\n"
                    + "                        <td bgcolor=\"#FFECD1\" align=\"center\" style=\"padding: 30px 30px 30px 30px; border-radius: 4px 4px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px; background-color: #cbe9ef;\">\n"
                    + "                            <h2 style=\"font-size: 20px; font-weight: 400; color: #0e69af; margin: 0;\">Your information will be kept confidential.</h2>\n"
                    + "                            <p style=\"margin: 0;\"><a href=\"#\" target=\"_blank\" style=\"color: #0e69af;\"></a></p>\n"
                    + "                        </td>\n"
                    + "                    </tr>\n"
                    + "                </table>\n"
                    + "            </td>\n"
                    + "        </tr>\n"
                    + "    </table>\n"
                    + "</body>\n"
                    + "\n"
                    + "</html>";
            UserDB udb = new UserDB();
            if (udb.getUserEmail(mail1)) {
                CheckMail e = new CheckMail();
                e.send(mail1, subject, message, "0362351671trai@gmail.com", "0362351671");
                String Notification1 = "Sent confirmation to your email!";
                request.setAttribute("notification1", Notification1);
                request.getRequestDispatcher("user/ResetPassword.jsp").forward(request, response);

            } else {

                String Notification1 = "Email is not registered!";
                request.setAttribute("notification1", Notification1);
                request.getRequestDispatcher("user/ResetPassword.jsp").forward(request, response);
            }
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
