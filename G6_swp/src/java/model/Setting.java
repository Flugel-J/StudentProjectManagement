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
public class Setting {
    private int settingId;
    private int typeId;
    private String settingTitle;
    private String settingValue;
    private int displayOrder;
    private String note;
    private boolean status;

    public Setting() {
    }

    public Setting(int settingId, int typeId, String settingTitle, String settingValue, int displayOrder, String note, boolean status) {
        this.settingId = settingId;
        this.typeId = typeId;
        this.settingTitle = settingTitle;
        this.settingValue = settingValue;
        this.displayOrder = displayOrder;
        this.note = note;
        this.status = status;
    }

    public int getSettingId() {
        return settingId;
    }

    public void setSettingId(int settingId) {
        this.settingId = settingId;
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
