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
import model.MemEval;
import model.UserEval;

/**
 *
 * @author KHANHHERE
 */
public class UserEvalDB {

    public List<UserEval> search(String search_class, String search_team,
            String search_iter, String search_value, String stu, String tra, int curPage, int numPerPage) {
        List<UserEval> list = new ArrayList<>();
        Connection conn = new DBContext().connection;
        String sql = " select * from "
                + " (select ie.eval_id, row_number() over "
                + " (order by ie.iteration_id, ie.team_id) as row_index from iteration_eval ie , class c, user u \n"
                + " where ie.class_id = c.class_id and ie.user_id = u.user_id ";
        List<String> searchList = new ArrayList<>();
        if (search_class != "") {
            sql += " and ie.class_id = ? ";
            searchList.add(search_class);
        }
        if (search_team != "") {
            sql += " and ie.team_id = ? ";
            searchList.add(search_team);
        }
        if (search_iter != "") {
            sql += " and ie.iteration_id = ? ";
            searchList.add(search_iter);
        }
        if (search_value != "") {
            sql += " and u.full_name like ? ";
            searchList.add("%" + search_value + "%");
        }
        if (stu != "") {
            sql += " and ie.user_id = ? ";
            searchList.add(stu);
        }
        if (tra != "") {
            sql += " and c.trainer_id = ? ";
            searchList.add(tra);
        }
        sql += " )as t ";
        if (curPage != 0) {
            searchList.add(curPage + "");
            searchList.add(numPerPage + "");
            searchList.add(curPage + "");
            searchList.add(numPerPage + "");
            sql += " where row_index >= (?-1)*?+1 and row_index <= ?*?";
        }
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 1; i <= searchList.size(); i++) {
                st.setString(i, searchList.get(i - 1));
            }
            ResultSet rs = st.executeQuery();
            IterEvalDB iedb = new IterEvalDB();
            while (rs.next()) {
                IterEval ie = iedb.getIEById(rs.getString(1));
                list.add(new UserEval(ie));
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

    public int count(String search_class, String search_team, String search_iter,
            String search_value, String stu, String tra) {
        int result = 0;
        Connection conn = new DBContext().connection;
        String sql = " select count(*) from iteration_eval ie , class c, user u \n"
                + " where ie.class_id = c.class_id and ie.user_id = u.user_id ";
        List<String> searchList = new ArrayList<>();
        if (search_class != "") {
            sql += " and ie.class_id = ? ";
            searchList.add(search_class);
        }
        if (search_team != "") {
            sql += " and ie.team_id = ? ";
            searchList.add(search_team);
        }
        if (search_iter != "") {
            sql += " and ie.iteration_id = ? ";
            searchList.add(search_iter);
        }
        if (search_value != "") {
            sql += " and u.full_name like ? ";
            searchList.add("%" + search_value + "%");
        }
        if (stu != "") {
            sql += " and ie.user_id = ? ";
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

    public void importData(List<UserEval> listUE) {
        IterEvalDB iedb = new IterEvalDB();
        MemEvalDB medb = new MemEvalDB();
        for(UserEval ue: listUE){
            IterEval ieImport = ue.getIe();
            IterEval ie = iedb.getIEById(ieImport.getId()+"");
            ie.setBonus(ieImport.getBonus());
            ie.setNote(ieImport.getNote());
            iedb.update(ie);
            List<MemEval> listME = ue.getMe();
            for(MemEval me: listME){
                int check = medb.isMEExist(me);
                if(check == -1) medb.add(me);
                else {
                    MemEval me2 = medb.getMEById(check+"");
                    me2.setGrade(me.getGrade());
                    medb.update(me2);
                }
            }
            
            
        }
    }
    
//    public static void main(String[] args) {
//        UserEvalDB uedb = new UserEvalDB();
//        List<UserEval> list = uedb.search("1", "", "", "", "", "", 1, 4);
//        System.out.println(list.get(0).getIe().getId());
//    }

    
}
