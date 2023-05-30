/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Subject;

/**
 *
 * @author admin
 */
public class SubjectDB extends DBContext {

    public Subject getSubjectById(String id) {
        Connection conn = new DBContext().connection;
        String sql = "select * from subject where subject_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Subject sub = new Subject();
                sub.setSubjectId(rs.getInt(1));
                sub.setSubjectCode(rs.getString(2));
                sub.setSubjectName(rs.getString(3));
                sub.setAuthor(Author(rs.getInt(4)));
                sub.setAuthorStatus(AuthorStatus(rs.getInt(4)));
                sub.setStatus(rs.getBoolean(5));
                return sub;
            }
        } catch (SQLException e) {
            Logger.getLogger(SubjectDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public List<Subject> Search(String code, String status, String name) {      //search
        Connection conn = new DBContext().connection;
        String sql = "select * from subject where true ";
        List<Subject> list = new ArrayList<>();
        List<String> search = new ArrayList<>();
        if (code != "") {
            sql += "and subject_code like ?";
            search.add("%" + code + "%");
        }
        if (status != "") {
            sql += "and status=?";
            search.add(status);
        }
        if (name != "") {
            sql += "and subject_name like ?";
            search.add("%" + name + "%");
        }

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 1; i <= search.size(); i++) {
                st.setString(i, search.get(i - 1));
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject sub = new Subject();
                sub.setSubjectId(rs.getInt(1));
                sub.setSubjectCode(rs.getString(2));
                sub.setSubjectName(rs.getString(3));
                sub.setAuthor(Author(rs.getInt(4)));
                sub.setAuthorStatus(AuthorStatus(rs.getInt(4)));
                sub.setStatus(rs.getBoolean(5));

                list.add(sub);
            }
        } catch (SQLException e) {
            Logger.getLogger(SubjectDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }

        return list;
    }

    public String Author(int id) {
        Connection conn = new DBContext().connection;
        String sql = "select full_name from user where user_id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return "No assigned author";
    }

    public boolean AuthorStatus(int id) {
        Connection conn = new DBContext().connection;
        String sql = "select status from user where user_id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(SubjectDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return false;
    }

    public LinkedHashMap<String, String> AuthorList() {
        LinkedHashMap<String, String> list = new LinkedHashMap<>();
        Connection conn = new DBContext().connection;
        String sql = "SELECT u.user_id, u.full_name from setting s inner join user u where s.setting_id= u.role_id and s.type_id=1 and s.setting_title='author' and u.status=1";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return list;
    }

    public void changeStatus(Subject s) {
        Connection conn = new DBContext().connection;
        String sql = "update subject set status=? where subject_id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setBoolean(1, s.isStatus());
            st.setInt(2, s.getSubjectId());
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(SubjectDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public void updateSubject(String code, String name, String author, String status, String id) { //update
        Connection conn = new DBContext().connection;
        String sql = "update subject "
                + "set `subject_code` = ?,\n"
                + "`subject_name` = ?,\n"
                + "`status` = ?,\n";
        if (author != "") {
            sql += "`author_id` = ?\n";
        } else {
            sql += "`author_id` = default\n";
        }
        sql += " where `subject_id` = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, code);
            st.setString(2, name);
            st.setString(3, status);
            if (author != "") {
                st.setString(4, author);
                st.setString(5, id);
            } else {
                st.setString(4, id);
            }

            st.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(SubjectDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public void AddSubject(String code, String name, String author, String status) {
        Connection conn = new DBContext().connection;
        String sql;
        if (author != "") {
            sql = "INSERT INTO `subject`\n"
                    + "(`subject_code`,\n"
                    + "`subject_name`,\n"
                    + "`author_id`,\n"
                    + "`status`)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?);";
        } else {
            sql = "INSERT INTO `subject`\n"
                    + "(`subject_code`,\n"
                    + "`subject_name`,\n"
                    + "`status`)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?);";
        }
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, code);
            st.setString(2, name);
            if (author != "") {
                st.setString(3, author);
                st.setString(4, status);
            } else {
                st.setString(3, status);
            }
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(SubjectDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public List<Subject> getListByPage(List<Subject> list, int start, int end) {    //paging support
        List<Subject> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public Subject getSubjectOfTracking(int trackingId) {
        Connection conn = new DBContext().connection;
        String sql = "SELECT c.subject_id FROM class c, team t, tracking tr\n"
                + "where c.class_id = t.class_id and tr.team_id = t.team_id and tr.tracking_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, trackingId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return getSubjectById(rs.getString(1));
            }
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return null;
    }

//    public static void main(String[] args) {
//        SubjectDB s = new SubjectDB();
//        s.AddSubject("PRF", "C", "", "1");
//    }
}
