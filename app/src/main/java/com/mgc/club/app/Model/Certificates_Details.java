package com.mgc.club.app.Model;

import java.io.Serializable;

/**
 * Created by savva on 01.07.2015.
 */
public class Certificates_Details extends Certificates implements Serializable {
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
