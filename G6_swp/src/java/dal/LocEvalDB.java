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
import model.EvalCriteria;
import model.IterEval;
import model.LocEval;
import model.MemEval;
import model.Milestone;
import model.SubjectSetting;
import model.Tracking;

/**
 *
 * @author KHANHHERE
 */
public class LocEvalDB {

    private static final String SELECT_SQL = " SELECT * FROM loc_eval ";
    private static final String UPDATE_SQL = " UPDATE `loc_eval` \n"
            + " SET `evaluation_time` = now(),`evaluation_note` = ?,`complexity_id` = ?, "
            + "`quality_id` = ?,`tracking_id` = ?,`status` = ? \n"
            + " WHERE `evaluation_id` = ? ";
    private static final String INSERT_SQL = " INSERT INTO `loc_eval` \n"
            + " (`evaluation_time`,`evaluation_note`,`complexity_id`,`quality_id`,`tracking_id`,`status`) \n"
            + " VALUES (now() , ?, ?, ? , ? , ?) ";

    public Milestone getMilestoneById(String id) {
        Connection conn = new DBContext().connection;
        String sql = "select * from milestone where milestone_id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, id);
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

    public Tracking getTrackingById(int trackingId, String stu, String tra) {
        Connection conn = new DBContext().connection;
        TeamDB tdb = new TeamDB();
        UserDB udb = new UserDB();
        List<String> searchList = new ArrayList<>();
        searchList.add(trackingId + "");
        String sql = " SELECT * FROM tracking tr, class c, team t, class_user cu \n"
                + " where c.class_id = t.class_id and tr.team_id = t.team_id "
                + " and t.team_id = cu.team_id and tr.tracking_id = ? ";
        if (stu != "") {
            sql += " and cu.user_id = ? ";
            searchList.add(stu);
        }
        if (tra != "") {
            sql += " and c.trainer_id = ? ";
            searchList.add(tra);
        }
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 1; i <= searchList.size(); i++) {
                st.setString(i, searchList.get(i - 1));
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Tracking track = new Tracking();
                track.setId(rs.getInt(1));
                track.setTeam(tdb.getTeam(rs.getString(2)));
                track.setMilestone(getMilestoneById(rs.getString(3)));
                track.setAssigner(udb.getUser(rs.getInt(5)));
                track.setAssignee(udb.getUser(rs.getInt(6)));
                track.setNote(rs.getString(7));
                track.setUpdates(rs.getString(8));
                track.setStatus(rs.getInt(9));
                return track;
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

    public LocEval getLocEvalByTrackingId(int trackingId, String stu, String tra) {
        Connection conn = new DBContext().connection;
        SubjectSettingDB sdb = new SubjectSettingDB();
        String sql = SELECT_SQL + " where tracking_id = ? ";
        if (stu != "") {
            sql += " and status = 1 ";
        }
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, trackingId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                SubjectSetting complexity = sdb.getSSById(rs.getString(4), "");
                SubjectSetting quality = sdb.getSSById(rs.getString(5), "");
                return new LocEval(rs.getInt(1), rs.getDate(2), rs.getString(3),
                        complexity, quality, getTrackingById(rs.getInt(6), stu, tra), rs.getBoolean(7));
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

    public void updateTrackingStatus(int id, int status) {
        String sql = " UPDATE `tracking`\n"
                + " SET `status` = ? \n"
                + " WHERE `tracking_id` = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, status);
            st.setInt(2, id);
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

    public void update(LocEval le) {
        String sql = UPDATE_SQL;
        Connection conn = new DBContext().connection;
        LocEval leOld = getLocEvalByTrackingId(le.getTracking().getId(), "", "");
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, le.getNote());
            st.setInt(2, le.getComplexity().getSettingId());
            st.setInt(3, le.getQuality().getSettingId());
            st.setInt(4, le.getTracking().getId());
            st.setBoolean(5, le.isStatus());
            st.setInt(6, le.getId());
            st.executeUpdate();

            if (le.isStatus()) {
                updateTrackingStatus(le.getTracking().getId(), 7);
            } else {
                if (le.getTracking().getStatus() == 7) {
                    updateTrackingStatus(le.getTracking().getId(), 6);
                }
            }
            // update grade for mem_eval
            IterEvalDB iedb = new IterEvalDB();
            IterEval ie = iedb.getIEOfTracking(le.getTracking().getId());
            ie.setId(iedb.isIEExist(ie));
            EvalCriteriaDB ecdb = new EvalCriteriaDB();
            EvalCriteria ec = ecdb.getLocCriteria(ie.getIteration().getIterationId());
            int convertedLoc = calculateConvertedLoc(le) - calculateConvertedLoc(leOld);
            
            MemEvalDB medb = new MemEvalDB();
            MemEval me = medb.getMemEvalOfLoc(iedb.isIEExist(ie));
            me.setConvertedLoc(me.getConvertedLoc()+convertedLoc);
            double grade = me.getConvertedLoc()*10 / ec.getMaxLoc();
            if (grade > 10) {
                grade = 10;
            }
            me.setGrade(grade);
            medb.update(me);
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public void add(LocEval le) {
        String sql = INSERT_SQL;
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, le.getNote());
            st.setInt(2, le.getComplexity().getSettingId());
            st.setInt(3, le.getQuality().getSettingId());
            st.setInt(4, le.getTracking().getId());
            st.setBoolean(5, le.isStatus());
            st.executeUpdate();
            if (le.isStatus()) {
                updateTrackingStatus(le.getTracking().getId(), 7);
            } else {
                if (le.getTracking().getStatus() == 7) {
                    updateTrackingStatus(le.getTracking().getId(), 6);
                }
            }
            IterEvalDB iedb = new IterEvalDB();
            IterEval ie = iedb.getIEOfTracking(le.getTracking().getId());
            if (iedb.isIEExist(ie) == -1) {
                iedb.add(ie);
            }
            ie.setId(iedb.isIEExist(ie));
            MemEvalDB medb = new MemEvalDB();
            MemEval me = medb.getMemEvalOfLoc(iedb.isIEExist(ie));
            EvalCriteriaDB ecdb = new EvalCriteriaDB();
            EvalCriteria ec = ecdb.getLocCriteria(ie.getIteration().getIterationId());
            // calculate grade grade
            int convertedLoc = calculateConvertedLoc(le);
            if (me == null) {
                double grade = convertedLoc*10 / ec.getMaxLoc();
                if (grade > 10) {
                    grade = 10;
                }
                MemEval me2 = new MemEval(1, ie, ec, convertedLoc, grade, null, true);
                medb.add(me2);               
            } else {
                // update grade
                me.setConvertedLoc(me.getConvertedLoc()+convertedLoc);
                double grade = me.getConvertedLoc()*10 / ec.getMaxLoc();
                if (grade > 10) {
                    grade = 10;
                }
                me.setGrade(grade);
                medb.update(me);
            }
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public int calculateConvertedLoc(LocEval le) {
        double result = 0;
        double complex = 0, quality = 0;
        try {
            complex = Double.parseDouble(le.getComplexity().getSettingValue());
            quality = Double.parseDouble(le.getQuality().getSettingValue());
            result = complex * quality;
        } catch (Exception e) {
        }

        return (int) Math.round(result);
    }

//    public static void main(String[] args) {
//        LocEvalDB ledb = new LocEvalDB();
//        System.out.println(ledb.calculateConvertedLoc(null));
//    }
}
