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
public class TeamEval {
    private int id;
    private EvalCriteria evalCriteria;
    private Team team;
    private double grade;
    private String note;
    private boolean status;

    public TeamEval() {
    }

    public TeamEval(int id, EvalCriteria evalCriteria, Team team, double grade, String note, boolean status) {
        this.id = id;
        this.evalCriteria = evalCriteria;
        this.team = team;
        this.grade = grade;
        this.note = note;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EvalCriteria getEvalCriteria() {
        return evalCriteria;
    }

    public void setEvalCriteria(EvalCriteria evalCriteria) {
        this.evalCriteria = evalCriteria;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
       
}
