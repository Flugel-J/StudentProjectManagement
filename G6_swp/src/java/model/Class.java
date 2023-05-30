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
public class Class {
    
    private int classId;
    private String classCode;
    private User trainer;
    private Subject subject;
    private int classYear;
    private int term; //1 - SP ; 2- SU ; 3- FA
    private boolean block5Class;
    private int status; // 1- active, 2-close, 3-cancel

    public Class() {
    }
    public Class(int classId, String classCode) {
        this.classId = classId;
        this.classCode = classCode;
    }

    public Class(int classId, String classCode, User trainer, Subject subject, int classYear, int term, boolean block5Class, int status) {
        this.classId = classId;
        this.classCode = classCode;
        this.trainer = trainer;
        this.subject = subject;
        this.classYear = classYear;
        this.term = term;
        this.block5Class = block5Class;
        this.status = status;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getClassYear() {
        return classYear;
    }

    public void setClassYear(int classYear) {
        this.classYear = classYear;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public boolean isBlock5Class() {
        return block5Class;
    }

    public void setBlock5Class(boolean block5Class) {
        this.block5Class = block5Class;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
