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
public class LocEval {
    private int id;
    private Date time;
    private String note;
    private SubjectSetting complexity, quality;
    private Tracking tracking;
    private boolean status;

    public LocEval() {
    }

    public LocEval(int id, Date time, String note, SubjectSetting complexity, SubjectSetting quality, Tracking tracking, boolean status) {
        this.id = id;
        this.time = time;
        this.note = note;
        this.complexity = complexity;
        this.quality = quality;
        this.tracking = tracking;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public SubjectSetting getComplexity() {
        return complexity;
    }

    public void setComplexity(SubjectSetting complexity) {
        this.complexity = complexity;
    }

    public SubjectSetting getQuality() {
        return quality;
    }

    public void setQuality(SubjectSetting quality) {
        this.quality = quality;
    }

    public Tracking getTracking() {
        return tracking;
    }

    public void setTracking(Tracking tracking) {
        this.tracking = tracking;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
