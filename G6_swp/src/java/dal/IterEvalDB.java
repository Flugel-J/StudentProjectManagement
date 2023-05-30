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
import model.ClassUser;
import model.IterEval;
import model.TeamEval;

/**
 *
 * @author KHANHHERE
 */
public class IterEvalDB {

    private static final String INSERT_SQL = "INSERT INTO `iteration_eval`\n"
            + "(`iteration_id`,`class_id`,`team_id`,`user_id`,`bonus`,`grade`,`note`)\n"
            + "VALUES (?,?,?,?,?,?, ?)";

    // get this IE to insert after loc_eval a tracking
    public IterEval getIEOfTracking(int trackingId) {
        String sql = "select m.iteration_id, t.class_id, t.team_id, tr.assingee_id from loc_eval le, tracking tr, milestone m, team t\n"
                + "where le.tracking_id = tr.tracking_id and tr.milestone_id = m.milestone_id and tr.team_id = t.team_id and tr.tracking_id = ? \n"
                + "group by m.iteration_id, t.class_id, t.team_id, tr.assingee_id";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, trackingId);
            ResultSet rs = st.executeQuery();
            IterationDB idb = new IterationDB();
            ClassDB cdb = new ClassDB();
            TeamDB tdb = new TeamDB();
            UserDB udb = new UserDB();
            if (rs.next()) {
                return new IterEval(1, idb.getIterationByID(rs.getInt(1)),
                        cdb.getClassById(rs.getString(2)), tdb.getTeam(rs.getString(3)),
                        udb.getUser(rs.getInt(4)), 0, 0, null, true);
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

    public List<IterEval> getListIEByTE(TeamEval te) {
        List<IterEval> list = new ArrayList<>();
        String sql = "select ec.interation_id, t.class_id, t.team_id, cu.user_id from team_eval te, eval_criteria ec, team t, class_user cu\n"
                + "where te.criteria_id = ec.criteria_id and te.team_id = t.team_id and t.team_id = cu.team_id \n"
                + "and te.team_id = ? and te.criteria_id = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, te.getTeam().getTeamId());
            st.setInt(2, te.getEvalCriteria().getCriteriaId());
            ResultSet rs = st.executeQuery();
            IterationDB idb = new IterationDB();
            ClassDB cdb = new ClassDB();
            TeamDB tdb = new TeamDB();
            UserDB udb = new UserDB();
            while (rs.next()) {
                list.add(new IterEval(1, idb.getIterationByID(rs.getInt(1)),
                        cdb.getClassById(rs.getString(2)), tdb.getTeam(rs.getString(3)),
                        udb.getUser(rs.getInt(4)), 0, 0, null, true));
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

    public int isIEExist(IterEval ie) {
        String sql = " select * from iteration_eval where iteration_id = ? and "
                + " class_id = ? and team_id = ? and user_id = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ie.getIteration().getIterationId());
            st.setInt(2, ie.getClassObj().getClassId());
            st.setInt(3, ie.getTeam().getTeamId());
            st.setInt(4, ie.getUser().getUserId());
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

    public void add(IterEval ie) {
        String sql = INSERT_SQL;
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ie.getIteration().getIterationId());
            st.setInt(2, ie.getClassObj().getClassId());
            st.setInt(3, ie.getTeam().getTeamId());
            st.setInt(4, ie.getUser().getUserId());
            st.setDouble(5, ie.getBonus());
            st.setDouble(6, ie.getGrade());
            st.setString(7, ie.getNote());
            st.executeUpdate();
            
            // update grade for class_user
            ClassUserDB cudb = new ClassUserDB();
            ClassUser cu = cudb.getClassbUser(ie.getUser().getUserId(), ie.getTeam().getTeamId(),
                    ie.getClassObj().getClassId());
            double ongoingGrade = cudb.grade(cu, 1);
            double finalPresGrade = cudb.grade(cu, 0);
            double finalTopicGrade = ongoingGrade + finalPresGrade;
            cu.setOngoingEval(ongoingGrade);
            cu.setFinalPresEval(finalPresGrade);
            cu.setFinalTopicEval(finalTopicGrade);
            cudb.updateGrade(cu);
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }
    
    public void update(IterEval ie) {
        String sql = "UPDATE `iteration_eval`\n"
                + "SET `iteration_id` = ?,`class_id` = ?,`team_id` = ?,`user_id` = ?,`bonus` = ?,`grade` = ?,`note` = ?,`status` = ?\n"
                + "WHERE `eval_id` = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ie.getIteration().getIterationId());
            st.setInt(2, ie.getClassObj().getClassId());
            st.setInt(3, ie.getTeam().getTeamId());
            st.setInt(4, ie.getUser().getUserId());
            st.setDouble(5, ie.getBonus());
            st.setDouble(6, ie.getGrade());
            st.setString(7, ie.getNote());
            st.setBoolean(8, ie.isStatus());
            st.setInt(9, ie.getId());
            st.executeUpdate();
            
            // update grade class_user
            ClassUserDB cudb = new ClassUserDB();
            ClassUser cu = cudb.getClassbUser(ie.getUser().getUserId(), ie.getTeam().getTeamId(),
                    ie.getClassObj().getClassId());
            double ongoingGrade = cudb.grade(cu, 1);
            double finalPresGrade = cudb.grade(cu, 0);
            double finalTopicGrade = ongoingGrade + finalPresGrade;
            cu.setOngoingEval(ongoingGrade);
            cu.setFinalPresEval(finalPresGrade);
            cu.setFinalTopicEval(finalTopicGrade);
            cudb.updateGrade(cu);
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }
    
    public IterEval getIEById(String id) {
        String sql = "select * from iteration_eval where eval_id = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            IterationDB idb = new IterationDB();
            ClassDB cdb = new ClassDB();
            TeamDB tdb = new TeamDB();
            UserDB udb = new UserDB();
            if (rs.next()) {
                return new IterEval(rs.getInt(1), idb.getIterationByID(rs.getInt(2)),
                        cdb.getClassById(rs.getString(3)), tdb.getTeam(rs.getString(4)),
                        udb.getUser(rs.getInt(5)), rs.getDouble(6), rs.getDouble(7),
                        rs.getString(8), rs.getBoolean(9));
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

    public double teamGradeOfIE(IterEval ie) {
        double result = -1;
        DecimalFormat df = new DecimalFormat("#.##");
        String sql = "select sum(distinct te.grade * ec.evaluation_weight/100) \n"
                + "as team_eval_grade from team_eval te, eval_criteria ec, iteration_eval ie\n"
                + "where te.criteria_id = ec.criteria_id and ec.interation_id = ie.iteration_id and te.team_id = ie.team_id\n"
                + "and ie.iteration_id = ? and ie.team_id = ? \n"
                + "group by ie.team_id, ie.iteration_id, ie.team_id ";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ie.getIteration().getIterationId());
            st.setInt(2, ie.getTeam().getTeamId());
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
    
    public double weightOfTeamGradeOfIE(IterEval ie){
        double result = -1;
        DecimalFormat df = new DecimalFormat("#.##");
        String sql = "select sum(distinct evaluation_weight) \n"
                + "as team_weight from team_eval te, eval_criteria ec, iteration_eval ie\n"
                + "where te.criteria_id = ec.criteria_id and ec.interation_id = ie.iteration_id and te.team_id = ie.team_id\n"
                + "and ie.iteration_id = ? and ie.team_id = ? \n"
                + "group by ie.team_id, ie.iteration_id, ie.team_id ";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ie.getIteration().getIterationId());
            st.setInt(2, ie.getTeam().getTeamId());
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

    public double memGradeOfIE(IterEval ie) {
        double result = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        String sql = "select (me.grade * ec.evaluation_weight/100) as mem_grade from member_eval me, eval_criteria ec\n"
                + "where me.evaluation_id = ? and me.criteria_id = ec.criteria_id;";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ie.getId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                result += rs.getDouble(1);
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

//    public static void main(String[] args) {
//        IterEvalDB e = new IterEvalDB();
//        IterEval ie = new IterEval();
//        ie.setId(1);
//        System.out.println(e.memGradeOfIE(ie));
//    }
}
