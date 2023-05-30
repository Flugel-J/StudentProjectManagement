/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author KHANHHERE
 */
public class Issue {
    private int id;
    private User user; //assignee
    private Setting setting; //issue type
    private String title, descript, gitlabId, gitlabUrl;
    private Date createAt, dueDate;
    private Team team;
    private Milestone milestone;
    private String functionIds, label;
    private int status;

    public Issue() {
    }

    public Issue(int id, User user, Setting setting, String title, String descript, String gitlabId, String gitlabUrl, Date createAt, Date dueDate, Team team, Milestone milestone, String functionIds, String label, int status) {
        this.id = id;
        this.user = user;
        this.setting = setting;
        this.title = title;
        this.descript = descript;
        this.gitlabId = gitlabId;
        this.gitlabUrl = gitlabUrl;
        this.createAt = createAt;
        this.dueDate = dueDate;
        this.team = team;
        this.milestone = milestone;
        this.functionIds = functionIds;
        this.label = label;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getGitlabId() {
        return gitlabId;
    }

    public void setGitlabId(String gitlabId) {
        this.gitlabId = gitlabId;
    }

    public String getGitlabUrl() {
        return gitlabUrl;
    }

    public void setGitlabUrl(String gitlabUrl) {
        this.gitlabUrl = gitlabUrl;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public String getFunctionIds() {
        return functionIds;
    }

    public void setFunctionIds(String functionIds) {
        this.functionIds = functionIds;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
