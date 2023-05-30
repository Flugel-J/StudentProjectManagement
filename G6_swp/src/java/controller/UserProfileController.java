/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.UserDB;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import javax.servlet.http.Part;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author KHANHHERE
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 200 // 200 MB
)
public class UserProfileController extends HttpServlet {

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
            out.println("<title>Servlet EditProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditProfileServlet at " + request.getContextPath() + "</h1>");
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
    private String fileName(String title) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());

        timeStamp = timeStamp.replace(" ", "");
        timeStamp = timeStamp.replace(":", "");
        timeStamp = timeStamp.replace("-", "");
        return timeStamp + title;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("useraccount");
        request.setAttribute("user", user);
        request.getRequestDispatcher("user/Profile.jsp").forward(request, response);
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
        String name = request.getParameter("name");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String mobile = request.getParameter("mobile");
        String facebook = request.getParameter("facebook");
        int id = Integer.parseInt(request.getParameter("id"));

        UserDB dao = new UserDB();

        Part imagePart = request.getPart("image");
        if (imagePart.getSize() > 0) {
            String fname = fileName(imagePart.getSubmittedFileName());
            String imageName = fileName(name + ".png");

            //String path = "C:\\Users\\admin\\OneDrive\\Desktop\\java\\g6\\g6\\G6_swp\\web\\imgs";//somehow f.getAbsolutePath() always point wrong      
            String path = "C:\\Users\\Admin\\Downloads\\g6-iteration2 (3)\\g6-iteration2\\G6_swp\\web\\imgs\\";
            File f = new File(path);                                                                  //where tf is User/Appdata/Netbeans???
            if (!f.exists()) {
                f.mkdirs();
            }
            OutputStream out = null;
            InputStream filecontent = null;
            final PrintWriter writer = response.getWriter();

            try {
                out = new FileOutputStream(new File(path + File.separator
                        + imageName));
                filecontent = imagePart.getInputStream();

                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
            } catch (FileNotFoundException fne) {

                writer.println("<br/> ERROR: " + fne.getMessage() + "<br>" + path);

            } finally {
                if (out != null) {
                    out.close();
                }
                if (filecontent != null) {
                    filecontent.close();
                }
//            if (writer != null) {
//                writer.close();
//            }

            }
            String imgDelete = dao.getUser(id).getAvatarLink();
            File img = new File(path+"\\" +imgDelete);
            if (img.delete()) {
                System.out.println("File " + imgDelete + " deleted");
            }
            System.out.println(imageName);
                        dao.update(name, dob, gender, mobile, facebook, imageName, id);
        }
        User user = dao.getUser(id);
        dao.update(name, dob, gender, mobile, facebook, user.getAvatarLink(), id);
        String text = "Update successfully";

        request.setAttribute(
                "alert", text);

        HttpSession session = request.getSession();
        user = dao.getUser(id);
        session.setAttribute("useraccount", user);

        response.sendRedirect("userprofile");
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
