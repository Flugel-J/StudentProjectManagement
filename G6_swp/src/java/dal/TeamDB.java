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
import model.Team;

/**
 *
 * @author Admin
 */
public class TeamDB extends DBContext {

    public List<Team> getAllTeam() {
        Connection conn = new DBContext().connection;
        String sql = "select * from team";
        List<Team> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            SettingDB sDB = new SettingDB();
            while (rs.next()) {
                list.add(new Team(rs.getInt(1), rs.getString(2), new model.Class(rs.getInt(3), getClassCode(rs.getInt(3))), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7)));
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

    public List getListClassCode() {
        Connection conn = new DBContext().connection;
        String sql = "select y.class_id , y.class_code from (SELECT distinct class_id FROM team) x left join (select * from class) y on x.class_id = y.class_id";
        List list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            SettingDB sDB = new SettingDB();
            while (rs.next()) {
                list.add(new model.Class(rs.getInt(1), rs.getString(2)));
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

    public Team getTeam(String id) {
        Connection conn = new DBContext().connection;
        String sql = "select * from team where team_id = ?";
        List<Team> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            ClassDB cdb = new ClassDB();
            if (rs.next()) {
                return new Team(rs.getInt(1), rs.getString(2),
                        cdb.getClassById(rs.getString(3)), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));
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

    public List getAllClass() {
        Connection conn = new DBContext().connection;
        String sql = "SELECT class_id, class_code FROM class;";
        List list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            SettingDB sDB = new SettingDB();
            while (rs.next()) {
                list.add(new model.Class(rs.getInt(1), rs.getString(2)));
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

    public void updateTeam(String teamId, String teamCode, String className, String topicN, String topC, String git, String status) {
        Connection conn = new DBContext().connection;
        String sql = "UPDATE team SET team_code =? ,class_id = ?,topic_code = ?,topic_name = ?, gitlab_url = ?, status = ? WHERE team_id = ?;";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, teamCode);
            st.setString(2, className);
            st.setString(3, topC);
            st.setString(4, topicN);
            st.setString(5, git);
            st.setString(6, status);
            st.setString(7, teamId);
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

    public List getTeamById(String teamCode, String className) {
        Connection conn = new DBContext().connection;
        List<String> list = new ArrayList<>();
        String sql = "SELECT team_code FROM team where class_id=? and team_code not in\n"
                + "(select team_code from team where team_code = ? and class_id = ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, className);
            st.setString(2, teamCode);
            st.setString(3, className);
            ResultSet rs = st.executeQuery();
            SettingDB sDB = new SettingDB();
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

    //UserList-Search
    public List<Team> Search(String search_class, String search_status, String search_value) {
        List<Team> list = new ArrayList<>();
        String sql;
        List<String> search = new ArrayList<>();
        if ((search_class == null && search_status == null && search_value == null) || (search_class == "" && search_status == "" && search_value == "")) {
            sql = "SELECT * FROM team";
        } else {
            sql = "SELECT * FROM team where true";
            if (search_class != "") {
                sql += " and class_id=?";
                search.add(search_class);
            }
            if (search_status != "") {
                sql += " and status=?";
                search.add(search_status);
            }
            if (search_value != "") {
                sql += " and topic_name like ?";
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
                list.add(new Team(rs.getInt(1), rs.getString(2), new model.Class(rs.getInt(3), getClassCode(rs.getInt(3))), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7)));
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
    public List<Team> getListByPage(List<Team> list, int start, int end) {
        List<Team> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public void updateStatus(String id, String status) {
        String sql = "UPDATE team SET status = ? WHERE team_id  = ?;";
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

    public void add(String teamCode, String className, String topicN, String topC, String git, String status) {
        String sql = "INSERT INTO `team`\n"
                + "(team_code, class_id, topic_code, topic_name, gitlab_url, status)\n"
                + "VALUES\n"
                + "(?, ?, ? ,?, ?, ?);";
        Connection conn = new DBContext().connection;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, teamCode);
            ps.setString(2, className);
            ps.setString(3, topC);
            ps.setString(4, topicN);
            ps.setString(5, git);
            ps.setBoolean(6, Boolean.parseBoolean(status));
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

    // list to add team_eval
    public List<Team> getListTeamOfTrainer(String tra) {
        String sql = "SELECT distinct t.team_id FROM  class c, team t, class_user cu \n"
                + "where c.class_id = t.class_id and t.team_id = cu.team_id and c.trainer_id = ? and t.status = 1 and c.status = 1";
        Connection conn = new DBContext().connection;
        List<Team> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, tra);
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

    public List<Team> getListTeamOfClass(String search_class, String stu) {
        String sql = "select distinct t.team_id from team t, class_user cu\n"
                + "where t.team_id = cu.team_id and t.class_id = ? ";
        Connection conn = new DBContext().connection;
        List<Team> list = new ArrayList<>();
        List<String> searchList = new ArrayList<>();
        searchList.add(search_class);
        if (stu != "") {
            sql += " and cu.user_id = ? ";
            searchList.add(stu);
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

//    public static void main(String[] args) {
//        String teamCode = "G2";
//        String className = "2";
//        String topicN = "qưeqwe";
//        String topC = "qưeqwe";
//        String git = "qưeqwe";
//        String status = "1";
//        TeamDB db = new TeamDB();
//        db.add(teamCode, className, topicN, topC, git, status);
//        
//    }
}
