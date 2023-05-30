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
import model.IterEval;
import model.MemEval;

/**
 *
 * @author KHANHHERE
 */
public class MemEvalDB {

    public MemEval getMemEvalOfLoc(int evalId) {
        String sql = "select * from member_eval me, eval_criteria ec\n"
                + "where me.criteria_id = ec.criteria_id and ec.max_loc != 0 and me.evaluation_id = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, evalId);
            ResultSet rs = st.executeQuery();
            IterEvalDB iedb = new IterEvalDB();
            EvalCriteriaDB ecdb = new EvalCriteriaDB();
            if (rs.next()) {
                return new MemEval(rs.getInt(1), iedb.getIEById(rs.getString(2)),
                        ecdb.getEvalCriById(rs.getString(3), ""), rs.getInt(4),
                        rs.getDouble(5), rs.getString(6), rs.getBoolean(7));
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

    public int isMEExist(MemEval me) {
        String sql = "select * from member_eval where evaluation_id = ? and criteria_id = ? ";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, me.getIterEval().getId());
            st.setInt(2, me.getEvalCriteria().getCriteriaId());
            ResultSet rs = st.executeQuery();
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

    public MemEval getMEById(String id) {
        String sql = "select * from member_eval where member_eval_id = ?";
        Connection conn = new DBContext().connection;
        IterEvalDB iedb = new IterEvalDB();
        EvalCriteriaDB ecdb = new EvalCriteriaDB();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new MemEval(rs.getInt(1), iedb.getIEById(rs.getString(2)),
                        ecdb.getEvalCriById(rs.getString(3), ""), rs.getInt(4),
                        rs.getDouble(5), rs.getString(6), rs.getBoolean(7));
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

    public void add(MemEval me) {
        String sql = "INSERT INTO `member_eval`\n"
                + "(`evaluation_id`,`criteria_id`,`converted_loc`,`grade`,`note`)\n"
                + "VALUES (?,?,?,?,?)";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, me.getIterEval().getId());
            st.setInt(2, me.getEvalCriteria().getCriteriaId());
            st.setInt(3, me.getConvertedLoc());
            st.setDouble(4, me.getGrade());
            st.setString(5, me.getNote());
            st.executeUpdate();

            IterEvalDB iedb = new IterEvalDB();
            IterEval ie = iedb.getIEById(me.getIterEval().getId() + "");
            double memGrade = iedb.memGradeOfIE(ie);
            double teamGrade = iedb.teamGradeOfIE(ie);
            ie.setGrade(memGrade + teamGrade);
            iedb.update(ie);
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public void update(MemEval me) {
        String sql = "UPDATE `member_eval`\n"
                + "SET\n"
                + "`evaluation_id` = ?,\n"
                + "`criteria_id` = ?,\n"
                + "`converted_loc` = ?,\n"
                + "`grade` = ?,\n"
                + "`note` = ?,\n"
                + "`status` = ? \n"
                + " WHERE `member_eval_id` = ?";
        IterEvalDB iedb = new IterEvalDB();
        IterEval ie = iedb.getIEById(me.getIterEval().getId() + "");
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, me.getIterEval().getId());
            st.setInt(2, me.getEvalCriteria().getCriteriaId());
            st.setInt(3, me.getConvertedLoc());
            st.setDouble(4, me.getGrade());
            st.setString(5, me.getNote());
            st.setBoolean(6, me.isStatus());
            st.setInt(7, me.getId());
            st.executeUpdate();
            double memGrade = iedb.memGradeOfIE(ie);
            double teamGrade = iedb.teamGradeOfIE(ie);
            ie.setGrade(memGrade + teamGrade);
            iedb.update(ie);
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    //get the grade of iter_eval for each eval_criteria
    public double getGrade(String ieId, String ecId) {
        double result = -1;
        String sql = "select * from member_eval where evaluation_id = ? and criteria_id = ? ";
        Connection conn = new DBContext().connection;
        DecimalFormat df = new DecimalFormat("#.##");
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, ieId);
            st.setString(2, ecId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(5);
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

    // error function, till need fixed
    public List<MemEval> getMEOfIE(IterEval ie) {
        List<MemEval> list = new ArrayList<>();
        String sql = " select * from member_eval me, iteration_eval ie, eval_criteria ec \n"
                + " where me.evaluation_id = ie.eval_id and ie.iteration_id = ec.interation_id and ec.team_evaluation = 0 \n"
                + " and ec.status = 1 and ie.iteration_id = ? and ie.team_id = ? ";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ie.getIteration().getIterationId());
            st.setInt(2, ie.getTeam().getTeamId());
            ResultSet rs = st.executeQuery();
            IterEvalDB iedb = new IterEvalDB();
            EvalCriteriaDB ecdb = new EvalCriteriaDB();
            while (rs.next()) {
                list.add(new MemEval(rs.getInt(1), iedb.getIEById(rs.getString(2)),
                        ecdb.getEvalCriById(rs.getString(3), ""), rs.getInt(4),
                        rs.getDouble(5), rs.getString(6), rs.getBoolean(7)));
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
//        MemEvalDB m = new MemEvalDB();
//        IterEvalDB i = new IterEvalDB();
//        IterEval ie = i.getIEById("1");
//        System.out.println(ie.getTeam().getTeamId());
//        List<MemEval> list = m.getMEOfIE(ie);
//        for(MemEval me: list){
//            System.out.println(me.getId());
//        }
//    }

}
