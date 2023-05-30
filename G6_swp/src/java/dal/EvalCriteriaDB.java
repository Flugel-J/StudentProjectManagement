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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import model.EvalCriteria;
import model.Iteration;

/**
 *
 * @author KHANHHERE
 */
public class EvalCriteriaDB extends DBContext {

    public int count(String search_teamEval, String status, String search_value, String author) {
        Connection conn = new DBContext().connection;
        int result = 0;
        String sql = " select count(*) \n"
                + " from eval_criteria as ec, iteration as i, `subject` as s \n "
                + " where ec.interation_id = i.iteration_id and i.subject_id = s.subject_id and i.status = 1 and s.status = 1 ";
        List<String> searchList = new ArrayList<>();
        if (search_teamEval != "") {
            sql += " and ec.team_evaluation = ? ";
            searchList.add(search_teamEval);
        }
        if (status != "") {
            sql += " and ec.status = ? ";
            searchList.add(status);
        }
        if (search_value != "") {
            sql += " and (i.iter_name like ? or s.subject_name like ? or ec.evaluation_title like ?) ";
            searchList.add("%" + search_value + "%");
            searchList.add("%" + search_value + "%");
            searchList.add("%" + search_value + "%");
        }
        if (author != "") {
            sql += " and s.author_id=? ";
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

    public List<EvalCriteria> search(String search_teamEval, String status, String search_value, String author, int curPage, int numPerPage) {
        List<EvalCriteria> list = new ArrayList<>();
        String sql = "select * from\n"
                + " (select ec.criteria_id, ec.interation_id, ec.evaluation_title, ec.evaluation_weight, ec.team_evaluation,ec.criteria_order, ec.max_loc, ec.`status`, row_number() over\n "
                + " (order by ec.interation_id) as row_index from eval_criteria as ec, iteration as i, `subject` as s \n "
                + " where ec.interation_id = i.iteration_id and i.subject_id = s.subject_id and i.status = 1 and s.status = 1 ";
        List<String> searchList = new ArrayList<>();
        Connection conn = new DBContext().connection;
        if (search_teamEval != "") {
            sql += " and ec.team_evaluation = ? ";
            searchList.add(search_teamEval);
        }
        if (status != "") {
            sql += " and ec.status = ? ";
            searchList.add(status);
        }
        if (search_value != "") {
            sql += " and (i.iter_name like ? or s.subject_name like ? or ec.evaluation_title like ?) ";
            searchList.add("%" + search_value + "%");
            searchList.add("%" + search_value + "%");
            searchList.add("%" + search_value + "%");
        }
        if (author != "") {
            sql += " and s.author_id=? ";
            searchList.add(author);
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
            IterationDB idb = new IterationDB();
            while (rs.next()) {
                EvalCriteria ec = new EvalCriteria(rs.getInt(1), idb.getIterationByID(rs.getInt(2)),
                        rs.getString(3), rs.getDouble(4), rs.getBoolean(5),
                        rs.getInt(6), rs.getInt(7), rs.getBoolean(8));
                list.add(ec);
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

    public List<Iteration> getIterList(String author) {
        Connection conn = new DBContext().connection;
        List<Iteration> list = new ArrayList<>();
        String sql = "SELECT i.iteration_id, i.subject_id, i.iter_name, i.duration, i.`status` \n"
                + " FROM iteration as i, subject as s \n"
                + " where i.subject_id=s.subject_id and s.status=1 ";
        if (author != "") {
            sql += " and s.author_id=? ";
        }
        try (PreparedStatement st = conn.prepareStatement(sql)) {

            if (author != "") {
                st.setString(1, author);
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Iteration(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getBoolean(5)));
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

    public EvalCriteria getEvalCriById(String id, String author) {
        Connection conn = new DBContext().connection;
        String sql = "SELECT * from eval_criteria as ec, iteration as i, `subject` as s where criteria_id = ?"
                + " and ec.interation_id = i.iteration_id and i.subject_id = s.subject_id ";
        List<String> searchList = new ArrayList<>();
        searchList.add(id);
        if (author != "") {
            sql += " and s.author_id=? ";
            searchList.add(author);
        }
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 1; i <= searchList.size(); i++) {
                st.setString(i, searchList.get(i - 1));
            }
            ResultSet rs = st.executeQuery();
            IterationDB idb = new IterationDB();
            if (rs.next()) {
                EvalCriteria ec = new EvalCriteria(rs.getInt(1), idb.getIterationByID(rs.getInt(2)),
                        rs.getString(3), rs.getDouble(4), rs.getBoolean(5),
                        rs.getInt(6), rs.getInt(7), rs.getBoolean(8));
                return ec;
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

    public void updateEvalCri(EvalCriteria e) {
        Connection conn = new DBContext().connection;
        String sql = "UPDATE `eval_criteria`\n"
                + "SET\n"
                + "`interation_id` = ?,\n"
                + "`evaluation_title` = ?,\n"
                + "`evaluation_weight` = ?,\n"
                + "`team_evaluation` = ?,\n"
                + "`criteria_order` = ?,\n"
                + "`max_loc` = ?,\n"
                + "`status` = ?\n"
                + "WHERE `criteria_id` =? ";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, e.getIteration().getIterationId());
            st.setString(2, e.getEvalTitle());
            st.setDouble(3, e.getEvalWeight());
            st.setBoolean(4, e.isTeamEval());
            st.setInt(5, e.getCriteriaOrder());
            st.setInt(6, e.getMaxLoc());
            st.setBoolean(7, e.isStatus());
            st.setInt(8, e.getCriteriaId());
            st.executeUpdate();
        } catch (Exception ex) {
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                /* Ignored */
            }
        }
    }

    public double getTotalWeight(int iterationId) {
        Connection conn = new DBContext().connection;
        String sql = "select sum(evaluation_weight) \n"
                + "from eval_criteria where status = 1 and interation_id = ?  \n"
                + "group by interation_id ";
        double result = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, iterationId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        result = Double.parseDouble(df.format(result));
        return result;
    }

    public double getMaxPossibleWeight(int id, int iterationId) {
        Connection conn = new DBContext().connection;
        String sql = "select sum(evaluation_weight) \n"
                + " from eval_criteria where status = 1 and interation_id = ?  "
                + " and criteria_id != ? \n"
                + " group by interation_id";
        double result = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, iterationId);
            st.setInt(2, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        result = Double.parseDouble(df.format(result));
        return 100 - result;
    }

    public void addEC(EvalCriteria ec) {
        Connection conn = new DBContext().connection;
        String sql = "INSERT INTO `eval_criteria`\n"
                + "(`interation_id`,`evaluation_title`,`evaluation_weight`,`team_evaluation`,`criteria_order`,`max_loc`,`status`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ec.getIteration().getIterationId());
            st.setString(2, ec.getEvalTitle());
            st.setDouble(3, ec.getEvalWeight());
            st.setBoolean(4, ec.isTeamEval());
            st.setInt(5, ec.getCriteriaOrder());
            st.setInt(6, ec.getMaxLoc());
            st.setBoolean(7, ec.isStatus());
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

    public EvalCriteria getLocCriteria(int iterId) {
        String sql = " select ec.criteria_id from eval_criteria ec where ec.interation_id = ? "
                + " and ec.team_evaluation=0 and ec.max_loc!=0";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, iterId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return getEvalCriById(rs.getString(1), "");
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

    public List<EvalCriteria> getECOfTeam(String teamId) {
//        String sql = "select * from eval_criteria ec ,team t, class c, subject s, iteration i\n"
//                + "where t.class_id = c.class_id and c.subject_id = s.subject_id and s.subject_id = i.subject_id and i.iteration_id = ec.interation_id\n"
//                + "and t.team_id = ? and ec.status = 1 and ec.team_evaluation = 1 order by ec.interation_id";
        String sql = "select * from eval_criteria ec\n"
                + "join iteration i on ec.interation_id = i.iteration_id\n"
                + "join subject s on i.subject_id = s.subject_id\n"
                + "join class c on s.subject_id = c.subject_id\n"
                + "join team t on c.class_id = t.class_id\n"
                + "where t.team_id = ? and ec.status = 1 and ec.team_evaluation = 1 "
                + "order by ec.interation_id";
        Connection conn = new DBContext().connection;
        List<EvalCriteria> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, teamId);
            ResultSet rs = st.executeQuery();
            IterationDB idb = new IterationDB();
            while (rs.next()) {
                EvalCriteria ec = new EvalCriteria(rs.getInt(1), idb.getIterationByID(rs.getInt(2)),
                        rs.getString(3), rs.getDouble(4), rs.getBoolean(5),
                        rs.getInt(6), rs.getInt(7), rs.getBoolean(8));
                list.add(ec);
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

    public List<EvalCriteria> getECOfIter(String iterId) {
        String sql = "select * from eval_criteria ec where ec.interation_id = ? and ec.team_evaluation = 0";
        Connection conn = new DBContext().connection;
        List<EvalCriteria> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, iterId);
            ResultSet rs = st.executeQuery();
            IterationDB idb = new IterationDB();
            while (rs.next()) {
                EvalCriteria ec = new EvalCriteria(rs.getInt(1), idb.getIterationByID(rs.getInt(2)),
                        rs.getString(3), rs.getDouble(4), rs.getBoolean(5),
                        rs.getInt(6), rs.getInt(7), rs.getBoolean(8));
                list.add(ec);
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

//    public static void main(String[] args) {
//        EvalCriteriaDB edb = new EvalCriteriaDB();
//        System.out.println(edb.getLocCriteria(2).getCriteriaId());
//    }
}
