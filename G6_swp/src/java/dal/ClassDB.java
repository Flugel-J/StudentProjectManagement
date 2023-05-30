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
import model.Class;

/**
 *
 * @author KHANHHERE
 */
public class ClassDB extends DBContext {

    public List<Class> search(String term, String status, String search_value, String author, String trainer, int curPage, int numPerPage) {
        Connection conn = new DBContext().connection;
        List<Class> list = new ArrayList<>();
        String sql = "select * from "
                + " (select c.class_id, c.class_code, c.trainer_id, c.subject_id, c.class_year, c.class_term, c.block5_class, c.`status`, row_number() over \n"
                + " (order by c.class_id) as row_index from class as c, subject as s \n"
                + " where c.subject_id=s.subject_id  ";
        List<String> searchList = new ArrayList<>();
        if (term != "") {
            sql += " and c.class_term = ? ";
            searchList.add(term);
        }
        if (status != "") {
            sql += " and c.status = ? ";
            searchList.add(status);
        }
        if (search_value != "") {
            sql += " and (s.subject_name like ? or c.class_code like ?) ";
            searchList.add("%" + search_value + "%");
            searchList.add("%" + search_value + "%");
        }
        if (author != "") {
            sql += " and s.author_id=? ";
            searchList.add(author);
        }
        if (trainer != "") {
            sql += " and c.trainer_id=? ";
            searchList.add(trainer);
        }
        searchList.add(curPage + "");
        searchList.add(numPerPage + "");
        searchList.add(curPage + "");
        searchList.add(numPerPage + "");
        sql += " )as t where row_index >= (?-1)*?+1 and row_index <= ?*?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 1; i <= searchList.size(); i++) {
                st.setString(i, searchList.get(i - 1));
            }
            ResultSet rs = st.executeQuery();
            SubjectDB sdb = new SubjectDB();
            UserDB udb = new UserDB();
            while (rs.next()) {
                Class c = new Class(rs.getInt(1), rs.getString(2), udb.getUser(rs.getInt(3)),
                        sdb.getSubjectById(rs.getInt(4) + ""), rs.getInt(5), rs.getInt(6),
                        rs.getBoolean(7), rs.getInt(8));
                list.add(c);
            }
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return list;
    }

    // for user_eval
    public List<Class> getListClass(String stu, String tra) {
        Connection conn = new DBContext().connection;
        List<Class> list = new ArrayList<>();
        String sql = " select c.class_id, c.class_code, c.trainer_id, c.subject_id, c.class_year, c.class_term, c.block5_class, c.`status` from class c, class_user cu \n"
                + " where c.class_id = cu.class_id and c.status = 1 ";
        List<String> searchList = new ArrayList<>();
        if (tra != "") {
            sql += " and c.trainer_id=? ";
            searchList.add(tra);
        }
        if (stu != "") {
            sql += " and cu.user_id = ? ";
            searchList.add(stu);
        }
        sql += " group by c.class_id, c.class_code, c.trainer_id, c.subject_id, "
                + " c.class_year, c.class_term, c.block5_class, c.`status` ";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 1; i <= searchList.size(); i++) {
                st.setString(i, searchList.get(i - 1));
            }
            ResultSet rs = st.executeQuery();
            SubjectDB sdb = new SubjectDB();
            UserDB udb = new UserDB();
            while (rs.next()) {
                Class c = new Class(rs.getInt(1), rs.getString(2), udb.getUser(rs.getInt(3)),
                        sdb.getSubjectById(rs.getInt(4) + ""), rs.getInt(5), rs.getInt(6),
                        rs.getBoolean(7), rs.getInt(8));
                list.add(c);
            }
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return list;
    }

    public int count(String term, String status, String search_value, String author, String trainer) {
        int result = 0;
        Connection conn = new DBContext().connection;
        String sql = "select count(*) \n"
                + " from class as c, subject as s \n"
                + " where c.subject_id=s.subject_id  ";
        List<String> searchList = new ArrayList<>();
        if (term != "") {
            sql += " and c.class_term = ? ";
            searchList.add(term);
        }
        if (status != "") {
            sql += " and c.status = ? ";
            searchList.add(status);
        }
        if (search_value != "") {
            sql += " and (s.subject_name like ? or c.class_code like ?) ";
            searchList.add("%" + search_value + "%");
            searchList.add("%" + search_value + "%");
        }
        if (author != "") {
            sql += " and s.author_id=? ";
            searchList.add(author);
        }
        if (trainer != "") {
            sql += " and c.trainer_id=? ";
            searchList.add(trainer);
        }
        try (PreparedStatement st = conn.prepareStatement(sql)) {

            for (int i = 1; i <= searchList.size(); i++) {
                st.setString(i, searchList.get(i - 1));
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return result;
    }

    public Class getClassById(String id, String author, String trainer) {
        String sql = "select c.class_id, c.class_code, c.trainer_id, c.subject_id, c.class_year, c.class_term, c.block5_class, c.`status` \n"
                + " from class as c, subject as s \n"
                + " where c.class_id=? and c.subject_id=s.subject_id  ";
        Connection conn = new DBContext().connection;
        List<String> searchList = new ArrayList<>();
        searchList.add(id);
        if (author != "") {
            sql += " and s.author_id=? ";
            searchList.add(author);
        }
        if (trainer != "") {
            sql += " and c.trainer_id=? ";
            searchList.add(trainer);
        }
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 1; i <= searchList.size(); i++) {
                st.setString(i, searchList.get(i - 1));
            }
            ResultSet rs = st.executeQuery();
            SubjectDB sdb = new SubjectDB();
            UserDB udb = new UserDB();
            if (rs.next()) {
                Class c = new Class(rs.getInt(1), rs.getString(2), udb.getUser(rs.getInt(3)),
                        sdb.getSubjectById(rs.getInt(4) + ""), rs.getInt(5), rs.getInt(6),
                        rs.getBoolean(7), rs.getInt(8));
                return c;
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

    public Class getClassById(String id) {
        String sql = "select * from class where class_id = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Class c = new Class();
                c.setClassId(rs.getInt(1));
                c.setClassCode(rs.getString(2));
                c.setTrainer(new UserDB().getUser(rs.getInt(3)));
                c.setSubject(new SubjectDB().getSubjectById(rs.getString(4)));
                c.setClassYear(rs.getInt(5));
                c.setTerm(rs.getInt(6));
                c.setBlock5Class(rs.getBoolean(7));
                c.setStatus(rs.getInt(8));
                return c;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return null;
    }

    public LinkedHashMap<String, String> TrainerList() {
        LinkedHashMap<String, String> list = new LinkedHashMap<>();
        Connection conn = new DBContext().connection;
        String sql = "SELECT u.user_id, u.full_name from setting s inner join"
                + " user u where s.setting_id= u.role_id and s.type_id=1 "
                + "and s.setting_title='trainer' and u.status=1";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }

        return list;
    }

    public LinkedHashMap<String, String> SubjectList() {
        Connection conn = new DBContext().connection;
        LinkedHashMap<String, String> list = new LinkedHashMap<>();
        String sql = "select distinct subject_id,"
                + "concat(subject_code,' (',subject_name,')') from subject";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }

        return list;
    }

    public LinkedHashMap<String, String> SubjectList(int id) {
        LinkedHashMap<String, String> list = new LinkedHashMap<>();
        Connection conn = new DBContext().connection;
        String sql = "select distinct subject_id,"
                + "concat(subject_code,' (',subject_name,')') from subject where author_id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }

        return list;
    }

    public void AddClass(Class c) {
        String sql = "INSERT INTO `class`\n"
                + "(`class_code`,\n"
                + "`trainer_id`,\n"
                + "`subject_id`,\n"
                + "`class_year`,\n"
                + "`class_term`,\n"
                + "`block5_class`,\n"
                + "`status`)\n"
                + "VALUES\n"
                + "(?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?)";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, c.getClassCode());
            if (c.getTrainer() == null) {
                st.setNull(2, java.sql.Types.INTEGER);
            } else {
                st.setInt(2, c.getTrainer().getUserId());
            }
            if (c.getSubject() == null) {
                st.setNull(3, java.sql.Types.INTEGER);
            } else {
                st.setInt(3, c.getSubject().getSubjectId());
            }
            st.setInt(4, c.getClassYear());
            st.setInt(5, c.getTerm());
            st.setBoolean(6, c.isBlock5Class());
            st.setInt(7, c.getStatus());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClassDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public void UpdateClass(Class c) {
        String sql = "UPDATE `class`\n"
                + "SET\n"
                + "`class_code` = ?,\n"
                + "`trainer_id` = ?,\n"
                + "`subject_id` = ?,\n"
                + "`class_year` = ?,\n"
                + "`class_term` = ?,\n"
                + "`block5_class` = ?,\n"
                + "`status` = ?\n"
                + "WHERE `class_id` = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, c.getClassCode());
            if (c.getTrainer() == null) {
                st.setNull(2, java.sql.Types.INTEGER);
            } else {
                st.setInt(2, c.getTrainer().getUserId());
            }
            if (c.getSubject() == null) {
                st.setNull(3, java.sql.Types.INTEGER);
            } else {
                st.setInt(3, c.getSubject().getSubjectId());
            }
            st.setInt(4, c.getClassYear());
            st.setInt(5, c.getTerm());
            st.setBoolean(6, c.isBlock5Class());
            st.setInt(7, c.getStatus());
            st.setInt(8, c.getClassId());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClassDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public boolean checkClassAndSubject(String code, String subject) {
        Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM class where class_code=? and subject_id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, code);
            st.setString(2, subject);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }

        return false;
    }

//    public static void main(String[] args) {
//        ClassDB cdb = new ClassDB();
//        
//        System.out.println(cdb.checkClassAndSubject("SE1610", "1"));
//    }
}
