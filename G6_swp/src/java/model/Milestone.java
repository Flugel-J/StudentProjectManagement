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
public class Milestone {
    private int milestoneId;
    private String milestoneName;
    private Iteration iteration;
    private Class classId;
    private Date fromDate, toDate;
    private int status;// 1- open, 2-close, 3-cancel

    public Milestone() {
    }

    public Milestone(int milestoneId, String milestoneName, Iteration iteration, Class classId, Date fromDate, Date toDate, int status) {
        this.milestoneId = milestoneId;
        this.milestoneName = milestoneName;
        this.iteration = iteration;
        this.classId = classId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public Iteration getIteration() {
        return iteration;
    }

    public void setIteration(Iteration iteration) {
        this.iteration = iteration;
    }

    public Class getClassId() {
        return classId;
    }

    public void setClassId(Class classId) {
        this.classId = classId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    

}