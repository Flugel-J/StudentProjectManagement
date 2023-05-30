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
public class Iteration {
    private int iterationId, subjectId;
    private String iterName;
    private int duration; // day
    private boolean status;
    private double evalWeight;
    private boolean isOngoing;

    public Iteration() {
    }

    public Iteration(int iterationId, int subjectId, String iterName, int duration, boolean status, double evalWeight, boolean isOngoing) {
        this.iterationId = iterationId;
        this.subjectId = subjectId;
        this.iterName = iterName;
        this.duration = duration;
        this.status = status;
        this.evalWeight = evalWeight;
        this.isOngoing = isOngoing;
    }
    

    public Iteration(int iterationId, int subjectId, String iterName, int duration, boolean status) {
        this.iterationId = iterationId;
        this.subjectId = subjectId;
        this.iterName = iterName;
        this.duration = duration;
        this.status = status;
    }

    public Iteration(int subjectId, String iterName, int duration, boolean status) {
        this.subjectId = subjectId;
        this.iterName = iterName;
        this.duration = duration;
        this.status = status;
    }
    
    

    public int getIterationId() {
        return iterationId;
    }

    public void setIterationId(int iterationId) {
        this.iterationId = iterationId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getIterName() {
        return iterName;
    }

    public void setIterName(String iterName) {
        this.iterName = iterName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getEvalWeight() {
        return evalWeight;
    }

    public void setEvalWeight(double evalWeight) {
        this.evalWeight = evalWeight;
    }

    public boolean isIsOngoing() {
        return isOngoing;
    }

    public void setIsOngoing(boolean isOngoing) {
        this.isOngoing = isOngoing;
    }
    

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}
