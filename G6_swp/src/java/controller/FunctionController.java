/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.ClassDB;
import dal.ClassUserDB;
import dal.FeatureDB;
import dal.FunctionDB;
import dal.SubjectSettingDB;
import dal.TeamDB;
import dal.UserDB;
import extension.CheckMail;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Feature;
import model.Function;
import model.SubjectSetting;
import model.Team;
import model.User;
import model.Class;

@MultipartConfig(
        maxFileSize = 1024 * 1024 * 15 // 15 MB
)
public class FunctionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        FunctionDB fudb = new FunctionDB();
        ClassUserDB cudb = new ClassUserDB();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("useraccount");

        String funtion = request.getParameter("funtion");
        String search = request.getParameter("search");
        String classid = request.getParameter("classid");
        String teamid = request.getParameter("teamid");
        String settingid = request.getParameter("settingid");
        String priority = request.getParameter("priority");
        String status = request.getParameter("status");
        String checkclass = request.getParameter("checkclass");
        String functionid = request.getParameter("functionid");
        if(checkclass == null){checkclass="null";}
        String Page = request.getParameter("Page");

        if (Page == null || Page.isEmpty()) {
            Page = "1";
        }

        if (funtion == null || funtion.isEmpty()) {
            int count = 0;
            int sizeinpage = 5;
            int du = 0;
            count = fudb.getCountPage(search, teamid, settingid, priority, status) / sizeinpage;
            du = fudb.getCountPage(search, teamid, settingid, priority, status) % sizeinpage;

            
            ArrayList<Class> classlistTrainer = cudb.getClasslistTrainer(user.getUserId());
            if(user.getRoleId().equals("student")){
                classlistTrainer = fudb.getClasslistStudent(user.getUserId());
            }
            
            ArrayList<Team> TeamListTrainer = new ArrayList<>();
            ArrayList<SubjectSetting> lsubjec = new ArrayList<>();
            ArrayList<Function> ListFunctions = new ArrayList<>();
            ArrayList<Function> ListAllFunctions = new ArrayList<>();

            if (classid == null || classid.isEmpty()) {
                if(user.getRoleId().equals("student")){
                    TeamListTrainer.add(cudb.getCheckClassbUserbyUserid(user.getUserId(), Integer.valueOf(classlistTrainer.get(0).getClassId())).getTeam());
                }else{TeamListTrainer = cudb.getListTeamidInClass(classlistTrainer.get(0).getClassId());}
                lsubjec = fudb.getListSubjectSettingBySubjectid(classlistTrainer.get(0).getClassId());
                request.setAttribute("classCodeImport", classlistTrainer.get(0).getClassId());
            } else {
                if(user.getRoleId().equals("student")){
                    TeamListTrainer.add(cudb.getCheckClassbUserbyUserid(user.getUserId(), Integer.valueOf(classid)).getTeam());
                }else{TeamListTrainer = cudb.getListTeamidInClass(Integer.valueOf(classid));}
                lsubjec = fudb.getListSubjectSettingBySubjectid(Integer.valueOf(classid));
            }
            
            if (teamid == null || teamid.isEmpty()) {

                if (TeamListTrainer.size() == 0) {
                    count = 0;
                    du = 0;
                    ListFunctions = null;
                    ListAllFunctions = null;
                    request.setAttribute("teamidImport", "not yet");
                } else {
                    request.setAttribute("teamidImport", TeamListTrainer.get(0).getTeamId());
                    ListFunctions = fudb.getFunctionbyTeamid(TeamListTrainer.get(0).getTeamId());
                    count = fudb.getCountPage(search, String.valueOf(TeamListTrainer.get(0).getTeamId()), settingid, priority, status) / sizeinpage;
                    du = fudb.getCountPage(search, String.valueOf(TeamListTrainer.get(0).getTeamId()), settingid, priority, status) % sizeinpage;
                    ListAllFunctions = fudb.getFunctionListPage(search, String.valueOf(TeamListTrainer.get(0).getTeamId()), settingid, priority, status, Integer.valueOf(Page), sizeinpage);
                    teamid = String.valueOf(TeamListTrainer.get(0).getTeamId());
                }

            } else {
                request.setAttribute("teamidImport", teamid);
                if (checkclass.equals("null")||checkclass==null) {
                    ListFunctions = fudb.getFunctionbyTeamid(Integer.valueOf(teamid));
                    ListAllFunctions = fudb.getFunctionListPage(search, teamid, settingid, priority, status, Integer.valueOf(Page), sizeinpage);
                } else {
                    if (TeamListTrainer.size() != 0) {
                        request.setAttribute("teamidImport", TeamListTrainer.get(0).getTeamId());
                        ListFunctions = fudb.getFunctionbyTeamid(TeamListTrainer.get(0).getTeamId());
                        ListAllFunctions = fudb.getFunctionListPage(search, String.valueOf(TeamListTrainer.get(0).getTeamId()), settingid, priority, status, Integer.valueOf(Page), sizeinpage);
                        count = fudb.getCountPage(search, String.valueOf(TeamListTrainer.get(0).getTeamId()), settingid, priority, status) / sizeinpage;
                        du = fudb.getCountPage(search, String.valueOf(TeamListTrainer.get(0).getTeamId()), settingid, priority, status) % sizeinpage;
                    } else {
                        request.setAttribute("teamidImport", "not yet");
                        count = 0;
                        du = 0;
                        ListFunctions = null;
                        ListAllFunctions = null;
                    }
                }

            }
            if (du > 0) {
                count += 1;
            }

            if (classid != null && !classid.isEmpty()) {
                request.setAttribute("classCodeImport", classid);
            }
            request.setAttribute("checkclass", checkclass);
            request.setAttribute("count", count);
            request.setAttribute("search", search);
            request.setAttribute("classid", classid);
            request.setAttribute("teamid", teamid);
            request.setAttribute("settingid", settingid);
            request.setAttribute("priority", priority);
            request.setAttribute("status", status);
            request.setAttribute("Page", Page);
            request.setAttribute("functionlist", ListAllFunctions);
            request.setAttribute("prioritylist", ListFunctions);
            request.setAttribute("listsubjectsetting", lsubjec);
            request.setAttribute("teamlist", TeamListTrainer);
            request.setAttribute("classlist", classlistTrainer);
            
            String thongbao1 = request.getParameter("thongbao1");
            request.setAttribute("thongbao1", thongbao1);
            request.getRequestDispatcher("function/FunctionList.jsp").forward(request, response);
        }
        if (funtion.equals("add")) {
            FunctionDB db = new FunctionDB();
            ClassUserDB db2 = new ClassUserDB();
            int Classid2 = 0;
            if (classid == null || classid.isEmpty()) {
                ArrayList<Class> classlistTrainer2 = cudb.getClasslistTrainer(user.getUserId());
                if(user.getRoleId().equals("student")){
                classlistTrainer2 = fudb.getClasslistStudent(user.getUserId());
            }
                Classid2 = classlistTrainer2.get(0).getClassId();
            }else{
                Classid2 = Integer.parseInt(classid);
            }
            if (classid != null && !classid.isEmpty()) {
                request.setAttribute("classCodeImport", classid);
            }
            Team teamuser =null;
            
            if (teamid == null || teamid.isEmpty()) { // trainer
                ArrayList<Team> TeamListTrainer3 = cudb.getListTeamidInClass(Integer.valueOf(classid));
                teamuser = TeamListTrainer3.get(0);
            }else{
                teamuser = db2.getTeambyid(Integer.parseInt(teamid));
            }
            
            
            if(user.getRoleId().equals("student")){// team student
                 teamuser = db2.getCheckClassbUserbyUserid(user.getUserId(), Classid2).getTeam();
            }
            
            ArrayList<Feature> fea = db.getFeaturebyTeamid(teamuser.getTeamId());
            ArrayList<SubjectSetting> lsubjec = db.getListSubjectSettingBySubjectid(Classid2);

            request.setAttribute("teamname", teamuser);
            request.setAttribute("userid", user.getUserId());
            request.setAttribute("featurelist", fea);
            request.setAttribute("listsubjectsetting", lsubjec);
            request.setAttribute("thongbao2", "add new function");
            request.getRequestDispatcher("function/FunctionAdd.jsp").forward(request, response);
        }
        if (funtion.equals("import2")) {
            ArrayList<Function> ListFu = new ArrayList<>();
            CheckMail.ExportFileExcelFunction(ListFu, response, "", "", "ko null");
        }
        if (funtion.equals("detail")) {
            Function fd = fudb.getFunctionbyid(functionid);
            FunctionDB db = new FunctionDB();
            ClassUserDB db2 = new ClassUserDB();
            
            ArrayList<Feature> fea = db.getFeaturebyTeamid(fd.getTeam().getTeamId());
            ArrayList<SubjectSetting> lsubjec = db.getListSubjectSettingBySubjectid(fd.getTeam().getClassId().getClassId());
            
            request.setAttribute("featurelist", fea);
            request.setAttribute("listsubjectsetting", lsubjec);
            request.setAttribute("functionid", functionid);
            request.setAttribute("thongbao2", "detail");
            request.setAttribute("status", fd.getStatus());
            request.getRequestDispatcher("function/FunctionAdd.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String funtion = request.getParameter("funtion");
        String userid = request.getParameter("userid");
        String teamId = request.getParameter("teamId");
        String functionname = request.getParameter("functionname");
        String featureid = request.getParameter("featureid");
        String accessroles = request.getParameter("accessroles");
        String description = request.getParameter("description");
        String complexity = request.getParameter("complexity");
        String priority = request.getParameter("priority");
        String status = request.getParameter("status");
        HttpSession session = request.getSession();
         User user = (User) session.getAttribute("useraccount");
        String url = (String) session.getAttribute("pre_url");

        TeamDB tdb = new TeamDB();
        FeatureDB feadb = new FeatureDB();
        SubjectSettingDB sudb = new SubjectSettingDB();
        UserDB udb = new UserDB();
        FunctionDB fudb = new FunctionDB();
        ClassDB cldb = new ClassDB();
        String funtiondetail = request.getParameter("funtiondetail");
        if(funtiondetail==null||funtiondetail.isEmpty()){funtiondetail="";}
        if (funtion.equals("add")&&funtiondetail.isEmpty()) {
            Function f = new Function(0, tdb.getTeam(teamId), functionname, feadb.getFeatureByID(Integer.parseInt(featureid)), accessroles, description, fudb.getSubjectSettingid(Integer.parseInt(complexity)), udb.getUser(user.getUserId()), Integer.parseInt(priority), Integer.parseInt(status));
            fudb.insertFunction(f);
            String thongbao = "Add new Function successfully!";
            response.sendRedirect("function?thongbao1="+thongbao);
        }
        if(funtiondetail.equals("funtiondetail")){
            String funtionid = request.getParameter("funtionid");
            Function f = new Function(Integer.valueOf(funtionid), tdb.getTeam(teamId), functionname, feadb.getFeatureByID(Integer.parseInt(featureid)), accessroles, description, fudb.getSubjectSettingid(Integer.parseInt(complexity)), udb.getUser(Integer.parseInt(userid)), Integer.parseInt(priority), Integer.parseInt(status));
            fudb.updateFunction(f);
            String thongbao = "Change Function information successfully!";
            response.sendRedirect("function?thongbao1="+thongbao);
        }
        if (funtion.equals("export")) {

            ArrayList<Function> ListFu = fudb.getFunctionbyTeamid2(Integer.parseInt(teamId));
            CheckMail.ExportFileExcelFunction(ListFu, response, tdb.getTeam(teamId).getClassId().getClassCode(), tdb.getTeam(teamId).getTeamCode(), null);

            //response.sendRedirect("function");
            //request.getRequestDispatcher(url).forward(request, response);
        }
        if (funtion.equals("import")) {
            String classCodeImport = request.getParameter("classCodeImport");
            String teamidImport = request.getParameter("teamidImport");
            Part fileimport = request.getPart("fileImport");
            if (!teamidImport.isEmpty() || teamidImport != null||fileimport.getSize() == 0) {
                InputStream is = fileimport.getInputStream();
                ArrayList<Function> ListFu1 = CheckMail.readExcelFunction(is,Integer.parseInt(teamidImport));
                int countup = 0;
                int countadd = 0;
                for (Function f : ListFu1) {
                    if (fudb.checkFuntion(f.getFunctionName(),Integer.parseInt(teamidImport)) != -1) { // Function Name co chua (update)
                        int idf = fudb.checkFuntion(f.getFunctionName(),Integer.parseInt(teamidImport));
                        f.setId(idf);
                        f.setUser(user);
                        fudb.updateFunction(f);
                        countup++;
                    } else { // (Insert)
                        f.setUser(user);
                        fudb.insertFunction(f);
                        countadd++;
                    }
                }
                
                String thongbao = "successfully added "+countadd+" Functions <br>" +
                                  "successfully updated "+countup+" Functions";
                response.sendRedirect("function?thongbao1="+thongbao);
            }else{
                String thongbao = "No team yet";
                response.sendRedirect("function?thongbao1="+thongbao);
            }

        }
        if(funtion.equals("changestatus")){
            String fuidstatus = request.getParameter("fuidstatus");
            String statusid = request.getParameter("statuschange");
            Function fst = new Function();
            fst.setId(Integer.parseInt(fuidstatus));
            fst.setStatus(Integer.parseInt(statusid));
            fudb.updateFunctionStatus(fst);
            
            String thongbao = "Successful change";
            response.sendRedirect("function?thongbao1="+thongbao);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
