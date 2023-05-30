/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extension;

import dal.IssueDB;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Issue;
import model.Milestone;
import org.json.JSONException;
import org.json.JSONObject;

public class GitlabSync {

    public static String getGroupId(String name) {
        try {
            String url = "https://gitlab.com/api/v4/groups?search=" + name + "&top_level_only=true";

            URL obj = null;
            try {
                obj = new URL(url);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            }

            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection) obj.openConnection();
            } catch (IOException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                con.setRequestMethod("GET");
            } catch (ProtocolException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            }
            //add request header
            //add request header

            con.setRequestProperty("Authorization", "Bearer glpat-i61ZKHVbXfDfNNkT86z-");

//            if (con.getResponseCode() != 200) {
//                  throw new RuntimeException("Failed : HTTP error code : "
//                        + con.getResponseCode());
//            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (con.getInputStream())));

            String o;
            String output = null;
            while ((o = br.readLine()) != null) {
                o = o.replaceAll("\\},\\{", "}\n{");
                if (o.charAt(1) != '{') {
                    return "";
                }
                output = o.substring(1, o.length() - 1);

            }
            br.close();

            JSONObject json = new JSONObject(output);
            return json.getInt("id") + "";

        } catch (IOException ex) {
            Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return "";
    }

    public static String getSubGroupId(String Class, String name) {
        String group = getGroupId(Class);
        if (group != "") {
            try {

                String url = "https://gitlab.com/api/v4/groups/" + group + "/subgroups?search=" + name;

                URL obj = null;
                try {
                    obj = new URL(url);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
                }

                HttpURLConnection con = null;
                try {
                    con = (HttpURLConnection) obj.openConnection();
                } catch (IOException ex) {
                    Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    con.setRequestMethod("GET");
                } catch (ProtocolException ex) {
                    Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
                }
                //add request header
                //add request header

                con.setRequestProperty("Authorization", "Bearer glpat-i61ZKHVbXfDfNNkT86z-");

//            if (con.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + con.getResponseCode());
//            }
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (con.getInputStream())));

                String o;
                String output = null;
                while ((o = br.readLine()) != null) {
                    o = o.replaceAll("\\},\\{", "}\n{");
                    output = o.substring(1, o.length() - 1);

                }
                br.close();

                JSONObject json = new JSONObject(output);
                return json.getInt("id") + "";

            } catch (IOException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        return "";
    }

    public static String getProjectId(String group) {
        try {
            String url = "https://gitlab.com/api/v4/groups/" + group + "/projects";

            URL obj = null;
            try {
                obj = new URL(url);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            }

            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection) obj.openConnection();
            } catch (IOException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                con.setRequestMethod("GET");
            } catch (ProtocolException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            }
            //add request header
            //add request header

            con.setRequestProperty("Authorization", "Bearer glpat-i61ZKHVbXfDfNNkT86z-");

//            if (con.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + con.getResponseCode());
//            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (con.getInputStream())));

            String o;
            String output = null;
            while ((o = br.readLine()) != null) {
                o = o.replaceAll("\\},\\{", "}\n{");
                output = o.substring(1, o.length() - 1);

            }
            br.close();

            JSONObject json = new JSONObject(output);
            return json.getInt("id") + "";

        } catch (IOException ex) {
            Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            return null;
        }
        return null;
    }

    public static List<Milestone> getMilestones(String Class) {
        String group = getGroupId(Class);
        if (group != "") {
            List<Milestone> list = new ArrayList<>();
            try {
                String url = "https://gitlab.com/api/v4/groups/" + getGroupId(Class) + "/milestones";

                URL obj = null;
                try {
                    obj = new URL(url);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
                }

                HttpURLConnection con = null;
                try {
                    con = (HttpURLConnection) obj.openConnection();
                } catch (IOException ex) {
                    Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    con.setRequestMethod("GET");
                } catch (ProtocolException ex) {
                    Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
                }
                //add request header
                //add request header

                con.setRequestProperty("Authorization", "Bearer glpat-i61ZKHVbXfDfNNkT86z-");

//            if (con.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + con.getResponseCode());
//            }
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (con.getInputStream())));

                String o;
                String[] output = null;
                while ((o = br.readLine()) != null) {
                    o = o.replaceAll("\\},\\{", "}\n{");
                    o = o.substring(1, o.length() - 1);
                    output = o.split("\n");
                }
                br.close();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (String out : output) {
                    JSONObject json = new JSONObject(out);
                    Milestone m = new Milestone();
                    m.setMilestoneName(json.getString("title"));
                    Date from_date = sdf.parse(json.getString("start_date"));
                    Date to_date = sdf.parse(json.getString("due_date"));
                    m.setFromDate(new java.sql.Date(from_date.getTime()));
                    m.setToDate(new java.sql.Date(to_date.getTime()));
                    m.setStatus(json.getBoolean("expired") == true ? 2 : 1);
                    list.add(m);
                }

            } catch (IOException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                return null;
            }
            return list;
        }
        return null;
    }

    public static void UploadMilestones(String Class, Milestone m) {
        String c = getGroupId(Class);
        try {
            String urlParameters = "id=" + c + "&title=" + m.getMilestoneName()
                    + "&due_date=" + m.getToDate() + "&start_date=" + m.getFromDate();
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            String request = "https://gitlab.com/api/v4/groups/" + c + "/milestones";
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer glpat-i61ZKHVbXfDfNNkT86z-");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);

                if (conn.getResponseCode() != 201) {
                    System.out.println("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }
        } catch (MalformedURLException ex) {
            Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void getIssuesByTeam(String Class, String team, int teamId, int assigneeId) {
        String group = getSubGroupId(Class, team);
        if (group != "") {
            List<Issue> list = new ArrayList<>();
            try {
                String url = "https://gitlab.com/api/v4/groups/" + group + "/issues";

                URL obj = null;
                try {
                    obj = new URL(url);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
                }

                HttpURLConnection con = null;
                try {
                    con = (HttpURLConnection) obj.openConnection();
                } catch (IOException ex) {
                    Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    con.setRequestMethod("GET");
                } catch (ProtocolException ex) {
                    Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
                }
                //add request header
                //add request header

                con.setRequestProperty("Authorization", "Bearer glpat-i61ZKHVbXfDfNNkT86z-");

                if (con.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + con.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (con.getInputStream())));

                String o;
                String[] output = null;
                while ((o = br.readLine()) != null) {
                    o = o.replaceAll("\\},\\{", "}\n{");
                    o = o.substring(1, o.length() - 1);
                    output = o.split("\n");
                }
                br.close();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (String out : output) {
                    JSONObject json = new JSONObject(out);
                    IssueDB idb = new IssueDB();
                    String title = json.getString("title");
                    if (idb.CheckIssueTitle(title, teamId)) {
                        int gitlabId = json.getInt("id");
                        String gitlabUrl = json.getString("web_url");
                        String description = json.getString("description");
                        String[] create = json.getString("created_at").split("T");

                        Date create_at = sdf.parse(create[0]);
                        Date dueDate = sdf.parse(json.getString("due_date"));
                        idb.sync(assigneeId, gitlabId, gitlabUrl, new java.sql.Date(create_at.getTime()), new java.sql.Date(dueDate.getTime()), teamId, description, title, 1);
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);

            } catch (ParseException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void UploadIssue(String Class, String team, String title, String description, String due, String labels, int issueId) {
        {
            try {
                String project = getProjectId(getSubGroupId(Class, team));
                String urlParameters = "id=" + project + "&title=" + title
                        + "&description=" + description + "&due_date=" + due + "&labels=" + labels;
                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                int postDataLength = postData.length;
                String request = "https://gitlab.com/api/v4/projects/" + project + "/issues";
                URL url = new URL(request);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "Bearer glpat-i61ZKHVbXfDfNNkT86z-");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                conn.setUseCaches(false);
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.write(postData);

                if (conn.getResponseCode() != 201) {
                    System.out.println("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String o;
                while ((o = br.readLine()) != null) {
                    JSONObject json = new JSONObject(o);
                    int gitlabId = json.getInt("id");
                    String gitlabUrl = json.getString("web_url");
                    new IssueDB().updateGitlab(issueId, gitlabId, gitlabUrl);
                }
                br.close();

            } catch (MalformedURLException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GitlabSync.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

//    public static void main(String[] args) {
//////        List<Milestone> list = getMilestones("SE1610");
//////        for (Milestone m : list) {
//////            System.out.println(m.getStatus());
//////        }
////////        List<Milestone> list = new MilestoneDB().Search("32", "1", "", "", "", 1, 10);
//////        Milestone m = new MilestoneDB()
//////        
//             List<Milestone> spm = new MilestoneDB().Search(32+"", 2+"", "", "", "1", 1, 10);
//             for(Milestone m : spm){
//                UploadMilestones("SE1549",m);
//            }
//
//////        System.out.println(getSubGroupId("SE1610", "G3"));
//////            Team t = new TeamDB().getTeam("5");
//////             GitlabSync.getIssuesByTeam(t.getClassId().getClassCode(), t.getTeamCode(), t.getTeamId(), 16);
////     //   UploadIssue("SE1549", "G3", "up", "test", "2022-07-31", "to do", 19);
//    }
}
