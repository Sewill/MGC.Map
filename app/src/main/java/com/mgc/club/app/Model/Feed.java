package com.mgc.club.app.Model;

import java.io.Serializable;

/**
 * Created by savva on 07.07.2015.
 */
public class Feed implements Serializable {
    private int id;
    private String name;
    private String cover;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
