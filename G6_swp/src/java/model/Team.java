/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dal.TeamDB;

/**
 *
 * @author KHANHHERE
 */
public class Team {

    private int teamId;
    private String teamCode;
    private Class classId;
    private String topicCode, topicName, gitlabUrl;
    private boolean status;

    public Team() {
    }

    public Team(int teamId, String teamCode, Class classId, String topicCode, String topicName, String gitlabUrl, boolean status) {
        this.teamId = teamId;
        this.teamCode = teamCode;
        this.classId = classId;
        this.topicCode = topicCode;
        this.topicName = topicName;
        this.gitlabUrl = gitlabUrl;
        this.status = status;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public Class getClassId() {
        return classId;
    }

    public void setClassId(Class classId) {
        this.classId = classId;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getGitlabUrl() {
        return gitlabUrl;
    }

    public void setGitlabUrl(String gitlabUrl) {
        this.gitlabUrl = gitlabUrl;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getClassCode() {
        TeamDB db = new TeamDB();
        return db.getClassCode(classId.getClassId());
    }

    public Team(int teamId, String topicCode) {
        this.teamId = teamId;
        this.topicCode = topicCode;
    }

    public Team(String topicCode) {
        this.topicCode = topicCode;
    }
}
