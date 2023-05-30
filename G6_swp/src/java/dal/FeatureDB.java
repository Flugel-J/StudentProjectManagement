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
import model.Feature;
import model.Team;
import dal.ClassUserDB;

/**
 *
 * @author phamt
 */
public class FeatureDB extends DBContext {

    public int count(String search_value, String status) {
        Connection conn = new DBContext().connection;
        String sql = "select count(*) from feature as f, team as t\n"
                + " where f.team_id=t.team_id ";
        List<String> search = new ArrayList<>();

        if (!"".equals(search_value)) {
            sql += " and (t.topic_name like ? or f.feature_name like ?) ";
            search.add("%" + search_value + "%");
            search.add("%" + search_value + "%");
        }

        if (!"".equals(status)) {
            sql += " and f.status =?";
            search.add(status);
        }
        try (PreparedStatement stm = conn.prepareStatement(sql)){
            for (int i = 1; i <= search.size(); i++) {
                stm.setString(i, search.get(i - 1));
            }
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
               return rs.getInt(1);
            }
        } catch (SQLException e) {
        } finally{
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public List<Feature> seach(String search_value, String status, int curPage, int NoPage) {
        Connection conn = new DBContext().connection;
        List<Feature> list = new ArrayList<>();
        String sql = "select * from  (select f.feature_id, concat(t.topic_name,'(', t.topic_code,')') as topic, f.feature_name, f.status ,\n"
                + "              row_number() over (order by f.feature_id) \n"
                + "               as row_index from feature as f, team as t\n"
                + "                where f.team_id=t.team_id ";
        List<String> search = new ArrayList<>();

        if (!"".equals(search_value)) {
            sql += " and (t.topic_name like ? or f.feature_name like ?) ";
            search.add("%" + search_value + "%");
            search.add("%" + search_value + "%");
        }

        if (!"".equals(status)) {
            sql += " and f.status =?";
            search.add(status);
        }
        sql += ") as t where row_index >= (?-1)*?+1 and row_index <= ?*?";
        search.add(curPage + "");
        search.add(NoPage + "");
        search.add(curPage + "");
        search.add(NoPage + "");
        try (PreparedStatement stm = conn.prepareStatement(sql)){
            for (int i = 1; i <= search.size(); i++) {
                stm.setString(i, search.get(i - 1));
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Feature f = new Feature(rs.getInt(1), new Team(rs.getString(2)), rs.getString(3), rs.getBoolean(4));
                list.add(f);
            }
        } catch (SQLException e) {
        } finally{
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return list;
    }

    public void addFeature(Feature f) {
        Connection conn = new DBContext().connection;
        String sql = "INSERT INTO `feature`\n"
                + "(`team_id`,\n"
                + "`feature_name`,\n"
                + "`status`)\n"
                + "VALUES\n"
                + "(?,\n"
                + "?,\n"
                + "?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, f.getTeamId().getTeamId());
            st.setString(2, f.getFeatureName());
            st.setBoolean(3, f.isStatus());
            st.executeUpdate();
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

    public void updateFeature(Feature f) {
        Connection conn = new DBContext().connection;
        String sql = "UPDATE `feature`\n"
                + "SET\n"
                + "`team_id` = ?,\n"
                + "`feature_name` = ?,\n"
                + "`status` = ?\n"
                + "WHERE `feature_id` = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, f.getTeamId().getTeamId());
            st.setString(2, f.getFeatureName());
            st.setBoolean(3, f.isStatus());
            st.setInt(4, f.getFeatureId());
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

    public Feature getFeatureByID(int id) {
        Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM feature as f inner join team as t on f.team_id = t.team_id where feature_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Feature f = new Feature();
                Team t = new Team();
                f.setFeatureId(rs.getInt(1));
                t.setTeamId(rs.getInt(2));
                f.setTeamId(t);
                t.setTopicCode(rs.getString(8));
                t.setTopicName(rs.getString(9));
                f.setFeatureName(rs.getString(3));
                f.setStatus(rs.getBoolean(4));
                return f;
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

    public List<String> getListFeatureName() {
        Connection conn = new DBContext().connection;
        String sql = "SELECT distinct feature_name FROM feature";
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

//    public static void main(String[] args) {
//        FeatureDB f = new FeatureDB();
//        Feature fe = new Feature();

//        fe.setFeatureId(1);
//        fe.setTeamId(new Team(1, "aadd44444"));
//        fe.setFeatureName("Admin");
//        fe.setStatus(true);
//        f.updateFeature(fe);
//        System.out.println(new FeatureDB().getFeatureByID(1).getFeatureName());
//        System.out.println(new FeatureDB().seach("0", "", 1, 4));
//          Feature fe = new Feature(1, new Team(Integer.parseInt("3"),""), "student", true);
//          f.addFeature(fe);
//    }

}
