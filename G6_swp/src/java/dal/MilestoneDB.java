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
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Milestone;
import model.Class;
import model.Iteration;

/**
 *
 * @author admin
 */
public class MilestoneDB extends DBContext {

    public List<Milestone> Search(String id, String Class, String from, String to, String status, int curPage, int numPerPage) {
        List<Milestone> list = new ArrayList<>();
        List<String> search = new ArrayList<>();
        Connection conn = new DBContext().connection;
        search.add(id);
        String sql = "select * from\n"
                + "(select m.milestone_id,m.milestone_name, m.iteration_id, m.class_id, m.from_date, m.to_date,m.status, row_number() over\n"
                + "(order by m.milestone_id) as row_index from milestone as m, iteration as i, class as c\n"
                + "where m.iteration_id = i.iteration_id and m.class_id = c.class_id and i.status = 1 and c.status = 1 and c.trainer_id=?";
        if (Class != "") {
            sql += " and m.class_id=? ";
            search.add(Class);
        }

        if (from != "") {
            sql += " and (m.from_date  between ? and ?) ";

            search.add(from);
            search.add(MaxDate());
        }
        if (to != "") {
            sql += " and (m.to_date between ? and ?) ";
            search.add(MinDate());
            search.add(to);
        }
        if (status != "") {
            sql += " and m.status =? ";
            search.add(status);
        }
        search.add(curPage + "");
        search.add(numPerPage + "");
        search.add(curPage + "");
        search.add(numPerPage + "");
        sql += " )as t where row_index >= (?-1)*?+1 and row_index <= ?*?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {

            for (int i = 1; i <= search.size(); i++) {
                st.setString(i, search.get(i - 1));
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Milestone m = new Milestone();
                m.setMilestoneId(rs.getInt(1));
                m.setMilestoneName(rs.getString(2));
                m.setIteration(new IterationDB().getIterationByID(rs.getInt(3)));
                m.setClassId(new ClassDB().getClassById(rs.getString(4)));
                m.setFromDate(rs.getDate(5));
                m.setToDate(rs.getDate(6));
                m.setStatus(rs.getInt(7));
                list.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {

            }
        }
        return list;
    }

    public int count(String id, String Class, String from, String to, String status) {

        List<String> search = new ArrayList<>();
        Connection conn = new DBContext().connection;
        search.add(id);
        String sql = "SELECT count(*) FROM milestone m inner join class c on m.class_id=c.class_id "
                + "inner join iteration i on m.iteration_id=i.iteration_id where c.trainer_id=? and i.status=1 and c.status=1";
        if (Class != "") {
            sql += " and m.class_id=?";
            search.add(Class);
        }

        if (from != "") {
            sql += " and (m.from_date  between ? and ?)";

            search.add(from);
            search.add(MaxDate());
        }
        if (to != "") {
            sql += " and (m.to_date between ? and ?)";
            search.add(MinDate());
            search.add(to);
        }
        if (status != "") {
            sql += " and m.status =? ";
            search.add(status);
        }
        try (PreparedStatement st = conn.prepareStatement(sql)) {

            for (int i = 1; i <= search.size(); i++) {
                st.setString(i, search.get(i - 1));
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDB.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                conn.close();
            } catch (Exception ex) {

            }
        }
        return 0;
    }

    public String MinDate() {
        Connection conn = new DBContext().connection;
        String sql = "select min(to_date) from milestone";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {

            }
        }
        return "0-00-00";
    }

    public String MaxDate() {
        Connection conn = new DBContext().connection;
        String sql = "select max(from_date) from milestone";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {

            }
        }
        return "2077-07-07";                                                           ///this mean ther will be a bug if no milestone on database and 
    }                                                                                  ///you using this web in 2077

    public List<Class> ClassList(int id) {
        Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM class where trainer_id=? and status=1 order by class_code";
        List<Class> list = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Class c = new Class();
                c.setClassId(rs.getInt(1));
                c.setClassCode(rs.getString(2));
                c.setSubject(new SubjectDB().getSubjectById(rs.getString(4)));
                c.setClassYear(rs.getInt(5));
                c.setTerm(rs.getInt(6));
                c.setBlock5Class(rs.getBoolean(7));
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {

            }
        }
        return list;
    }

    public List<Iteration> getIterationByClass(String id) {
        Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM iteration i  inner join class c on c.subject_id=i.subject_id where c.class_id=?";
        List<Iteration> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Iteration i = new Iteration();
                i.setIterationId(rs.getInt(1));
                i.setIterName(rs.getString(3));
                list.add(i);
            }
        } catch (SQLException ex) {

            Logger.getLogger(MilestoneDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {

            }
        }
        return list;
    }

    public Milestone getMilestoneById(String id, int trainer) {
        Connection conn = new DBContext().connection;
        String sql = "select * from milestone m inner join class c on m.class_id=c.class_id where m.milestone_id=? and c.trainer_id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, id);
            st.setInt(2, trainer);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Milestone m = new Milestone();
                m.setMilestoneId(rs.getInt(1));
                m.setMilestoneName(rs.getString(2));
                m.setIteration(new IterationDB().getIterationByID(rs.getInt(3)));
                m.setClassId(new ClassDB().getClassById(rs.getString(4)));
                m.setFromDate(rs.getDate(5));
                m.setToDate(rs.getDate(6));
                m.setStatus(rs.getInt(7));
                return m;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {

            }
        }
        return null;
    }

    public void changeStatus(String id) {
        Connection conn = new DBContext().connection;
        String sql = "update milestone set status = case \n"
                + "    when status=3 then 1\n"
                + "    else status+1\n"
                + "    End\n"
                + "where milestone_id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, id);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {

            }
        }
    }

    public void update(String id, String name, String from, String to, String status) {
        Connection conn = new DBContext().connection;
        String sql = "UPDATE `milestone`\n"
                + "SET\n"
                + "\n"
                + "`milestone_name` = ?,\n"
                + "`from_date` = ?,\n"
                + "`to_date` = ?,\n"
                + "`status` = ?\n"
                + "WHERE `milestone_id` = ?;";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, name);
            st.setString(2, from);
            st.setString(3, to);
            st.setString(4, status);
            st.setString(5, id);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {

            }
        }
    }

    public void Add(String name, String iteration, String Class, String from, String to, String status) {
        Connection conn = new DBContext().connection;
        String sql = "INSERT INTO `milestone`\n"
                + "(`milestone_name`,\n"
                + "`iteration_id`,\n"
                + "`class_id`,\n"
                + "`from_date`,\n"
                + "`to_date`,\n"
                + "`status`)\n"
                + "VALUES\n"
                + "(?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?);";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, name);
            if (iteration == "") {
                st.setNull(2, Types.INTEGER);
            } else {
                st.setString(2, iteration);
            }
            st.setString(3, Class);
            st.setString(4, from);
            st.setString(5, to);
            st.setString(6, status);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {

            }
        }
    }

    public boolean checkUniqueMilestoneName(String name, String iter, String Class) {
        Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM milestone where milestone_name=? and class_id=? and iteration_id";
        if (iter == "") {
            sql += " is null";
        } else {
            sql += "=?";
        }
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, name);
            st.setString(2, Class);
            if (!"".equals(iter)) {
                st.setString(3, iter);
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {

            }
        }
        return false;
    }

//    public static void main(String[] args) {
//        System.out.println(Boolean.parseBoolean("0"));
//    }
}
