/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Function;
import model.Issue;
import model.Milestone;
import model.Setting;
import model.Team;
import model.User;
import model.Class;

/**
 *
 * @author Admin
 */
public class IssueDB {

    public ArrayList<Issue> getAllIssue() {
        Connection conn = new DBContext().connection;
        String sql = "select * from issue";
        ArrayList<Issue> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Issue(rs.getInt(1), getUser(rs.getInt(2)), getSetting(rs.getInt(3)),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(8), rs.getDate(9),
                        getTeam(rs.getInt(10)), getMileStone(rs.getInt(11)), getFunction(rs.getInt(12)), rs.getString(13), rs.getInt(14)));
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
        UserDB udb = new UserDB();
        return udb.getUser(id);
    }

    public Setting getSetting(int id) {
        SettingDB s = new SettingDB();
        return s.getSettingById(id);
    }

    public Team getTeam(int id) {
        Connection conn = new DBContext().connection;
        String sql = "select * from team where team_id = ?";
        List<Team> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            SettingDB sDB = new SettingDB();
            if (rs.next()) {
                return new Team(rs.getInt(1), rs.getString(2), new model.Class(rs.getInt(3), getClassCode(rs.getInt(3))), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));
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

    public String getClassCode(int classId) {
        Connection conn = new DBContext().connection;
        String sql = "SELECT class_code FROM class where class_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, classId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return "";
    }

    public Milestone getMileStone(int id) {
        Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM milestone where milestone_id = ?;";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
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

    public String getFunction(int id) {
        Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM function where function_id = ?;";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(3);
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

    public void updateStatus(String id, String active) {
        String sql = "UPDATE issue SET status = ? WHERE issue_id  = ?;";
        Connection conn = new DBContext().connection;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, active);
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

    public Issue getIssue(String id) {
        Connection conn = new DBContext().connection;
        String sql = "select * from issue where issue_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Issue(rs.getInt(1), getUser(rs.getInt(2)), getSetting(rs.getInt(3)),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(8), rs.getDate(9),
                        getTeam(rs.getInt(10)), getMileStone(rs.getInt(11)), getFunction(rs.getInt(12)), rs.getString(13), rs.getInt(14));
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

    public List getAllSetting() {
        List<Setting> list = new ArrayList();
        Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM setting;";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Setting b = new Setting();
                b.setSettingId(rs.getInt(1));
                b.setTypeId(rs.getInt(2));
                b.setSettingTitle(rs.getString(3));
                b.setSettingValue(rs.getString(4));
                b.setDisplayOrder(rs.getInt(5));
                b.setStatus(rs.getBoolean(7));

                list.add(b);
            }
            return list;
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

    public List getAllMile() {
        List list = new ArrayList();
        Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM milestone;";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
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
            return list;
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

    public void update(int id, int assignId, int setting, String title, int gitlabId, String gitlabUrl, Date createAt, Date dueDate, int milestone, String functionIds, int teamId, String label, String descript, int status) {
        Connection conn = new DBContext().connection;
        String sql = "UPDATE issue\n"
                + "SET issue_id = ?, assignee_id = ?, issue_type = ?, issue_title = ?, "
                + "descript = ?, gitlab_id= ?, gitlab_url =?, created_at =?, due_date = ?,\n"
                + "team_id = ?, milestone_id = ?, function_ids = ?, labels = ?, status = ? where issue_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.setInt(2, assignId);
            st.setInt(3, setting);
            st.setString(4, title);
            st.setString(5, descript);
            st.setInt(6, gitlabId);
            st.setString(7, gitlabUrl);
            st.setDate(8, createAt);
            st.setDate(9, dueDate);
            st.setInt(10, teamId);
            st.setInt(11, milestone);
            st.setString(12, functionIds);
            st.setString(13, label);
            st.setInt(14, status);
            st.setInt(15, id);
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

    public String getClassName(int milestoneId) {
        Connection conn = new DBContext().connection;
        String sql = "select y.class_code from (SELECT class_id FROM milestone where milestone_id = ?) x left join (select * from class) y \n"
                + "on x.class_id = y.class_id";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, milestoneId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
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

    public List getMileStudent(int userId) {
        List list = new ArrayList();
        String sql = "SELECT * FROM milestone \n"
                + "where class_id in (SELECT class_id FROM class_user where user_id = ?) ";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, userId);
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

    public List<Issue> Search(String search_status, String search_value) {
        List<Issue> list = new ArrayList<>();
        String sql;
        List<String> search = new ArrayList<>();
        if ((search_value == null && search_status == null) || (search_value == "" && search_status == "")) {
            sql = "select * from issue";
        } else {
            sql = "select x.*, y.full_name from issue x join user y on x.assignee_id = y.user_id where true";
            if (search_status != "") {
                sql += " and x.status = ?";
                search.add(search_status);
            }
            if (search_value != "") {
                sql += " and issue_title like ? or full_name like ?";
                search.add("%" + search_value + "%");
                search.add("%" + search_value + "%");
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
                list.add(new Issue(rs.getInt(1), getUser(rs.getInt(2)), getSetting(rs.getInt(3)),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(8), rs.getDate(9),
                        getTeam(rs.getInt(10)), getMileStone(rs.getInt(11)), getFunction(rs.getInt(12)), rs.getString(13), rs.getInt(14)));
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

    public Object getListByPage(List<Issue> list, int start, int end) {
        List<Issue> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public void add(int assignId, int setting, String title, String gitlabId, String gitlabUrl, Date dueDate, int milestone, String functionIds, int teamId, String label, String descript, int status) {
        Connection conn = new DBContext().connection;
        String sql = "INSERT INTO issue\n"
                + "(assignee_id , issue_type , issue_title , descript,  gitlab_id, gitlab_url, created_at, due_date ,team_id , milestone_id , function_ids, labels, status)"
                + "VALUES\n"
                + "(?, ?, ? ,?, ?, ?, curdate(), ?, ? ,?, ?, ?, ?);";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, assignId);
            st.setInt(2, setting);
            st.setString(3, title);
            st.setString(4, descript);
            st.setString(5, gitlabId);
            st.setString(6, gitlabUrl);
            st.setDate(7, dueDate);
            st.setInt(8, teamId);
            st.setInt(9, milestone);
            st.setString(10, functionIds);
            st.setString(11, label);
            st.setInt(12, status);
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

    public List<Team> getTeamByUser(int id, boolean isTrainer) {
        List<Team> list = new ArrayList<>();
        String sql = "SELECT c.class_id,y.team_code,y.team_id FROM class_user as cu , team as y , class as c where cu.team_id=y.team_id and c.class_id=cu.class_id and";
        if (isTrainer) {
            sql += " c.trainer_id=?";
        } else {
            sql += " cu.user_id=?";
        }
        Connection conn = new DBContext().connection;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Team t = new Team();
                Class c = new ClassDB().getClassById(rs.getString(1));
                t.setClassId(c);
                t.setTeamCode(rs.getString(2));
                t.setTeamId(rs.getInt(3));
                list.add(t);
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

    public void sync(int assignId, int gitlabId, String gitlabUrl, Date create_at, Date dueDate, int teamId, String descript, String title, int status) {
        Connection conn = new DBContext().connection;
        String sql = "INSERT INTO issue\n"
                + "(assignee_id ,issue_title, descript, gitlab_id, gitlab_url, created_at, due_date ,team_id, status)"
                + "VALUES\n"
                + "(?, ?, ?, ? ,?, ?, ?, ?, ?);";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, assignId);
            st.setString(2, title);
            if (descript == "") {
                st.setNull(3, Types.VARCHAR);
            } else {
                st.setString(3, descript);
            }
            st.setInt(4, gitlabId);
            st.setString(5, gitlabUrl);
            st.setDate(6, create_at);
            st.setDate(7, dueDate);
            st.setInt(8, teamId);
            st.setInt(9, status);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }

    public List<Issue> getIssueByTeam(int id) {
        List<Issue> list = new ArrayList<>();
        Connection conn = new DBContext().connection;
        String sql = "select * from issue where team_id=? and gitlab_id is null";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Issue(rs.getInt(1), getUser(rs.getInt(2)), getSetting(rs.getInt(3)),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(8), rs.getDate(9),
                        getTeam(rs.getInt(10)), getMileStone(rs.getInt(11)), getFunction(rs.getInt(12)), rs.getString(13), rs.getInt(14)));
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

    public boolean CheckIssueTitle(String title, int team) {
        Connection conn = new DBContext().connection;
        String sql = "select * from issue where issue_title=? and team_id=?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, title);
            st.setInt(2, team);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return true;
    }

    public void updateGitlab(int id, int gitlabId, String gitlabUrl) {
        Connection conn = new DBContext().connection;
        String sql = "UPDATE issue\n"
                + "SET gitlab_id= ?, gitlab_url =? where issue_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(3, id);
            st.setInt(1, gitlabId);
            st.setString(2, gitlabUrl);
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

}
