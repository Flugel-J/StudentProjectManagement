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
public class MemEval {
    private int id;
    private IterEval iterEval; //eval_id
    private EvalCriteria evalCriteria; // criteria_id
    private int convertedLoc; // display converted loc
    private double grade;
    private String note;
    private boolean status;

    public MemEval() {
    }

    public MemEval(int id, IterEval iterEval, EvalCriteria evalCriteria, int convertedLoc, double grade, String note, boolean status) {
        this.id = id;
        this.iterEval = iterEval;
        this.evalCriteria = evalCriteria;
        this.convertedLoc = convertedLoc;
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

    public IterEval getIterEval() {
        return iterEval;
    }

    public void setIterEval(IterEval iterEval) {
        this.iterEval = iterEval;
    }

    public EvalCriteria getEvalCriteria() {
        return evalCriteria;
    }

    public void setEvalCriteria(EvalCriteria evalCriteria) {
        this.evalCriteria = evalCriteria;
    }

    public int getConvertedLoc() {
        return convertedLoc;
    }

    public void setConvertedLoc(int convertedLoc) {
        this.convertedLoc = convertedLoc;
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
