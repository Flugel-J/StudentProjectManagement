/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.ClassUserDB;
import dal.UserDB;
import extension.CheckMail;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Class;
import model.ClassUser;
import model.Team;
import model.User;

@MultipartConfig(
        maxFileSize = 1024 * 1024 * 15 // 15 MB
)

public class ClassUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("useraccount");

        String funtion = request.getParameter("funtion");
        String class_code = request.getParameter("class_code");
        String class_year = request.getParameter("class_year");
        String class_term = request.getParameter("class_term");
        String team_leader = request.getParameter("team_leader");
        String dropout_date = request.getParameter("dropout_date");
        String status = request.getParameter("status");
        String search = request.getParameter("search");
        String Page = request.getParameter("Page");
        if (Page == null) {
            Page = "1";
        }
        int sizeinpage = 5;

        ClassUserDB cdb = new ClassUserDB();

//      Class user list        
        if (funtion == null) {//list class user

            ArrayList<Class> classlist = cdb.getClasslistTrainer(user.getUserId());
            ArrayList<String> classyear = cdb.getListClassYear(user.getUserId());
            if (class_code == null) {
                class_code = classlist.get(0).getClassCode();
            }
            int count = cdb.getCountClassuser(team_leader, class_code, dropout_date, status, class_year,
                    class_term, search, search) / sizeinpage;
            int du = cdb.getCountClassuser(team_leader, class_code, dropout_date, status, class_year,
                    class_term, search, search) % sizeinpage;
            if (du > 0) {
                count += 1;
            }
            ArrayList<ClassUser> classuserlist
                    = cdb.getClassUserlistPage(team_leader, class_code, dropout_date, status, class_year,
                            class_term, search, search, Integer.parseInt(Page), sizeinpage);

            request.setAttribute("funtion", funtion);
            request.setAttribute("classCode", class_code);
            request.setAttribute("classYear", class_year);
            request.setAttribute("classterm", class_term);
            request.setAttribute("teamleader", team_leader);
            request.setAttribute("dropoutdate", dropout_date);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("search", search);
            request.setAttribute("Page", Page);
            request.setAttribute("count", count);
            request.setAttribute("classuserlist", classuserlist);
            request.setAttribute("classlist", classlist);
            request.setAttribute("classyear", classyear);
            String thongbaodetail = request.getParameter("thongbaodetail");
            String thongbaoexport = request.getParameter("thongbaoexport");
            if (thongbaodetail != null) {
                request.setAttribute("Thongbaode", "Successfully changed student information!");
            }
            if (thongbaoexport != null) {
                request.setAttribute("thongbaoexport", "The Excel Class user file has been successfully sent to your email!");
            }
            request.getRequestDispatcher("class_user/ClassUserList.jsp").forward(request, response);
        }
//      Class user list   
//      Class user  status
        if (funtion.equals("status")) {
            String userid = request.getParameter("userid");
            String classid = request.getParameter("classid");
            String teamid = request.getParameter("teamid");
            String statususer = request.getParameter("statususer");
            boolean status2 = true;
            if (statususer.equals("true")) {
                status2 = false;
            }
            cdb.changeStatusClassuser(status2, Integer.parseInt(teamid), Integer.parseInt(classid), Integer.parseInt(userid));
            ArrayList<Class> classlist = cdb.getClasslistTrainer(user.getUserId());
            ArrayList<String> classyear = cdb.getListClassYear(user.getUserId());
            int count = cdb.getCountClassuser(team_leader, class_code, dropout_date, status, class_year,
                    class_term, search, search) / sizeinpage;
            int du = cdb.getCountClassuser(team_leader, class_code, dropout_date, status, class_year,
                    class_term, search, search) % sizeinpage;
            if (du > 0) {
                count += 1;
            }
            ArrayList<ClassUser> classuserlist
                    = cdb.getClassUserlistPage(team_leader, class_code, dropout_date, status, class_year,
                            class_term, search, search, Integer.parseInt(Page), sizeinpage);
            request.setAttribute("funtion", funtion);
            request.setAttribute("classCode", class_code);
            request.setAttribute("classYear", class_year);
            request.setAttribute("classterm", class_term);
            request.setAttribute("teamleader", team_leader);
            request.setAttribute("dropoutdate", dropout_date);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("search", search);
            request.setAttribute("Page", Page);
            request.setAttribute("count", count);
            request.setAttribute("classuserlist", classuserlist);
            request.setAttribute("classlist", classlist);
            request.setAttribute("classyear", classyear);
            request.getRequestDispatcher("class_user/ClassUserList.jsp").forward(request, response);
        }
//      Class user status
        if (funtion.equals("detail")) {

            String userid = request.getParameter("userid");
            String classid = request.getParameter("classid");
            String teamid = request.getParameter("teamid");
            ClassUser cu = cdb.getClassbUser(Integer.parseInt(userid), Integer.parseInt(teamid), Integer.parseInt(classid));
            ArrayList<Team> listteamid = cdb.getListTeamidInClass(cu.getClassId().getClassId());

            request.setAttribute("classuser", cu);
            request.setAttribute("listteamid", listteamid);
//            response.getWriter().println(cu.getOngoingEval());
            request.getRequestDispatcher("class_user/ClassUserDetails.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String funtion = request.getParameter("funtion");
        String classId = request.getParameter("classId");
        String userId = request.getParameter("userId");
        String teamid = request.getParameter("TeamId");
        String teamleader = request.getParameter("teamleader");
        String dropoutdate = request.getParameter("dropoutdate");
        String ongoingeval = request.getParameter("ongoingeval");
        String finalpreseval = request.getParameter("finalpreseval");
        String finaltopiceval = request.getParameter("finalpreseval");
        String usernote = request.getParameter("usernote");
        String class_codeExcel = request.getParameter("class_codeExcel");
        ClassUserDB cdb = new ClassUserDB();
        if (funtion.equals("export")) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("useraccount");
            ArrayList<ClassUser> c = cdb.getListClassUserByClassCode(class_codeExcel);
            CheckMail.ExportFileExcel(c);
            String subject = "File Excel class User " + class_codeExcel + ".";
            String message = "<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "\n"
                    + "<head>\n"
                    + "</head>\n"
                    + "\n"
                    + "<body>\n"
                    + "    <h3 style=\"color: blue;\">Account registration confirmation email.</h3>\n"
                    + "    <div>Click here: <a href=\"http://localhost:8084/G6_swp/registration\" style=\"color: brown; font-size: 20px;\">confirm</a></div>\n"
                    + "\n"
                    + "</body>\n"
                    + "\n"
                    + "</html>";
            CheckMail.sendFile(user.getEmail(), subject, message, "traicvhe153014@fpt.edu.vn", "12345678", class_codeExcel + "-ClassUser");
            response.sendRedirect("classuser?thongbaoexport=notnull");
        }
        if (funtion.equals("updateclassuser")) {
            String teamchange = request.getParameter("teamchange");
            Date dropout = null;
            if (!dropoutdate.isEmpty()) {
                dropout = Date.valueOf(dropoutdate);
            }
            cdb.updateClassUser(Integer.parseInt(classId), Integer.parseInt(teamid), Integer.parseInt(userId), Boolean.parseBoolean(teamleader), dropout, usernote, Integer.parseInt(teamchange));
            response.sendRedirect("classuser?thongbaodetail=k");
        }
        if (funtion.equals("import")) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("useraccount");
            UserDB ur = new UserDB();
            //User user1 = ;
            String ClassImport = request.getParameter("classimport");
            Part fileimport = request.getPart("fileImport");
            int countupdate = 0;
            int countInsertClassUser = 0;
            if (fileimport.getSize() > 0) {
                InputStream is = fileimport.getInputStream();
                //FileInputStream f = new  FileInputStream(is);
                CheckMail cmail = new CheckMail();
                ArrayList<ClassUser> listclassuser = cmail.readExcel(is, cdb.getClassbyClassCodeTrainerid(user.getUserId(), ClassImport).getClassId());
                for (ClassUser c : listclassuser) {
                    if (cdb.getCheckClassbUser(c.getUser().getUserId(), c.getTeam().getTeamId(), c.getClassId().getClassId()) != null) {
                        try {
                            cdb.updateClassUserImport(c.getClassId().getClassId(), c.getTeam().getTeamId(), c.getUser().getUserId(), c.isTeamLeader(), c.getDropoutDate(), c.getUserNotes(), (float) c.getOngoingEval(), (float) c.getFinalPresEval(), (float) c.getFinalTopicEval());
                            countupdate++;
                        } catch (Exception e) {
                        }
                    } else {

                        if (ur.getUserAccount(c.getUser().getEmail(), c.getUser().getPassword()) != null) {

                            if (cdb.getCheckClassbUserbyUserid(c.getUser().getUserId(), c.getClassId().getClassId()) != null) {//check user in class user update team
                                try {
                                    cdb.updateClassUserImport(c.getClassId().getClassId(), c.getTeam().getTeamId(), c.getUser().getUserId(), c.isTeamLeader(), c.getDropoutDate(), c.getUserNotes(), (float) c.getOngoingEval(), (float) c.getFinalPresEval(), (float) c.getFinalTopicEval());
                                    countupdate++;
                                } catch (Exception e) {
                                }
                            } else {
                                try {
                                    cdb.insertClassUser(c);
                                    countInsertClassUser++;
                                } catch (Exception e) {
                                }
                            }

                        }
                    }

                }

            }

            String class_code = request.getParameter("class_code");
            String class_year = request.getParameter("class_year");
            String class_term = request.getParameter("class_term");
            String team_leader = request.getParameter("team_leader");
            String dropout_date = request.getParameter("dropout_date");
            String status = request.getParameter("status");
            String search = request.getParameter("search");
            String Page = request.getParameter("Page");
            if (Page == null) {
                Page = "1";
            }
            int sizeinpage = 3;

            ArrayList<Class> classlist = cdb.getClasslistTrainer(user.getUserId());
            ArrayList<String> classyear = cdb.getListClassYear(user.getUserId());
            if (class_code == null) {
                class_code = classlist.get(0).getClassCode();
            }
            int count = cdb.getCountClassuser(team_leader, class_code, dropout_date, status, class_year,
                    class_term, search, search) / sizeinpage;
            int du = cdb.getCountClassuser(team_leader, class_code, dropout_date, status, class_year,
                    class_term, search, search) % sizeinpage;
            if (du > 0) {
                count += 1;
            }
            ArrayList<ClassUser> classuserlist
                    = cdb.getClassUserlistPage(team_leader, class_code, dropout_date, status, class_year,
                            class_term, search, search, Integer.parseInt(Page), sizeinpage);

            request.setAttribute("funtion", funtion);
            request.setAttribute("classCode", class_code);
            request.setAttribute("classYear", class_year);
            request.setAttribute("classterm", class_term);
            request.setAttribute("teamleader", team_leader);
            request.setAttribute("dropoutdate", dropout_date);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("search", search);
            request.setAttribute("Page", Page);
            request.setAttribute("count", count);
            request.setAttribute("classuserlist", classuserlist);
            request.setAttribute("classlist", classlist);
            request.setAttribute("classyear", classyear);

            String thongbao = "Updated the information of <span style=\"color: blue\">" + countupdate + "</span> students in the table \"Class User\"<br>\n"
                    + "add <span style=\"color: blue\">" + countInsertClassUser + "</span> new student to the table \"Class User\" to class " + ClassImport + ".";
            request.setAttribute("thongbao", thongbao);
            request.getRequestDispatcher("class_user/ClassUserList.jsp").forward(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
