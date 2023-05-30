/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class Feature {

    private int featureId;
    private Team teamId;
    private String featureName;
    private boolean status;

    public Feature() {
    }

    public Feature(int featureId, Team teamId, String featureName, boolean status) {
        this.featureId = featureId;
        this.teamId = teamId;
        this.featureName = featureName;
        this.status = status;
    }

    public int getFeatureId() {
        return featureId;
    }

    public void setFeatureId(int featureId) {
        this.featureId = featureId;
    }

    public Team getTeamId() {
        return teamId;
    }

    public void setTeamId(Team teamId) {
        this.teamId = teamId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Feature{" + "featureId=" + featureId + ", teamId=" + teamId + ", featureName=" + featureName + ", status=" + status + '}';
    }

}
