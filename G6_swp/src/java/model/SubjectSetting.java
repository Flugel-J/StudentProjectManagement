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
public class SubjectSetting {
    private int settingId, subjectId, typeId;
    private String settingTitle;
    private String settingValue;
    private int displayOrder;
    private boolean status;

    public SubjectSetting() {
    }

    public SubjectSetting(int settingId, int subjectId, int typeId, String settingTitle, String settingValue, int displayOrder, boolean status) {
        this.settingId = settingId;
        this.subjectId = subjectId;
        this.typeId = typeId;
        this.settingTitle = settingTitle;
        this.settingValue = settingValue;
        this.displayOrder = displayOrder;
        this.status = status;
    }
    

    public int getSettingId() {
        return settingId;
    }

    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getSettingTitle() {
        return settingTitle;
    }

    public void setSettingTitle(String settingTitle) {
        this.settingTitle = settingTitle;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
    
}
