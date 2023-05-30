/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import extension.Encode;
import dal.UserDB;
import extension.CheckMail;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author 03623
 */
public class RegistrationController extends HttpServlet {

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String mail1 = request.getParameter("email2");
        if(mail1!=null){
            String email2 = request.getParameter("email2").replaceAll(" ", "+");
            Encode en2 = new Encode();
            String email3 = null;
            try {
                email3 = en2.decrypt(email2);
            } catch (Exception e) {
                email3=null;
            }
            request.setAttribute("email2", email3);
            request.getRequestDispatcher("user/Registration.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("user/CheckMail.jsp").forward(request, response);
        }
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        
        String mail1 = request.getParameter("mail");
        if(mail1!=null){
            String emailcode= "";
        Encode en = new Encode();
        try {
            emailcode = en.encrypt(mail1);
        } catch (Exception e) {
            emailcode=null;
        }
        String subject = "Account registration confirmation email.";
        String message = "<!DOCTYPE html>\n" +
"<html>\n" +
"\n" +
"<head>\n" +
"    <title></title>\n" +
"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
"    <style type=\"text/css\">\n" +
"        @media screen {\n" +
"            @font-face {\n" +
"                font-family: 'Lato';\n" +
"                font-style: normal;\n" +
"                font-weight: 400;\n" +
"                src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\n" +
"            }\n" +
"\n" +
"            @font-face {\n" +
"                font-family: 'Lato';\n" +
"                font-style: normal;\n" +
"                font-weight: 700;\n" +
"                src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\n" +
"            }\n" +
"\n" +
"            @font-face {\n" +
"                font-family: 'Lato';\n" +
"                font-style: italic;\n" +
"                font-weight: 400;\n" +
"                src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\n" +
"            }\n" +
"\n" +
"            @font-face {\n" +
"                font-family: 'Lato';\n" +
"                font-style: italic;\n" +
"                font-weight: 700;\n" +
"                src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\n" +
"            }\n" +
"        }\n" +
"\n" +
"        /* CLIENT-SPECIFIC STYLES */\n" +
"        body,\n" +
"        table,\n" +
"        td,\n" +
"        a {\n" +
"            -webkit-text-size-adjust: 100%;\n" +
"            -ms-text-size-adjust: 100%;\n" +
"        }\n" +
"\n" +
"        table,\n" +
"        td {\n" +
"            mso-table-lspace: 0pt;\n" +
"            mso-table-rspace: 0pt;\n" +
"        }\n" +
"\n" +
"        img {\n" +
"            -ms-interpolation-mode: bicubic;\n" +
"        }\n" +
"\n" +
"        /* RESET STYLES */\n" +
"        img {\n" +
"            border: 0;\n" +
"            height: auto;\n" +
"            line-height: 100%;\n" +
"            outline: none;\n" +
"            text-decoration: none;\n" +
"        }\n" +
"\n" +
"        table {\n" +
"            border-collapse: collapse !important;\n" +
"        }\n" +
"\n" +
"        body {\n" +
"            height: 100% !important;\n" +
"            margin: 0 !important;\n" +
"            padding: 0 !important;\n" +
"            width: 100% !important;\n" +
"        }\n" +
"\n" +
"        /* iOS BLUE LINKS */\n" +
"        a[x-apple-data-detectors] {\n" +
"            color: inherit !important;\n" +
"            text-decoration: none !important;\n" +
"            font-size: inherit !important;\n" +
"            font-family: inherit !important;\n" +
"            font-weight: inherit !important;\n" +
"            line-height: inherit !important;\n" +
"        }\n" +
"\n" +
"        /* MOBILE STYLES */\n" +
"        @media screen and (max-width:600px) {\n" +
"            h1 {\n" +
"                font-size: 32px !important;\n" +
"                line-height: 32px !important;\n" +
"            }\n" +
"        }\n" +
"\n" +
"        /* ANDROID CENTER FIX */\n" +
"        div[style*=\"margin: 16px 0;\"] {\n" +
"            margin: 0 !important;\n" +
"        }\n" +
"    </style>\n" +
"</head>\n" +
"\n" +
"<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">\n" +
"    <!-- HIDDEN PREHEADER TEXT -->\n" +
"    <div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Lato', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\"> We're thrilled to have you here! Get ready to dive into your new account.\n" +
"    </div>\n" +
"    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"        <!-- LOGO -->\n" +
"        <tr>\n" +
"            <td bgcolor=\"#FFA73B\" align=\"center\" style=\"background-color: #0e69af;\">\n" +
"                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
"                    <tr>\n" +
"                        <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\n" +
"                    </tr>\n" +
"                </table>\n" +
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td bgcolor=\"#FFA73B\" align=\"center\" style=\"padding: 0px 10px 0px 10px; background-color: #0e69af;\" >\n" +
"                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
"                    <tr>\n" +
"                        <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\n" +
"                            <h1 style=\"font-size: 48px; font-weight: 400; margin: 2;\">Welcome!</h1> <img src=\" https://img.icons8.com/clouds/100/000000/handshake.png\" width=\"125\" height=\"120\" style=\"display: block; border: 0px;\" />\n" +
"                        </td>\n" +
"                    </tr>\n" +
"                </table>\n" +
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\" >\n" +
"                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
"                    <tr>\n" +
"                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
"                            <p style=\"margin: 0;\">Account registration confirmation email.</p>\n" +
"                        </td>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                        <td bgcolor=\"#ffffff\" align=\"left\">\n" +
"                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                <tr>\n" +
"                                    <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 60px 30px;\">\n" +
"                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                            <tr>\n" +
"                                                <td align=\"center\" style=\"border-radius: 3px; background-color: #0e69af;\" bgcolor=\"#FFA73B\">\n" +
"                                                    <a href=\"http://localhost:8084/G6_swp/registration?email2="+emailcode+"\" target=\"_blank\" style=\"font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 2px; border: 1px solid #0e69af; display: inline-block;\">\n" +
"                                                    Confirm Account</a></td>\n" +
"                                            </tr>\n" +
"                                        </table>\n" +
"                                    </td>\n" +
"                                </tr>\n" +
"                            </table>\n" +
"                        </td>\n" +
"                    </tr> <!-- COPY -->\n" +
" <!-- COPY -->\n" +
"\n" +
"                    </tr>\n" +
"                </table>\n" +
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 30px 10px 0px 10px;\">\n" +
"                <table style=\"margin-left:228px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
"                    <tr>\n" +
"                        <td bgcolor=\"#FFECD1\" align=\"center\" style=\"padding: 30px 30px 30px 30px; border-radius: 4px 4px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px; background-color: #cbe9ef;\">\n" +
"                            <h2 style=\"font-size: 20px; font-weight: 400; color: #0e69af; margin: 0;\">Your information will be kept confidential.</h2>\n" +
"                            <p style=\"margin: 0;\"><a href=\"#\" target=\"_blank\" style=\"color: #0e69af;\"></a></p>\n" +
"                        </td>\n" +
"                    </tr>\n" +
"                </table>\n" +
"            </td>\n" +
"        </tr>\n" +
"    </table>\n" +
"</body>\n" +
"\n" +
"</html>";
        UserDB udb = new UserDB();
        if(!mail1.endsWith("@fpt.edu.vn")){
            
                String Notification1 = "this is not a university email FPT!";
                request.setAttribute("notification1", Notification1);
                request.getRequestDispatcher("user/CheckMail.jsp").forward(request, response);
            
        }else{
            if(udb.getUserEmail(mail1)){
                String Notification1 = "The Email was registered!";
                request.setAttribute("notification1", Notification1);
                request.getRequestDispatcher("user/CheckMail.jsp").forward(request, response);
            }
            CheckMail e = new CheckMail();
            
            
            e.send(mail1, subject, message, "0362351671trai@gmail.com", "0362351671");
            
            String Notification1 = "Sent confirmation to your email!";
            request.setAttribute("notification1", Notification1);
            request.getRequestDispatcher("user/CheckMail.jsp").forward(request, response);
        }
}else{
      
        UserDB db = new UserDB();
        List<User> listuser = db.getAllUser();
        
        String Rollnumber = "0";
        String fullname2 = request.getParameter("fullname");
//        String fullname2 = fullname.replaceAll("\\s+", " ");// gom spase
//        fullname2 = fullname2.trim().replaceAll("\\s+", " ");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String re_password = request.getParameter("re_password");
        
//mã hóa password
        String password2 =null;
        Encode en = new Encode();
        try {
            password2 = en.encrypt(password);
        } catch (Exception e) {
            password2=null;
        }
//mã hóa password
        
        String mobile = request.getParameter("mobile");
        String date = request.getParameter("date");
        Date dob = Date.valueOf(date);
        String gender = request.getParameter("gender");
        boolean g1 = false;
        if(gender.equals("male")){
            g1= true;
        }
        
// xóa kí tự đặc biệt trong input fullname
        String fullname3 ="";
        String specialChars2 = "`~!@#$%^&*()_+[]\\;\',./{}|:\"<>?";
        for (int i = 0; i < fullname2.length(); i++) {
            boolean check = true;
            for (int j = 0; j < specialChars2.length(); j++) {
                if(specialChars2.charAt(j)==fullname2.charAt(i)){
                    check= false;
                }
            }
            if(check){
                fullname3 +=fullname2.charAt(i);
            }
        }
        fullname3 = fullname3.trim().replaceAll("\\s+", " ");
        fullname3 = fullname3.replaceAll("\\s+", " ");
// xóa kí tự đặc biệt trong input fullname

// lấy rollnumber
        String roll_number2 = "";
        for (int i = email.length()-1; i >= 0 ; i--) {
            if(email.charAt(i)=='@'){
                for (int j = 1; j <= 8; j++) {
                    roll_number2 += email.charAt(i-j);
                }
            }
        }
        StringBuffer r3  = new StringBuffer(roll_number2);
        roll_number2 = r3.reverse().toString();
// lấy rollnumber
        
// check fullname, password, mobile 
        if(fullname2.equals("")||fullname2.equals(" ")||fullname2==null||!email.endsWith("@fpt.edu.vn")){
            String Notification1 = "don't leave it empty!";
            request.setAttribute("notification1", Notification1);
            request.getRequestDispatcher("user/Registration.jsp").forward(request, response);
            
        }else{
            if(!password.equals(re_password)){
                String Notification1 = "password does not match!";
                request.setAttribute("notification1", Notification1);
                request.getRequestDispatcher("user/Registration.jsp").forward(request, response);
            }
            if(mobile.length()!=10){
                String Notification1 = "This is not a personal phone number!";
                request.setAttribute("notification1", Notification1);
                request.getRequestDispatcher("user/Registration.jsp").forward(request, response);
            }else{
                User a = new User(0,roll_number2,fullname3,g1,dob,email,mobile,null,null,password2,"student",true);
                db.insertUserRegistration(a); // insertUser
            }
        }
// check fullname, password, mobile     

        String Notification2 = "You have successfully registered!";
        request.setAttribute("notification", Notification2);
        request.getRequestDispatcher("user/Login.jsp").forward(request, response);
            
}
      
        
        
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
