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
public class EvalCriteria {
    
    private int criteriaId;
    private Iteration iteration;
    private String evalTitle;
    private double evalWeight;
    private boolean teamEval; // true - team ; false - member
    private int criteriaOrder;
    private int maxLoc;
    private boolean status;

    public EvalCriteria() {
    }

    public EvalCriteria(int criteriaId, Iteration iteration, String evalTitle, double evalWeight, boolean teamEval, int criteriaOrder, int maxLoc, boolean status) {
        this.criteriaId = criteriaId;
        this.iteration = iteration;
        this.evalTitle = evalTitle;
        this.evalWeight = evalWeight;
        this.teamEval = teamEval;
        this.criteriaOrder = criteriaOrder;
        this.maxLoc = maxLoc;
        this.status = status;
    }

    public int getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(int criteriaId) {
        this.criteriaId = criteriaId;
    }

    public Iteration getIteration() {
        return iteration;
    }

    public void setIteration(Iteration iteration) {
        this.iteration = iteration;
    }

    public String getEvalTitle() {
        return evalTitle;
    }

    public void setEvalTitle(String evalTitle) {
        this.evalTitle = evalTitle;
    }

    public double getEvalWeight() {
        return evalWeight;
    }

    public void setEvalWeight(double evalWeight) {
        this.evalWeight = evalWeight;
    }

    public boolean isTeamEval() {
        return teamEval;
    }

    public void setTeamEval(boolean teamEval) {
        this.teamEval = teamEval;
    }

    public int getCriteriaOrder() {
        return criteriaOrder;
    }

    public void setCriteriaOrder(int criteriaOrder) {
        this.criteriaOrder = criteriaOrder;
    }

    public int getMaxLoc() {
        return maxLoc;
    }

    public void setMaxLoc(int maxLoc) {
        this.maxLoc = maxLoc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
}
