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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Feature;
import model.Function;
import model.SubjectSetting;
import model.Class;

/**
 *
 * @author 03623
 */
public class FunctionDB {
    
    public ArrayList<Function> getFunctionListPage(String funamefenameaccess,String teamid,String complexityid,String priority,String status,int page_index,int size ) {
        Connection conn = new DBContext().connection;
        ArrayList<Function> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        if(funamefenameaccess==null){funamefenameaccess="";}if(teamid==null){teamid="";}if(complexityid==null){complexityid="";}
        if(priority==null){priority="";}if(status==null){status="";}
        funamefenameaccess = "%"+funamefenameaccess+"%";
        teamid = "%"+teamid+"%";complexityid = "%"+complexityid+"%";priority = "%"+priority+"%";
        status = "%"+status+"%";
        try {
        String sql = "select * from (select fu.function_id,fu.team_id,fu.function_name,fu.feature_id,fu.access_roles,fu.description,fu.complexity_id,fu.owner_id,fu.priority,fu.status ,\n" +
                "ROW_NUMBER() OVER (ORDER BY function_id ASC) as row_index from `function` as fu \n" +
                "INNER JOIN feature as fe on fe.feature_id = fu.feature_id \n" +
                "WHERE (fu.function_name like ? or fe.feature_name like ? or fu.access_roles like ?) and fu.team_id like ? \n" +
                "and fu.complexity_id like ? and fu.priority like ? and fu.status like ?) as t\n" +
                "WHERE row_index >= (? -1)*? + 1 AND row_index <= ?*?";
         st = conn.prepareStatement(sql);
            st.setString(1, funamefenameaccess);
            st.setString(2, funamefenameaccess);
            st.setString(3, funamefenameaccess);
            st.setString(4, teamid);
            st.setString(5, complexityid);
            st.setString(6, priority);
            st.setString(7, status);
            st.setInt(8, page_index);
            st.setInt(9, size);
            st.setInt(10, page_index);
            st.setInt(11, size);
         rs = st.executeQuery();
         TeamDB dbt =new TeamDB();
        FeatureDB feadb =new FeatureDB();
        FunctionDB fudb = new FunctionDB();
        UserDB udb = new UserDB();
            while (rs.next()) {
                Function f = new Function();
                f.setId(rs.getInt(1));
                f.setTeam(dbt.getTeam(String.valueOf(rs.getInt(2))));
                f.setFunctionName(rs.getString(3));
                f.setFeature(feadb.getFeatureByID(rs.getInt(4)));
                f.setAccessRoles(rs.getString(5));
                f.setDescription(rs.getString(6));
                f.setSubjectSetting(fudb.getSubjectSettingid(rs.getInt(7)));
                f.setUser(udb.getUser(rs.getInt(8)));
                f.setPriority(rs.getInt(9));
                f.setStatus(rs.getInt(10));
                list.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FunctionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return list;
    }
    
    public ArrayList<Function> getFunctionbyTeamid(int teamid) {
        Connection conn = new DBContext().connection;
        ArrayList<Function> list = new ArrayList<>();
        String sql = "SELECT DISTINCT priority, function_id,team_id,function_name,feature_id,access_roles,description,complexity_id,owner_id,priority, status  \n" +
                "FROM `function` where team_id = ? group by priority order by priority asc";
        TeamDB dbt =new TeamDB();
        FeatureDB feadb =new FeatureDB();
        FunctionDB fudb = new FunctionDB();
        UserDB udb = new UserDB();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, teamid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Function f = new Function();
                f.setId(rs.getInt(2));
                f.setTeam(dbt.getTeam(String.valueOf(rs.getInt(3))));
                f.setFunctionName(rs.getString(4));
                f.setFeature(feadb.getFeatureByID(rs.getInt(5)));
                f.setAccessRoles(rs.getString(6));
                f.setDescription(rs.getString(7));
                f.setSubjectSetting(fudb.getSubjectSettingid(rs.getInt(8)));
                f.setUser(udb.getUser(rs.getInt(9)));
                f.setPriority(rs.getInt(10));
                f.setStatus(rs.getInt(11));
                list.add(f);
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
    
    public ArrayList<Function> getFunctionbyTeamid2(int teamid) {
        Connection conn = new DBContext().connection;
        ArrayList<Function> list = new ArrayList<>();
        String sql = "SELECT function_id,team_id,function_name,feature_id,access_roles,description,complexity_id,owner_id,priority, status  \n" +
                "FROM `function` where team_id = ? order by priority asc";
        TeamDB dbt =new TeamDB();
        FeatureDB feadb =new FeatureDB();
        FunctionDB fudb = new FunctionDB();
        UserDB udb = new UserDB();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, teamid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Function f = new Function();
                f.setId(rs.getInt(1));
                f.setTeam(dbt.getTeam(String.valueOf(rs.getInt(2))));
                f.setFunctionName(rs.getString(3));
                f.setFeature(feadb.getFeatureByID(rs.getInt(4)));
                f.setAccessRoles(rs.getString(5));
                f.setDescription(rs.getString(6));
                f.setSubjectSetting(fudb.getSubjectSettingid(rs.getInt(7)));
                f.setUser(udb.getUser(rs.getInt(8)));
                f.setPriority(rs.getInt(9));
                f.setStatus(rs.getInt(10));
                list.add(f);
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

    
    public void insertFunction(Function f) {
        Connection conn = new DBContext().connection;
        String sql = "INSERT INTO `function`\n" +
                    "(`team_id`,\n" +
                    "`function_name`,\n" +
                    "`feature_id`,\n" +
                    "`access_roles`,\n" +
                    "`description`,\n" +
                    "`complexity_id`,\n" +
                    "`owner_id`,\n" +
                    "`priority`,\n" +
                    "`status`)\n" +
                    "VALUES\n" +
                    "(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            SettingDB sDB = new SettingDB();
            stm.setInt(1, f.getTeam().getTeamId());
            stm.setString(2, f.getFunctionName());
            stm.setInt(3, f.getFeature().getFeatureId());
            stm.setString(4, f.getAccessRoles());
            stm.setString(5, f.getDescription());
            stm.setInt(6, f.getSubjectSetting().getSettingId());
            stm.setInt(7, f.getUser().getUserId());
            stm.setInt(8, f.getPriority());
            stm.setInt(9, f.getStatus());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }
    
    public ArrayList<Feature> getFeaturebyTeamid(int teamid) {
        Connection conn = new DBContext().connection;
        ArrayList<Feature> list = new ArrayList<>();
        String sql = "select * from  feature where team_id = ? ";
        TeamDB dbt =new TeamDB();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, teamid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Feature f = new Feature();
                f.setFeatureId(rs.getInt(1));
                f.setTeamId(dbt.getTeam(String.valueOf(rs.getInt(2))));
                f.setFeatureName(rs.getString(3));
                f.setStatus(rs.getBoolean(4));
                list.add(f);
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
    
    public ArrayList<SubjectSetting> getListSubjectSettingBySubjectid(int classid){
        ClassDB cdb = new ClassDB();
        Class c = cdb.getClassById(String.valueOf(classid));
        
        Connection conn = new DBContext().connection;
        ArrayList<SubjectSetting> list = new ArrayList<>();
        String sql = "select * from  subject_setting where subject_id = ? ";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, c.getSubject().getSubjectId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SubjectSetting ss = new SubjectSetting();
                ss.setSettingId(rs.getInt(1));
                ss.setSubjectId(rs.getInt(2));
                ss.setTypeId(rs.getInt(3));
                ss.setSettingTitle(rs.getString(4));
                ss.setSettingValue(rs.getString(5));
                ss.setDisplayOrder(rs.getInt(6));
                ss.setStatus(rs.getBoolean(7));
                list.add(ss);
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
    
    public SubjectSetting getSubjectSettingid(int id) {
       Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM subject_setting where setting_id = ? ";
        UserDB udb = new UserDB();
        SubjectDB sdb = new SubjectDB();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                return new SubjectSetting(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getBoolean(7));
            }
        } catch (Exception e) {
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return null;
    }
    
    public int getCountPage(String funamefenameaccess,String teamid,String complexityid,String priority,String status) {
        Connection conn = new DBContext().connection;
        PreparedStatement st = null;
        ResultSet rs = null;
        if(funamefenameaccess==null){funamefenameaccess="";}if(teamid==null){teamid="";}if(complexityid==null){complexityid="";}
        if(priority==null){priority="";}if(status==null){status="";}
        funamefenameaccess = "%"+funamefenameaccess+"%";
        teamid = "%"+teamid+"%";complexityid = "%"+complexityid+"%";priority = "%"+priority+"%";
        status = "%"+status+"%";
        try {
        String sql = "select count(*) from (select fu.function_id,fu.team_id,fu.function_name,fu.feature_id,fu.access_roles,fu.description,fu.complexity_id,fu.owner_id,fu.priority,fu.status ,\n" +
                "ROW_NUMBER() OVER (ORDER BY function_id ASC) as row_index from `function` as fu \n" +
                "INNER JOIN feature as fe on fe.feature_id = fu.feature_id\n" +
                "WHERE (fu.function_name like ? or fe.feature_name like ? or fu.access_roles like ?) and fu.team_id like ? \n" +
                "and fu.complexity_id like ? and fu.priority like ? and fu.status like ?) as t ";
         st = conn.prepareStatement(sql);
            st.setString(1, funamefenameaccess);
            st.setString(2, funamefenameaccess);
            st.setString(3, funamefenameaccess);
            st.setString(4, teamid);
            st.setString(5, complexityid);
            st.setString(6, priority);
            st.setString(7, status);
         rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FunctionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return -1;
    }
    
    public int checkFuntion(String fname,int teamid) {
       Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM `function` where function_name like ? and team_id = ? ";
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, fname);
            st.setInt(2, teamid);
            rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return -1;
    }
    
    public void updateFunction(Function f) {
       Connection conn = new DBContext().connection; 
       String sql = "UPDATE `function` \n" +
                    "SET\n" +
                    "`team_id` = ?,\n" +
                    "`function_name` = ?,\n" +
                    "`feature_id` = ?,\n" +
                    "`access_roles` = ?,\n" +
                    "`description` = ?,\n" +
                    "`complexity_id` = ?,\n" +
                    "`owner_id` = ?,\n" +
                    "`priority` = ?,\n" +
                    "`status` = ?\n" +
                    "WHERE `function_id` = ?;";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            SettingDB sDB = new SettingDB();
            st.setInt(1, f.getTeam().getTeamId());
            st.setString(2, f.getFunctionName());
            st.setInt(3, f.getFeature().getFeatureId());
            st.setString(4, f.getAccessRoles());
            st.setString(5, f.getDescription());
            st.setInt(6, f.getSubjectSetting().getSettingId());
            st.setInt(7, f.getUser().getUserId());
            st.setInt(8, f.getPriority());
            st.setInt(9, f.getStatus());
            st.setInt(10, f.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(FunctionDB.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }
    
    public Feature getFeaturebyTeamidbyfname(int teamid,String feaName) {
        Connection conn = new DBContext().connection;
        String sql = "select * from  feature where team_id = ? and feature_name like ?";
        TeamDB dbt = new TeamDB();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, teamid);
            st.setString(2, feaName);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Feature f = new Feature();
                f.setFeatureId(rs.getInt(1));
                f.setTeamId(dbt.getTeam(String.valueOf(rs.getInt(2))));
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
    public SubjectSetting getSubjectSettingbyTile(String tile, int subjectid) {
       Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM subject_setting where  setting_title like ? and subject_id = ?";
        UserDB udb = new UserDB();
        SubjectDB sdb = new SubjectDB();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, tile);
            st.setInt(2, subjectid);
            rs = st.executeQuery();
            if (rs.next()) {
                return new SubjectSetting(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getBoolean(7));
            }
        } catch (Exception e) {
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return null;
    }
    public void updateFunctionStatus(Function f) {
       Connection conn = new DBContext().connection; 
       String sql = "UPDATE `function` \n" +
                    "SET\n" +
                    "`status` = ?\n" +
                    "WHERE `function_id` = ?;";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            SettingDB sDB = new SettingDB();
            st.setInt(1, f.getStatus());
            st.setInt(2, f.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(FunctionDB.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    
}
    public Function getFunctionbyid(String fuid) {
       Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM `function` where `function_id` = ?";
        UserDB udb = new UserDB();
        SubjectDB sdb = new SubjectDB();
        TeamDB dbt =new TeamDB();
        FeatureDB feadb = new FeatureDB();
        FunctionDB fudb = new FunctionDB();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, Integer.parseInt(fuid));
            rs = st.executeQuery();
            if (rs.next()) {
                Function f = new Function();
                f.setId(rs.getInt(1));
                f.setTeam(dbt.getTeam(String.valueOf(rs.getInt(2))));
                f.setFunctionName(rs.getString(3));
                f.setFeature(feadb.getFeatureByID(rs.getInt(4)));
                f.setAccessRoles(rs.getString(5));
                f.setDescription(rs.getString(6));
                f.setSubjectSetting(fudb.getSubjectSettingid(rs.getInt(7)));
                f.setUser(udb.getUser(rs.getInt(8)));
                f.setPriority(rs.getInt(9));
                f.setStatus(rs.getInt(10));
                return f;
            }
        } catch (Exception e) {
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return null;
    }
    public ArrayList<Class> getClasslistStudent(int idUser) {
       Connection conn = new DBContext().connection;
        ArrayList<Class> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
        String sql = " SELECT * FROM class_user where user_id = ?";
         st = conn.prepareStatement(sql);
            st.setInt(1, idUser);
         rs = st.executeQuery();
        ClassDB cldb =new  ClassDB();
            while (rs.next()) {
                Class c = cldb.getClassById(String.valueOf(rs.getInt(1)));
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FunctionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
        return list;
    }
//        public static void main(String[] args) {
//        FunctionDB fdb =new FunctionDB();
//        SubjectSetting s = fdb.getSubjectSettingid(2);
//            System.out.println(s.getSettingId());
//            System.out.println(s.getSettingTitle());
//    }
}
