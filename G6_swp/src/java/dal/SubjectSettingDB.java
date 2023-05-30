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
import java.util.List;
import model.Subject;
import model.SubjectSetting;

/**
 *
 * @author KHANHHERE
 */
public class SubjectSettingDB extends DBContext {

    public List<SubjectSetting> search(String type, String status, String search_value, String author, int curPage, int numPerPage) {
        Connection conn = new DBContext().connection;
        List<SubjectSetting> list = new ArrayList<>();
        String sql = "select * from\n"
                + " (select ss.setting_id, ss.subject_id, ss.type_id, ss.setting_title, ss.setting_value, ss.display_order, ss.`status`, row_number() over \n"
                + " (order by ss.setting_id) as row_index  from user as u, subject as s, subject_setting as ss \n"
                + " where u.user_id = s.author_id and ss.subject_id = s.subject_id and s.status=1 \n";
        List<String> searchList = new ArrayList<>();
        if (type != "") {
            sql += " and ss.type_id=? ";
            searchList.add(type);
        }
        if (status != "") {
            sql += " and ss.status=? ";
            searchList.add(status);
        }
        if (search_value != "") {
            sql += " and (ss.setting_title like ? or s.subject_name like ? ) ";
            searchList.add("%" + search_value + "%");
            searchList.add("%" + search_value + "%");
        }
        if (author != "") {
            sql += " and u.user_id=? ";
            searchList.add(author);
        }
        searchList.add(curPage + "");
        searchList.add(numPerPage + "");
        searchList.add(curPage + "");
        searchList.add(numPerPage + "");
        sql += " )as t where row_index >= (?-1)*?+1 and row_index <= ?*? ";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 1; i <= searchList.size(); i++) {
                st.setString(i, searchList.get(i - 1));
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SubjectSetting ss = new SubjectSetting();
                ss.setSettingId(rs.getInt(1));
                ss.setSubjectId(rs.getInt(2));
                ss.setTypeId(rs.getInt(3));
                ss.setSettingTitle(rs.getString(4));
                ss.setSettingValue(rs.getString(5));
                ss.setDisplayOrder(rs.getInt(6));
                ss.setStatus(rs.getBoolean(7));
                list.add(ss);
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return list;
    }

    public int count(String type, String status, String search_value, String author) {
        int result = 0;
        String sql = "select count(*) \n"
                + " from user as u, subject as s, subject_setting as ss\n"
                + " where u.user_id = s.author_id and ss.subject_id = s.subject_id and s.status=1 ";
        List<String> searchList = new ArrayList<>();
        Connection conn = new DBContext().connection;
        if (type != "") {
            sql += " and ss.type_id=? ";
            searchList.add(type);
        }
        if (status != "") {
            sql += " and ss.status=? ";
            searchList.add(status);
        }
        if (search_value != "") {
            sql += " and (ss.setting_title like ? or s.subject_name like ? ) ";
            searchList.add("%" + search_value + "%");
            searchList.add("%" + search_value + "%");
        }
        if (author != "") {
            sql += " and u.user_id=? ";
            searchList.add(author);
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

    public List<String> getListType() {
        Connection conn = new DBContext().connection;
        String sql = "SELECT distinct type_id FROM subject_setting";
        List<String> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt(1) + "");
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

    public List<SubjectSetting> getAll() {
        Connection conn = new DBContext().connection;
        String sql = "select * from subject_setting";
        List<SubjectSetting> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new SubjectSetting(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getBoolean(7)));
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

    public SubjectSetting getSSById(String id, String user_id) {
        Connection conn = new DBContext().connection;
        String sql = "select ss.setting_id, ss.subject_id, ss.type_id, ss.setting_title, ss.setting_value, ss.display_order, ss.`status` from user as u, subject as s, subject_setting as ss\n"
                + "where u.user_id = s.author_id and ss.subject_id = s.subject_id "
                + "and ss.setting_id = ? ";
        List<String> searchList = new ArrayList<>();
        searchList.add(id);
        if (user_id != "") {
            sql += "and u.user_id=? ";
            searchList.add(user_id);
        }
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 1; i <= searchList.size(); i++) {
                st.setString(i, searchList.get(i - 1));
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new SubjectSetting(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getBoolean(7));
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

    public void updateSubjectSetting(SubjectSetting ss) {
        Connection conn = new DBContext().connection;
        String sql = "UPDATE subject_setting\n"
                + "SET\n"
                + "`subject_id` = ?,\n"
                + "`type_id` = ?,\n"
                + "`setting_title` = ?, \n"
                + "`setting_value` = ?,\n"
                + "`display_order` = ?,\n"
                + "`status` = ?\n"
                + "WHERE `setting_id` = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ss.getSubjectId());
            st.setInt(2, ss.getTypeId());
            st.setString(3, ss.getSettingTitle());
            st.setString(4, ss.getSettingValue());
            st.setInt(5, ss.getDisplayOrder());
            st.setBoolean(6, ss.isStatus());
            st.setInt(7, ss.getSettingId());
            st.executeUpdate();
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public List<Subject> getAllSubject(String author) {
        Connection conn = new DBContext().connection;
        String sql = "select s.subject_id, s.subject_code, s.subject_name, s.author_id, s.status from user as u, subject as s\n"
                + "where u.user_id = s.author_id and s.status=1 ";
        List<Subject> list = new ArrayList<>();
        SubjectDB sdb = new SubjectDB();
        if (author != "") {
            sql += " and s.author_id=? ";
        }
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            if (author != "") {
                st.setString(1, author);
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subject s = new Subject();
                s.setSubjectId(rs.getInt(1));
                s.setSubjectCode(rs.getString(2));
                s.setSubjectName(rs.getString(3));
                s.setAuthor(sdb.Author(rs.getInt(4)));
                s.setStatus(rs.getBoolean(5));
                list.add(s);
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

    public void addSubjectSetting(SubjectSetting ss) {
        Connection conn = new DBContext().connection;
        String sql = "INSERT INTO `subject_setting`\n"
                + "(`subject_id`,`type_id`,`setting_title`,`setting_value`,`display_order`,`status`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ss.getSubjectId());
            st.setInt(2, ss.getTypeId());
            st.setString(3, ss.getSettingTitle());
            st.setString(4, ss.getSettingValue());
            st.setInt(5, ss.getDisplayOrder());
            st.setBoolean(6, ss.isStatus());
            st.executeUpdate();
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public List<SubjectSetting> getPropertiesOfSubject(String subjectId, int typeId) {
        String sql = "SELECT * FROM subject_setting\n"
                + "where subject_id = ? and type_id = ?";
        Connection conn = new DBContext().connection;
        List<SubjectSetting> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, subjectId);
            st.setInt(2, typeId);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                list.add(new SubjectSetting(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getBoolean(7)));
            }
        } catch (Exception e) {
        }
        return list;
    }
    

//    public static void main(String[] args) {
//        SubjectSettingDB s = new SubjectSettingDB();
//        SubjectSetting sub = s.getSSById("7", "");
//        System.out.println(sub.getSubjectId());
//        List<SubjectSetting> list = s.search("", "", "", "", "");
//        for (SubjectSetting ss : list) {
//            System.out.println(ss.getSettingId());
//            System.out.println(ss.getSubjectId());
//        }
//        List<String> listSt = s.getListType();
//        for(String sss: listSt){
//            System.out.println(sss);
//        }
//    }
}
