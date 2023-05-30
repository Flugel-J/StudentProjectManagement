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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author KHANHHERE
 */
public class UserDB extends DBContext {

    public List<User> getAllUser() {
        Connection conn = new DBContext().connection;
        String sql = "select * from user";
        List<User> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            SettingDB sDB = new SettingDB();
            while (rs.next()) {
                list.add(new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getBoolean(4),
                        rs.getDate(5), rs.getString(6),
                        rs.getString(7),
                        rs.getString(8), rs.getString(9), rs.getString(10),
                        sDB.getRoleById(rs.getInt(11)),
                        rs.getBoolean(12)));
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

    public User getUser(int id) {
        Connection conn = new DBContext().connection;
        String sql = "select * from user where user_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)){
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            SettingDB sDB = new SettingDB();
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getBoolean(4),
                        rs.getDate(5), rs.getString(6),
                        rs.getString(7),
                        rs.getString(8), rs.getString(9), rs.getString(10),
                        sDB.getRoleById(rs.getInt(11)),
                        rs.getBoolean(12));
            }
        } catch (Exception e) {
        } finally{
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public void insertUserRegistration(User s) {
        Connection conn = new DBContext().connection;
        String sql = "INSERT INTO `user`\n"
                + "(`roll_number`,\n"
                + "`full_name`,\n"
                + "`gender`,\n"
                + "`dob`,\n"
                + "`email`,\n"
                + "`mobile`,\n"
                + "`password`,\n"
                + "`role_id`,\n"
                + "`status`)\n"
                + "VALUES\n"
                + "(?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?)";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            SettingDB sDB = new SettingDB();
            stm.setString(1, s.getRollNumber());
            stm.setString(2, s.getFullName());
            stm.setBoolean(3, s.isGender());
            stm.setDate(4, s.getDob());
            stm.setString(5, s.getEmail());
            stm.setString(6, s.getMobile());
            stm.setString(7, s.getPassword());
            stm.setInt(8, sDB.getRoleID(s.getRoleId()));
            stm.setBoolean(9, s.isStatus());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public User getUserAccount(String email, String password) {
        Connection conn = new DBContext().connection;
        String sql = "select * from user where email = ? and password = ? and status=1";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {           
            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            SettingDB sDB = new SettingDB();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setRollNumber(rs.getString("roll_number"));
                u.setFullName(rs.getString("full_name"));
                u.setGender(rs.getBoolean("gender"));
                u.setDob(rs.getDate("dob"));
                u.setEmail(rs.getString("email"));
                u.setMobile(rs.getString("mobile"));
                u.setAvatarLink(rs.getString("avatar_link"));
                u.setFacebookLink(rs.getString("facebook_link"));
                u.setPassword(rs.getString("password"));
                u.setRoleId(sDB.getRoleById(rs.getInt("role_id")));
                u.setFullName(rs.getString("full_name"));
                return u;
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return null;
    }

    public boolean getUserEmail(String email) {
        Connection conn = new DBContext().connection;
        String sql = "select * from user where email = ? ";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            SettingDB sDB = new SettingDB();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                /* Ignored */
            }
        }
        return false;
    }

    public void update(String roll, String name, String dob, String gender, String mobile, String email, String image, int id) {
        String sql = "update user set roll_number=?,full_name=?,gender=?,dob=?,email=?,mobile=?,avatar_link=? where user_id = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int result = 0;
            ps.setString(1, roll);
            ps.setString(2, name);
            ps.setString(3, gender);
            ps.setString(4, dob);
            ps.setString(5, email);
            ps.setString(6, mobile);
            ps.setString(7, image);
            ps.setInt(8, id);
            result = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public void updatepassword(int user_id, String pass_word) {
        String sql = "UPDATE user\n"
                    + "SET\n"
                    + "`password` = ?\n"
                    + "WHERE `user_id` = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int result = 0;
            ps.setString(1, pass_word);
            ps.setInt(2, user_id);
            result = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public int getUseridbyemail(String email) {
        String sql = "SELECT * FROM user where email = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            SettingDB sDB = new SettingDB();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return -1;
    }

    //Update UserProfile iter1
    public void update(String name, String dob, String gender, String mobile, String facebook, String image, int id) {
        String sql = "UPDATE user SET full_name = ?, dob =?, gender = ?, mobile = ?, facebook_link = ?, avatar_link = ? WHERE user_id= ?;";
        Connection conn = new DBContext().connection;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, dob);
            ps.setString(3, gender);
            ps.setString(4, mobile);
            ps.setString(5, facebook);
            ps.setString(6, image);
            ps.setInt(7, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }
//-----------------------------------------------------------------------------
//iter2
//UpdateStatus UserList
    public void updateStatus(String id, String status) {
        String sql = "UPDATE user \n"
                    + "SET \n"
                    + "    status = ?\n"
                    + "WHERE\n"
                    + "    user_id  = ?;";
        Connection conn = new DBContext().connection;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, Boolean.parseBoolean(status));
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    // Update UserDetail iter2
    public void update(String id, String name, String email, String mobile, String facebook, String gender, int status, String roleId) {
        String sql = "UPDATE user SET full_name = ?, email = ?,mobile=? ,facebook_link = ?,gender= ?,status = ?,role_id = ?  WHERE user_id = ?;";
        Connection conn = new DBContext().connection;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);
            ps.setString(4, facebook);
            ps.setString(5, gender);
            ps.setInt(6, status);
            ps.setString(7, roleId);
            ps.setInt(8, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }
//UserList-Search
    public List<User> Search(String roleId, String status, String name) {
        List<User> list = new ArrayList<>();
        String sql;
        List<String> search = new ArrayList<>();
        if ((roleId == null && status == null && name == null) || (roleId == "" && status == "" && name == "")) {
            sql = "select * from user ";
        } else {
            sql = "select * from user where true";
            if (roleId != "") {
                sql += " and role_id=?";
                search.add(roleId);
            }
            if (status != "") {
                sql += " and status=?";
                search.add(status);
            }
            if (name != "") {
                sql += " and full_name like ?";
                search.add("%" + name + "%");
            }
        }
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            if (search.size() > 0) {
                for (int i = 1; i <= search.size(); i++) {
                    st.setString(i, search.get(i - 1));
                }
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getBoolean(4),
                        rs.getDate(5), rs.getString(6),
                        rs.getString(7),
                        rs.getString(8), rs.getString(9), rs.getString(10),
                        rs.getString(11),
                        rs.getBoolean(12)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                /* Ignored */
            }
        }

        return list;
    }

//ListByPage
    public List<User> getListByPage(List<User> list, int start, int end) {
        List<User> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

 //UserList-UserDetail-AllRole
    public List<String> getAllRole() {
        Connection conn = new DBContext().connection;
        List<String> list = new ArrayList<>();
        String sql = "SELECT setting_title FROM setting limit 4;";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                /* Ignored */
            }
        }
        return list;
    }

}
