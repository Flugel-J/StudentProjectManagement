/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author KHANHHERE
 */
public class UserEval {
    private IterEval ie;
    private List<MemEval> me;

    public UserEval() {
    }

    public UserEval(IterEval ie) {
        this.ie = ie;
    }

    public UserEval(IterEval ie, List<MemEval> me) {
        this.ie = ie;
        this.me = me;
    }
    

    public IterEval getIe() {
        return ie;
    }

    public void setIe(IterEval ie) {
        this.ie = ie;
    }

    public List<MemEval> getMe() {
        return me;
    }

    public void setMe(List<MemEval> me) {
        this.me = me;
    }
    
}
