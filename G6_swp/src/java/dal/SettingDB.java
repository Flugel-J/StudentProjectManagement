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
import model.Setting;

/**
 *
 * @author KHANHHERE
 */
public class SettingDB extends DBContext{
    
    public String getRoleById(int id){
        Connection conn = new DBContext().connection;
        String sql = "select * from setting where setting_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)){
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) return rs.getString(3);
        } catch (Exception e) {
        } finally{
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return null;
    }
    public int getRoleID(String roll_value){
        Connection conn = new DBContext().connection;
        String sql = "select * from setting where setting_title = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, roll_value);
            ResultSet rs = st.executeQuery();
            if(rs.next()) return rs.getInt(1);
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
    
    public Setting getSettingById(int id) {
        Connection conn = new DBContext().connection;
        String sql = "select * from setting where setting_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Setting(rs.getInt(1), rs.getInt(2),
                        rs.getString(3),rs.getString(4), rs.getInt(5),
                        rs.getString(6), rs.getBoolean(7));
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
    
    public List<String> getListSettingType(){
        Connection conn = new DBContext().connection;
        String sql = "SELECT distinct type_id FROM setting";
        List<String> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs =st.executeQuery();
            while(rs.next()){
                list.add(rs.getInt(1)+"");
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
    
    

    public void updateSetting(Setting s) {
        Connection conn = new DBContext().connection;
        String sql = "update setting "
                + "set `type_id` = ?,\n"
                + "`setting_title`= ?,\n"
                + "`setting_value` = ?,\n"
                + "`display_order` = ?,\n"
                + "`note` = ?,\n"
                + "`status` = ?"
                + " where `setting_id` = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, s.getTypeId());
            st.setString(2, s.getSettingTitle());
            st.setString(3, s.getSettingValue());
            st.setInt(4, s.getDisplayOrder());
            st.setString(5, s.getNote());
            st.setBoolean(6, s.isStatus());
            st.setInt(7, s.getSettingId());
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

    public void addSetting(Setting s) {
        Connection conn = new DBContext().connection;
        String sql = "INSERT INTO `setting`\n"
                + "("
                + "`type_id`,\n"
                + "`setting_title`,\n"
                + "`setting_value`,\n"
                + "`display_order`,\n"
                + "`note`,\n"
                + "`status`) "
                + "values(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, s.getTypeId());
            st.setString(2, s.getSettingTitle());
            st.setString(3, s.getSettingValue());
            st.setInt(4, s.getDisplayOrder());
            st.setString(5, s.getNote());
            st.setBoolean(6, s.isStatus());
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
    public List<Setting> getListByPage(List<Setting> list,int start,int end){ 
        List<Setting> arr=new ArrayList<>();
        for(int i=start;i<end;i++){
            arr.add(list.get(i));
        }
        return arr;
    }
     public List<Setting> Search(String type,String status,String name){
        Connection conn = new DBContext().connection;
        List<Setting> list = new ArrayList<>();
        String sql ="select * from setting where true ";
        List<String> search= new ArrayList<>();
        if(type!=""){
            sql+="and type_id=?";
            search.add(type);
        }
        if(status!=""){
            sql+="and status=?";
            search.add(status);
        }
        if(name!=""){
            sql+="and setting_title like ?";
            search.add("%"+name+"%");
        }      
        try (PreparedStatement st = conn.prepareStatement(sql)) {
           for(int i=1;i<=search.size();i++){
               st.setString(i, search.get(i-1));
           }
            ResultSet rs =st.executeQuery();
            while(rs.next()){
                   Setting b = new Setting();
                b.setSettingId(rs.getInt(1));
                b.setTypeId(rs.getInt(2));
               b.setSettingTitle(rs.getString(3));
                b.setSettingValue(rs.getString(4));
                b.setDisplayOrder(rs.getInt(5));
                b.setStatus(rs.getBoolean(7));
                
                list.add(b);
            }
        } catch (SQLException e) {
            System.out.println(e);
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
//        SettingDB sd = new SettingDB();
//        List<Setting> s = sd.Search("3", "", "");
//        for(Setting set :s){
//            System.out.println(set.getSettingValue());
//        }
//    }
}
