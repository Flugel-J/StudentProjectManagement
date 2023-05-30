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
import model.Iteration;
import model.Setting;

/**
 * n
 *
 * @author Admin
 */
public class IterationDB extends DBContext {

    public List<Iteration> getIteration() {
        Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM iteration";
        List<Iteration> iter = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Iteration i = new Iteration(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getInt(4), rs.getBoolean(5),
                        rs.getDouble(6), rs.getBoolean(7));
                iter.add(i);
            }
        } catch (Exception e) {
            Logger.getLogger(IterationDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return iter;
    }

    public Iteration getIterationByID(int id) {
        Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM iteration where iteration_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Iteration it = new Iteration();
                it.setIterationId(rs.getInt(1));
                it.setSubjectId(rs.getInt(2));
                it.setIterName(rs.getString(3));
                it.setDuration(rs.getInt(4));
                it.setStatus(rs.getBoolean(5));
                it.setEvalWeight(rs.getDouble(6));
                it.setIsOngoing(rs.getBoolean(7));
                return it;
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

    public void insertIter(Iteration i) {
        Connection conn = new DBContext().connection;
        String sql = "INSERT INTO `iteration`\n"
                + "(`subject_id`,\n"
                + "`iter_name`,\n"
                + "`duration`,\n"
                + "`status`)\n"
                + "VALUES\n"
                + "(?,\n"
                + "?,\n"
                + "?,\n"
                + "?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, i.getSubjectId());
            st.setString(2, i.getIterName());
            st.setInt(3, i.getDuration());
            st.setBoolean(4, i.isStatus());
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

    public List<Iteration> getListIterOfClass(String search_class) {
        Connection conn = new DBContext().connection;
        List<Iteration> list = new ArrayList<>();
        String sql = "select distinct i.iteration_id from iteration i, class c\n"
                + "where i.subject_id = c.subject_id and c.class_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, search_class);
            ResultSet rs = st.executeQuery();
            IterationDB idb = new IterationDB();
            while(rs.next()){
                list.add(idb.getIterationByID(rs.getInt(1)));
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

    public List<Iteration> Search(String itername, String status, String subjectId) {
        Connection conn = new DBContext().connection;
        List<Iteration> iter = new ArrayList<>();
        String sql = "select * from iteration where true ";
        List<String> search = new ArrayList<>();
        if (!"".equals(itername)) {
            sql += "and iter_name=?";
            search.add(itername);
        }
        if (!"".equals(status)) {
            sql += "and status=?";
            search.add(status);
        }
        if (!"".equals(subjectId)) {
            sql += "and subject_id like ?";
            search.add("%" + subjectId + "%");
        }
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 1; i <= search.size(); i++) {
                st.setString(i, search.get(i - 1));
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Iteration it = new Iteration();
                it.setIterationId(rs.getInt(1));
                it.setSubjectId(rs.getInt(2));
                it.setIterName(rs.getString(3));
                it.setDuration(rs.getInt(4));
                it.setStatus(rs.getBoolean(5));

                iter.add(it);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }

        return iter;
    }

    public void updateIteration(Iteration i) {
        Connection conn = new DBContext().connection;
        String sql = "UPDATE `iteration`\n"
                + "SET\n"
                + "`subject_id` = ?,\n"
                + "`iter_name` = ?,\n"
                + "`duration` = ?,\n"
                + "`status` = ?\n"
                + "WHERE `iteration_id` = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, i.getSubjectId());
            st.setString(2, i.getIterName());
            st.setInt(3, i.getDuration());
            st.setBoolean(4, i.isStatus());
            st.setInt(5, i.getIterationId());
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

    public List<String> getListIterName() {
        Connection conn = new DBContext().connection;
        String sql = "SELECT distinct iter_name FROM iteration";
        List<String> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
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

    public List<Iteration> getListByPage(List<Iteration> list, int start, int end) {
        List<Iteration> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

}
