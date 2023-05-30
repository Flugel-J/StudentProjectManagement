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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClassUser;
import model.Class;
import model.Team;
import model.User;

public class ClassUserDB extends DBContext{
    
     public ArrayList<ClassUser> getClassUserlistPage(String team_leader,String class_code,String dropout_date,String status,String class_year,String class_term,String full_name, String roll_number,int page_index, int size ) {
        Connection conn = new DBContext().connection;
         ArrayList<ClassUser> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
        if(team_leader==null||team_leader.equals("")){ team_leader = "!= -1";}else{team_leader = "= "+team_leader;}
        if(dropout_date==null||dropout_date.equals("")){ dropout_date = "";}if(dropout_date.equals("true")){ dropout_date = "and class_user.dropout_date is not null";} if(dropout_date.equals("false")){ dropout_date = "and class_user.dropout_date is null";}
        if(class_code==null||class_code.equals("")){ class_code = "";}
        if(status==null||status.equals("")){ status = "!= -1";}else{status = "= "+status;}
        if(class_year==null||class_year.equals("")){ class_year = "!= -1";}else{class_year = "= "+class_year;}
        if(class_term==null||class_term.equals("")){ class_term = "!= -1";}else{class_term = "= "+class_term;}
        if(full_name==null||full_name.equals("")){ full_name = "";}
        if(roll_number==null||roll_number.equals("")){ roll_number = "";}
        String sql = "SELECT * from(\n" +
                        "SELECT class_user.class_id,class_user.team_id,class_user.user_id,class_user.team_leader,class_user.dropout_date,user_notes,class_user.ongoing_eval,class_user.final_pres_eval,class_user.final_topic_eval,class_user.status,ROW_NUMBER() OVER \n" +
                        "(ORDER BY class_user.class_id ASC) as row_index FROM \n" +
                        "class_user\n" +
                        "INNER JOIN class ON class_user.class_id = class.class_id\n" +
                        "INNER JOIN user ON user.user_id = class_user.user_id\n" +
                        "where (class_user.team_leader "+team_leader+" and class.class_code like '%"+class_code+"%' "+dropout_date+" and \n" +
                        "class_user.status "+status+" and class.class_year "+class_year+" and class.class_term "+class_term+" ) and  \n" +
                        "(user.full_name like '%"+full_name+"%' OR user.roll_number like '%"+roll_number+"%') ) as t\n" +
                        "WHERE row_index >= (? -1)*? + 1 AND row_index <= ?*? ";
         st = conn.prepareStatement(sql);
            st.setInt(1, page_index);
            st.setInt(2, size);
            st.setInt(3, page_index);
            st.setInt(4, size);
         rs = st.executeQuery();
        UserDB udb = new UserDB();
            while (rs.next()) {
                list.add(new ClassUser(getClassbyid(rs.getInt(1)), getTeambyid(rs.getInt(2)) , udb.getUser(rs.getInt(3)),rs.getBoolean(4), 
                        rs.getDate(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getBoolean(10)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassUserDB.class.getName()).log(Level.SEVERE, null, ex);
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
     
   public Class getClassbyid(int id) {
       Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM class\n" +
                "where class_id = ?";
        UserDB udb = new UserDB();
        SubjectDB sdb = new SubjectDB();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
             st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Class c = new Class();
                return new Class(rs.getInt(1),rs.getString(2),udb.getUser(rs.getInt(3)), sdb.getSubjectById(rs.getString(4)),rs.getInt(5),rs.getInt(6),rs.getBoolean(7),rs.getInt(8));
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
   
   public Team getTeambyid(int id) {
       Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM team\n" +
                "where team_id = ?";
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
             st = conn.prepareStatement(sql);
            st.setInt(1, id);
             rs = st.executeQuery();
            if (rs.next()) {
                Team c = new Team();
                return new Team(rs.getInt(1),rs.getString(2), getClassbyid(rs.getInt(3)), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));
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
   public int getCountClassuser(String team_leader,String class_code,String dropout_date,String status,String class_year,String class_term,String full_name, String roll_number) {
       Connection conn = new DBContext().connection;
       PreparedStatement st = null;
       ResultSet rs = null;
        try {
            if(team_leader==null||team_leader.equals("")){ team_leader = "!= -1";}else{team_leader = "= "+team_leader;}
            if(dropout_date==null||dropout_date.equals("")){ dropout_date = "";}if(dropout_date.equals("true")){ dropout_date = "and class_user.dropout_date is not null";} if(dropout_date.equals("false")){ dropout_date = "and class_user.dropout_date is null";}
            if(class_code==null||class_code.equals("")){ class_code = "";}
            if(status==null||status.equals("")){ status = "!= -1";}else{status = "= "+status;}
            if(class_year==null||class_year.equals("")){ class_year = "!= -1";}else{class_year = "= "+class_year;}
            if(class_term==null||class_term.equals("")){ class_term = "!= -1";}else{class_term = "= "+class_term;}
            if(full_name==null||full_name.equals("")){ full_name = "";}
            if(roll_number==null||roll_number.equals("")){ roll_number = "";}
            String sql = "SELECT count(*) from(\n" +
                        "SELECT class_user.class_id,class_user.team_id,class_user.user_id,class_user.team_leader,class_user.dropout_date,user_notes,class_user.ongoing_eval,class_user.final_pres_eval,class_user.final_topic_eval,class_user.status,ROW_NUMBER() OVER \n" +
                        "(ORDER BY class_user.class_id ASC) as row_index FROM \n" +
                        "class_user\n" +
                        "INNER JOIN class ON class_user.class_id = class.class_id\n" +
                        "INNER JOIN user ON user.user_id = class_user.user_id\n" +
                        "where (class_user.team_leader "+team_leader+" and class.class_code like '%"+class_code+"%' "+dropout_date+" and \n" +
                        "class_user.status "+status+" and class.class_year "+class_year+" and class.class_term "+class_term+" ) and  \n" +
                        "(user.full_name like '%"+full_name+"%' OR user.roll_number like '%"+roll_number+"%') ) as t\n" +
                        " ";
             st = conn.prepareStatement(sql);
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
   
   public ArrayList<Class> getClasslistTrainer(int id) {
       Connection conn = new DBContext().connection;
        ArrayList<Class> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
        String sql = " SELECT * FROM class\n" +
                    "where trainer_id = ?";
         st = conn.prepareStatement(sql);
            st.setInt(1, id);
         rs = st.executeQuery();
        UserDB udb = new UserDB();
        SubjectDB sdb = new SubjectDB();
            while (rs.next()) {
                Class c = new Class(rs.getInt(1),rs.getString(2),udb.getUser(rs.getInt(3)), sdb.getSubjectById(rs.getString(4)),rs.getInt(5),rs.getInt(6),rs.getBoolean(7),rs.getInt(8));
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassUserDB.class.getName()).log(Level.SEVERE, null, ex);
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
   
   public ArrayList<String> getListClassYear(int id) {
       Connection conn = new DBContext().connection;
        ArrayList<String> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
        String sql = " SELECT DISTINCT class_year FROM class\n" +
                    "where trainer_id = ?";
         st = conn.prepareStatement(sql);
            st.setInt(1, id);
         rs = st.executeQuery();
            while (rs.next()) {
                list.add(String.valueOf( rs.getInt(1) ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassUserDB.class.getName()).log(Level.SEVERE, null, ex);
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
   
   public void changeStatusClassuser(boolean status, int teamid, int classid, int userid) {
       Connection conn = new DBContext().connection;
        String sql = "UPDATE `class_user` SET\n" +
                    "`status` = ? \n" +
                    "WHERE `class_id` = ? AND `team_id` = ? AND `user_id` = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);

            st.setBoolean(1, status);
            st.setInt(2, classid);
            st.setInt(3, teamid);
            st.setInt(4, userid);
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ClassUserDB.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }
   
   public ClassUser getClassbUser(int userid, int teamid, int classid) {
       Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM class_user\n" +
                "WHERE class_id = ? AND team_id = ? AND user_id = ?";
        UserDB udb = new UserDB();
        SubjectDB sdb = new SubjectDB();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
             st = conn.prepareStatement(sql);
            st.setInt(1, classid);
            st.setInt(2, teamid);
            st.setInt(3, userid);
            rs = st.executeQuery();
            if (rs.next()) {
                return new ClassUser(getClassbyid(rs.getInt(1)), getTeambyid(rs.getInt(2)) , udb.getUser(rs.getInt(3)),rs.getBoolean(4), 
                        rs.getDate(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getBoolean(10));
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
   
   public ArrayList<Team> getListTeamidInClass(int classid) {
       Connection conn = new DBContext().connection;
        ArrayList<Team> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
        String sql = " SELECT * FROM team\n" +
                    "where class_id = ?";
         st = conn.prepareStatement(sql);
            st.setInt(1, classid);
         rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Team(rs.getInt(1),rs.getString(2), getClassbyid(rs.getInt(3)), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassUserDB.class.getName()).log(Level.SEVERE, null, ex);
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
   
   public void updateClassUser(int class_id, int teamid, int user_id, boolean team_leader,Date dropout_date, String user_notes,int teamchange) {
       Connection conn = new DBContext().connection; 
       String sql = "UPDATE `class_user`\n" +
                    "SET\n" +
                    "`team_id` = ?,\n" +
                    "`team_leader` = ?,\n" +
                    "`dropout_date` = ?,\n" +
                    "`user_notes` = ?\n" +
                    "WHERE `class_id` = ? AND `team_id` = ? AND `user_id` = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, teamchange);
            st.setBoolean(2, team_leader);
            st.setDate(3, dropout_date);
            st.setString(4, user_notes);
            st.setInt(5, class_id);
            st.setInt(6, teamid);
            st.setInt(7, user_id);
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ClassUserDB.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }
   
   public ArrayList<ClassUser> getListClassUserByClassCode(String ClassCode) {
       Connection conn = new DBContext().connection;
        ArrayList<ClassUser> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
        String sql = "SELECT * FROM class_user\n" +
            "INNER JOIN class ON class_user.class_id = class.class_id\n" +
            "where class.class_code like ?";
         st = conn.prepareStatement(sql);
            st.setString(1, ClassCode);
         rs = st.executeQuery();
         UserDB udb = new UserDB();
            while (rs.next()) {
                list.add(new ClassUser(getClassbyid(rs.getInt(1)), getTeambyid(rs.getInt(2)) , udb.getUser(rs.getInt(3)),rs.getBoolean(4), 
                        rs.getDate(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getBoolean(10)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassUserDB.class.getName()).log(Level.SEVERE, null, ex);
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
   
   public Team getTeambyteamcode_classid(int classid, String teamcode) {
       Connection conn = new DBContext().connection;
        String sql = "select * from team\n" +
            "where class_id = ? and team_code = ?";
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
             st = conn.prepareStatement(sql);
            st.setInt(1, classid);
            st.setString(2, teamcode);
             rs = st.executeQuery();
            if (rs.next()) {
                Team c = new Team();
                return new Team(rs.getInt(1),rs.getString(2), getClassbyid(rs.getInt(3)), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));
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
   
   public User getUserbyRollNumber(String RollNumber) {
        Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM user\n" +
                "where roll_number = ? or roll_number = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)){
            st.setString(1, RollNumber.toUpperCase());
            st.setString(2, RollNumber.toLowerCase());
            ResultSet rs = st.executeQuery();
            SettingDB sDB = new SettingDB();
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getBoolean(4),
                        rs.getDate(5), rs.getString(6),
                        rs.getString(7),
                        rs.getString(8), rs.getString(9), rs.getString(10),
                        sDB.getRoleById(rs.getInt(11)),
                        rs.getBoolean(12));
            }
        } catch (Exception e) {
        } finally{
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return null;
    }
   
   public void updateClassUserImport(int class_id, int teamid, int user_id, boolean team_leader,Date dropout_date, String user_notes,float ongoing_eval, float final_pres_eval,float final_topic_eval) {
       Connection conn = new DBContext().connection; 
       String sql = "UPDATE `class_user`\n" +
                    "SET\n" +
                    "`team_leader` = ?,\n" +
                    "`dropout_date` = ?,\n" +
                    "`user_notes` = ?,\n" +
                    "`ongoing_eval` = ?,\n" +
                    "`final_pres_eval` = ?,\n" +
                    "`final_topic_eval` = ?\n" +
                    "WHERE `class_id` = ? AND `team_id` = ? AND `user_id` = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setBoolean(1, team_leader);
            st.setDate(2, dropout_date);
            st.setString(3, user_notes);
            st.setFloat(4, ongoing_eval);
            st.setFloat(5, final_pres_eval);
            st.setFloat(6, final_topic_eval);
            st.setInt(7, class_id);
            st.setInt(8, teamid);
            st.setInt(9, user_id);
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ClassUserDB.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }
   
   public Class getClassbyClassCodeTrainerid(int id, String classcode) {
       Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM class\n" +
            "where (class_code = ? and trainer_id = ?) or (class_code = ? and trainer_id = ?)";
        UserDB udb = new UserDB();
        SubjectDB sdb = new SubjectDB();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
             st = conn.prepareStatement(sql);
            st.setString(1, classcode.toUpperCase());
            st.setInt(2, id);
            st.setString(3, classcode.toUpperCase());
            st.setInt(4, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Class c = new Class();
                return new Class(rs.getInt(1),rs.getString(2),udb.getUser(rs.getInt(3)), sdb.getSubjectById(rs.getString(4)),rs.getInt(5),rs.getInt(6),rs.getBoolean(7),rs.getInt(8));
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
   
   public ClassUser getCheckClassbUser(int userid, int teamid, int classid) {
       Connection conn = new DBContext().connection;
        String sql = "select * from class_user\n" +
            "where team_id = ? and user_id = ? and class_id = ?";
        UserDB udb = new UserDB();
        SubjectDB sdb = new SubjectDB();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
             st = conn.prepareStatement(sql);
            st.setInt(1, teamid);
            st.setInt(2, userid);
            st.setInt(3, classid);
            rs = st.executeQuery();
            if (rs.next()) {
                return new ClassUser(getClassbyid(rs.getInt(1)), getTeambyid(rs.getInt(2)) , udb.getUser(rs.getInt(3)),rs.getBoolean(4), 
                        rs.getDate(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getBoolean(10));
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
   
   public ClassUser getCheckClassbUserbyUserid(int userid, int classid) {
       Connection conn = new DBContext().connection;
        String sql = "select * from class_user\n" +
            "where user_id = ? and class_id = ?";
        UserDB udb = new UserDB();
        SubjectDB sdb = new SubjectDB();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
             st = conn.prepareStatement(sql);
            st.setInt(1, userid);
            st.setInt(2, classid);
            rs = st.executeQuery();
            if (rs.next()) {
                return new ClassUser(getClassbyid(rs.getInt(1)), getTeambyid(rs.getInt(2)) , udb.getUser(rs.getInt(3)),rs.getBoolean(4), 
                        rs.getDate(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getBoolean(10));
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
   
   public void insertTeam(String teamcode, int classid) {
        Connection conn = new DBContext().connection;
        String sql = "INSERT INTO `team`\n" +
                        "(`team_code`,\n" +
                        "`class_id`,\n" +
                        "`topic_code`,\n" +
                        "`topic_name`,\n" +
                        "`status`)\n" +
                        "VALUES\n" +
                        "(?,?,\n" +
                        "'not yet',\n" +
                        "'not yet',\n" +
                        "?)";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            SettingDB sDB = new SettingDB();
            stm.setString(1, teamcode);
            stm.setInt(2, classid);
            stm.setBoolean(3, true);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClassUserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }
   public void insertClassUser(ClassUser c) {
        Connection conn = new DBContext().connection;
        String sql = "INSERT INTO `class_user`\n" +
                    "(`class_id`,\n" +
                    "`team_id`,\n" +
                    "`user_id`,\n" +
                    "`team_leader`,\n" +
                    "`dropout_date`,\n" +
                    "`user_notes`,\n" +
                    "`status`)\n" +
                    "VALUES\n" +
                    "(?,?,?,?,?,?,?)";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            SettingDB sDB = new SettingDB();
            stm.setInt(1, c.getClassId().getClassId());
            stm.setInt(2, c.getTeam().getTeamId());
            stm.setInt(3, c.getUser().getUserId());
            stm.setBoolean(4, c.isTeamLeader());
            stm.setDate(5, c.getDropoutDate());
            stm.setString(6, c.getUserNotes());
            stm.setBoolean(7, true);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClassUserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                /* Ignored */
            }
        }
    }
   
   public String getTeamCodebyid(int Teamid) {
       Connection conn = new DBContext().connection;
        String sql = "SELECT * FROM team\n" +
                    "where team_id = ?";
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
             st = conn.prepareStatement(sql);
            st.setInt(1, Teamid);
             rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(2);
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
   
    public double grade(ClassUser cu, int isOngoing) {
        String sql = "select sum((ie.grade+ie.bonus)*i.eval_weight/100) from  iteration i, iteration_eval ie\n"
                + "where ie.iteration_id = i.iteration_id and i.is_ongoing = ? \n"
                + "and ie.class_id = ? and ie.team_id = ? and ie.user_id = ? \n"
                + "group by ie.class_id, ie.team_id, ie.user_id";
        DecimalFormat df = new DecimalFormat("#.##");
        double result = 0;
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, isOngoing);
            st.setInt(2, cu.getClassId().getClassId());
            st.setInt(3, cu.getTeam().getTeamId());
            st.setInt(4, cu.getUser().getUserId());
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

    public void updateGrade(ClassUser cu) {
        String sql = "UPDATE `class_user`\n"
                + "SET `ongoing_eval` = ? , `final_pres_eval` = ? , `final_topic_eval` = ?\n"
                + "WHERE `class_id` = ? AND `team_id` = ? AND `user_id` = ?";
        Connection conn = new DBContext().connection;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDouble(1, cu.getOngoingEval());
            st.setDouble(2, cu.getFinalPresEval());
            st.setDouble(3, cu.getFinalTopicEval());
            st.setInt(4, cu.getClassId().getClassId());
            st.setInt(5, cu.getTeam().getTeamId());
            st.setInt(6, cu.getUser().getUserId());
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
   
//    public static void main(String[] args) {
//        ClassUserDB s = new ClassUserDB();
//        Team a = s.getTeambyteamcode_classid(2, "G7");
//        System.out.println(a);
//        s.insertTeam("G7", 2);
//        System.out.println();
////        for (ClassUser u : a) {
//            
//        //}
//          //System.err.println(s.getCountClassuser(null, null, null, null, null, null, null, null)); 
//    }
     
}


