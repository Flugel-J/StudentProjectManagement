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
import model.IterEval;
import model.Team;
import model.TeamEval;

/**
 *
 * @author KHANHHERE
 */
public class TeamEvalDB {

    private static final String UPDATE_SQL = "UPDATE `team_eval`\n"
            + "SET `criteria_id` = ?, `team_id` = ?,`grade` = ?,`note` = ?,`status` = ?\n"
            + "WHERE `team_eval_id` = ? ";
    private static final String INSERT_SQL = " INSERT INTO `team_eval`(`criteria_id`,`team_id`,`grade`,`note`) \n"
            + " VALUES (?,?,?,?) ";

    public List<TeamEval> search(String search_team, String search_value, String stu, String tra, int curPage, int numPerPage) {
        List<TeamEval> list = new ArrayList<>();
        Connection conn = new DBContext().connection;
        String sql = " select * from \n"
                + " (select te.team_eval_id, te.criteria_id, te.team_id, te.grade, te.note, te.status ,row_number() over \n"
                + " (order by te.team_eval_id) as row_index  from team_eval as te, team as t, class_user as cu, class as c, subject as s, eval_criteria as ec \n"
                + " where te.status = 1 and te.team_id = t.team_id and t.class_id = cu.class_id and t.class_id = c.class_id and c.subject_id = s.subject_id and te.criteria_id = ec.criteria_id ";
        List<String> searchList = new ArrayList<>();
        if (search_team != "") {
            sql += " and t.team_id = ? ";
            searchList.add(search_team);
        }
        if (search_value != "") {
            sql += " and (s.subject_name like ? or ec.evaluation_title like ? ) ";
            searchList.add("%" + search_value + "%");
            searchList.add("%" + search_value + "%");
        }
        if (stu != "") {
            sql += " and cu.user_id = ? ";
            searchList.add(stu);
        }
        if (tra != "") {
            sql += " and c.trainer_id = ? ";
            searchList.add(tra);
        }
        sql += " group by te.team_eval_id, te.criteria_id, te.team_id, te.grade, te.note, te.status ";
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
            EvalCriteriaDB ecdb = new EvalCriteriaDB();
            TeamDB tdb = new TeamDB();
            while (rs.next()) {
                list.add(new TeamEval(rs.getInt(1), ecdb.getEvalCriById(rs.getString(2), ""),
                        tdb.getTeam(rs.getString(3)), rs.getDouble(4), rs.getString(5), rs.getBoolean(6)));
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

    public int count(String search_team, String search_value, String stu, String tra) {
        int result = 0;
        Connection conn = new DBContext().connection;
        String sql = "with T as "
                + " (select distinct te.team_eval_id, te.criteria_id, te.team_id, te.grade, te.note, te.status \n"
                + " from team_eval as te, team as t, class_user as cu, class as c, subject as s, eval_criteria as ec \n"
                + " where te.team_id = t.team_id and t.class_id = cu.class_id and t.class_id = c.class_id and c.subject_id = s.subject_id and te.criteria_id = ec.criteria_id ";
        List<String> searchList = new ArrayList<>();
        if (search_team != "") {
            sql += " and and t.team_id = ? ";
            searchList.add(search_team);
        }
        if (search_value != "") {
            sql += " and (s.subject_name like ? or ec.evaluation_title like ? ) ";
            searchList.add("%" + search_value + "%");
            searchList.add("%" + search_value + "%");
        }
        if (stu != "") {
            sql += " and cu.user_id = ? ";
            searchList.add(stu);
        }
        if (tra != "") {
            sql += " and c.trainer_id = ? ) ";
            searchList.add(tra);
        }
        sql += " select count(*) from T ";
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

    //for search in team eval list page
    public List<Team> getListTeam(String stu, String tra) {
        Connection conn = new DBContext().connection;
        List<String> searchList = new ArrayList<>();
        List<Team> list = new ArrayList<>();
//        String sql = " SELECT distinct te.team_id FROM team_eval te, class c, team t, class_user cu \n"
//                + " where c.class_id = t.class_id and te.team_id = t.team_id "
//                + " and t.team_id = cu.team_id  ";
        String sql = "select distinct t.team_id from team t\n"
                + "join class c on t.class_id = c.class_id\n"
                + "join class_user cu on t.team_id = cu.team_id\n"
                + "where true ";
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
            TeamDB tdb = new TeamDB();
            while (rs.next()) {
                list.add(tdb.getTeam(rs.getString(1)));
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

    public TeamEval getTEById(String id) {
        String sql = "select * from team_eval where team_eval_id = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            EvalCriteriaDB ecdb = new EvalCriteriaDB();
            TeamDB tdb = new TeamDB();
            if (rs.next()) {
                return new TeamEval(rs.getInt(1), ecdb.getEvalCriById(rs.getString(2), ""),
                        tdb.getTeam(rs.getString(3)), rs.getDouble(4), rs.getString(5), rs.getBoolean(6));
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

    public int isTEExist(String criteria, String team) {
        String sql = "select * from team_eval where criteria_id = ? and team_id = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, criteria);
            st.setString(2, team);
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

    public void update(TeamEval te) {
        String sql = UPDATE_SQL;
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, te.getEvalCriteria().getCriteriaId());
            st.setInt(2, te.getTeam().getTeamId());
            st.setDouble(3, te.getGrade());
            st.setString(4, te.getNote());
            st.setBoolean(5, te.isStatus());
            st.setInt(6, te.getId());
            st.executeUpdate();

            // need code to update grade in iteration_evaluation
            IterEvalDB iedb = new IterEvalDB();
            List<IterEval> listIE = iedb.getListIEByTE(te);
            for (IterEval iei : listIE) {
                int ieId = iedb.isIEExist(iei);
                IterEval ie = iedb.getIEById(ieId + "");
                double memGrade = iedb.memGradeOfIE(ie);
                double teamGrade = iedb.teamGradeOfIE(ie);
                ie.setGrade(memGrade + teamGrade);
                iedb.update(ie);
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

    public void add(TeamEval te2) {
        String sql = INSERT_SQL;
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, te2.getEvalCriteria().getCriteriaId());
            st.setInt(2, te2.getTeam().getTeamId());
            st.setDouble(3, te2.getGrade());
            st.setString(4, te2.getNote());
            st.executeUpdate();

            // update grade
            IterEvalDB iedb = new IterEvalDB();
            List<IterEval> listIE = iedb.getListIEByTE(te2);
            for (IterEval iei : listIE) {
                if (iedb.isIEExist(iei) == -1) {
                    iei.setGrade(te2.getGrade() * te2.getEvalCriteria().getEvalWeight() / 100);
                    iedb.add(iei);
                } else {
                    int ieId = iedb.isIEExist(iei);
                    IterEval ie = iedb.getIEById(ieId + "");
                    double memGrade = iedb.memGradeOfIE(ie);
                    double teamGrade = iedb.teamGradeOfIE(ie);
                    ie.setGrade(memGrade + teamGrade);
                    iedb.update(ie);
                }
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
    //    public static void main(String[] args) {
//        TeamEvalDB t = new TeamEvalDB();
//        TeamEval te2 = new TeamEval();
//        te2.setGrade(10);
//        t.add(te2);
//    }

}
