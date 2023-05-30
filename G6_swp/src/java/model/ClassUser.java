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
public class ClassUser {
    private Class classId;
    private Team team;
    private User user;
    private boolean teamLeader;
    private Date dropoutDate;
    private String userNotes;
    private double ongoingEval, finalPresEval, finalTopicEval;
    private boolean status;

    public ClassUser() {
    }

    public ClassUser(Class classId, Team team, User user, boolean teamLeader, Date dropoutDate, String userNotes, double ongoingEval, double finalPresEval, double finalTopicEval, boolean status) {
        this.classId = classId;
        this.team = team;
        this.user = user;
        this.teamLeader = teamLeader;
        this.dropoutDate = dropoutDate;
        this.userNotes = userNotes;
        this.ongoingEval = ongoingEval;
        this.finalPresEval = finalPresEval;
        this.finalTopicEval = finalTopicEval;
        this.status = status;
    }

    public Class getClassId() {
        return classId;
    }

    public void setClassId(Class classId) {
        this.classId = classId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(boolean teamLeader) {
        this.teamLeader = teamLeader;
    }

    public Date getDropoutDate() {
        return dropoutDate;
    }

    public void setDropoutDate(Date dropoutDate) {
        this.dropoutDate = dropoutDate;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

    public double getOngoingEval() {
        return ongoingEval;
    }

    public void setOngoingEval(double ongoingEval) {
        this.ongoingEval = ongoingEval;
    }

    public double getFinalPresEval() {
        return finalPresEval;
    }

    public void setFinalPresEval(double finalPresEval) {
        this.finalPresEval = finalPresEval;
    }

    public double getFinalTopicEval() {
        return finalTopicEval;
    }

    public void setFinalTopicEval(double finalTopicEval) {
        this.finalTopicEval = finalTopicEval;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    

    
}
