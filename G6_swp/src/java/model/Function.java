/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author KHANHHERE
 */
public class Function {
    private int id;
    private Team team;
    private String functionName;
    private Feature feature;
    private String accessRoles, description;
    private SubjectSetting subjectSetting; // for complexity
    private User user;
    private int priority, status;

    public Function() {
    }

    public Function(int id, Team team, String functionName, Feature feature, String accessRoles, String description, SubjectSetting subjectSetting, User user, int priority, int status) {
        this.id = id;
        this.team = team;
        this.functionName = functionName;
        this.feature = feature;
        this.accessRoles = accessRoles;
        this.description = description;
        this.subjectSetting = subjectSetting;
        this.user = user;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public String getAccessRoles() {
        return accessRoles;
    }

    public void setAccessRoles(String accessRoles) {
        this.accessRoles = accessRoles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SubjectSetting getSubjectSetting() {
        return subjectSetting;
    }

    public void setSubjectSetting(SubjectSetting subjectSetting) {
        this.subjectSetting = subjectSetting;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
}
