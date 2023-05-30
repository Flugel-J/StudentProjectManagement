/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.ClassDB;
import dal.SubjectDB;
import dal.UserDB;
import extension.LogicalProcess;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import model.Class;

/**
 *
 * @author KHANHHERE
 */
public class ClassController extends HttpServlet {

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
            out.println("<title>Servlet ClassController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ClassController at " + request.getContextPath() + "</h1>");
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
        String tag = request.getParameter("tag");
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("useraccount");
         String uSuccess = (String) session.getAttribute("update_success");
        String aSuccess = (String) session.getAttribute("add_success");
        session.removeAttribute("update_success");
        session.removeAttribute("add_success");
        if(uSuccess != null) request.setAttribute("success", "uSuccess");
        if(aSuccess != null) request.setAttribute("success", "aSuccess");
        ClassDB cdb = new ClassDB();
        String id = request.getParameter("id");
        if (tag == null) {
            // change status           
            if (id != null) {
                String author = null;
                if (u.getRoleId().equalsIgnoreCase("admin")) {
                    author = "";
                }
                if (u.getRoleId().equalsIgnoreCase("author")) {
                    author = u.getUserId() + "";
                }
                String trainer = "";
                if (u.getRoleId().equalsIgnoreCase("trainer")) {
                    trainer = u.getUserId() + "";
                }
                Class c = cdb.getClassById(id, author, trainer);
                if (c == null) {
                    response.sendRedirect("class");
                } else {
                    int statusNow = c.getStatus();
                    int statusUpdate = (statusNow + 1) > 3 ? 1 : (statusNow + 1);
                    c.setStatus(statusUpdate);
                    cdb.UpdateClass(c);
                    String url = (String) session.getAttribute("pre_url");
                    response.sendRedirect(url);
                }
                return;
            }
            // list
            String search_term = request.getParameter("search_term");
            String search_status = request.getParameter("search_status");
            String search_value = request.getParameter("search_value");
            if (search_term == null) {
                search_term = "";
            }
            if (search_status == null) {
                search_status = "";
            }
            if (search_value == null) {
                search_value = "";
            }
            String author = "";
            if (u.getRoleId().equalsIgnoreCase("admin")) {
                author = "";
            }
            if (u.getRoleId().equalsIgnoreCase("author")) {
                author = u.getUserId() + "";
            }
            String trainer = "";
            if (u.getRoleId().equalsIgnoreCase("trainer")) {
                trainer = u.getUserId() + "";
            }
            int size = cdb.count(search_term, search_status, search_value, author, trainer);
            int curPage, numPerPage = 4;
            int NoPage = (size % numPerPage == 0 ? (size / numPerPage) : (size / numPerPage + 1));
            String curPage_raw = request.getParameter("curPage");
            if (curPage_raw == null) {
                curPage = 1;
            } else {
                curPage = Integer.parseInt(curPage_raw);
            }
            List<Class> listPerPage = cdb.search(search_term, search_status, search_value, author, trainer, curPage, numPerPage);
            request.setAttribute("curPage", curPage);
            request.setAttribute("listPerPage", listPerPage);
            request.setAttribute("NoPage", NoPage);
            request.setAttribute("search_term", search_term);
            request.setAttribute("search_status", search_status);
            request.setAttribute("search_value", search_value);
            request.getRequestDispatcher("class/ClassList.jsp").forward(request, response);
        } else {
            LinkedHashMap<String, String> subject;
            if ("author".equals(u.getRoleId())) {
                subject = cdb.SubjectList(u.getUserId());
            } else {
                subject = cdb.SubjectList();
            }
            if (!subject.containsKey(id) && tag == "Update") {

                String url = (String) session.getAttribute("pre_url");
                response.sendRedirect(url);
                return;
            }

            Class c = cdb.getClassById(id);
            LinkedHashMap<String, String> trainer = cdb.TrainerList();

            request.setAttribute("trainerList", trainer);
            request.setAttribute("subjectList", subject);
            request.setAttribute("aclass", c);
            request.setAttribute("tag", tag);
            request.getRequestDispatcher("class/Detail.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("useraccount");
        LogicalProcess lp = new LogicalProcess();
        String id = request.getParameter("id");
        String code = lp.filterString(request.getParameter("code"));
        String trainer = request.getParameter("trainer");
        String subject = request.getParameter("subject");
        String year = request.getParameter("year");
        String term = lp.filterString(request.getParameter("term"));
        boolean block5;
        String status = request.getParameter("status");
        block5 = request.getParameter("bl5") != null;

        UserDB udb = new UserDB();
        SubjectDB sdb = new SubjectDB();
        ClassDB cdb = new ClassDB();
        Class c0= cdb.getClassById(id);
        Class c2= new Class();
        c2.setClassCode(c0!=null?c0.getClassCode():null);
        c2.setSubject(c0!=null?c0.getSubject():null);
        Class c = new Class();
        c.setClassCode(code);
        c.setTrainer(udb.getUser(Integer.parseInt(trainer)));
        c.setSubject(sdb.getSubjectById(subject));
        c.setClassYear(Integer.parseInt(year));
        c.setTerm(Integer.parseInt(term));
        c.setBlock5Class(block5);
        c.setStatus(Integer.parseInt(status));
        if (cdb.checkClassAndSubject(code, subject)&&
        ((c2.getClassCode() == null ? code != null : !c2.getClassCode().equals(code)) ||
        (Integer.toString(c2.getSubject().getSubjectId()) == null ? (subject) != null : !Integer.toString(c2.getSubject().getSubjectId()).equals(subject)))) {
            LinkedHashMap<String, String> trainerList = cdb.TrainerList();
            LinkedHashMap<String, String> subjectList;

            c.setClassCode(code);
            c.setTrainer(udb.getUser(Integer.parseInt(trainer)));
            c.setSubject(sdb.getSubjectById(subject));
            c.setClassYear(Integer.parseInt(year));
            c.setTerm(Integer.parseInt(term));
            c.setBlock5Class(block5);
            c.setStatus(Integer.parseInt(status));
            if ("author".equals(u.getRoleId())) {
                subjectList = cdb.SubjectList(u.getUserId());
            } else {
                subjectList = cdb.SubjectList();
            }
            if (cdb.getClassById(id) == null) {
                 request.setAttribute("tag", "add");
            }
            else{
                  c.setClassId(Integer.parseInt(id));
                  
                  request.setAttribute("tag", "update");
            } 
            request.setAttribute("aclass", c);   
            request.setAttribute("trainerList", trainerList);
            request.setAttribute("subjectList", subjectList);
            request.setAttribute("err", "class " + code + " already have subject " + c.getSubject().getSubjectCode());
            request.getRequestDispatcher("class/Detail.jsp").forward(request, response);
            return;
        }
        else if(cdb.getClassById(id) == null) {
            cdb.AddClass(c);
             session.setAttribute("add_success", "add_success");
        } 
        else if(cdb.getClassById(id) != null){
            c.setClassId(Integer.parseInt(id));
            cdb.UpdateClass(c);
             session.setAttribute("update_success", "update_success");
        }

        String url = (String) session.getAttribute("pre_url");
        response.sendRedirect(url);
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
