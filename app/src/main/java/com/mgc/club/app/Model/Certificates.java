package com.mgc.club.app.Model;

import com.google.gson.internal.LinkedTreeMap;

import java.io.Serializable;

/**
 * Created by savva on 29.06.2015.
 */
public class Certificates implements Serializable {

    public Certificates(int id, String name, int bonus_price, String cover_url, String url) {
        this.id = id;
        this.name = name;
        this.bonus_price = bonus_price;
        this.cover_url = cover_url;
        this.url = url;
    }

    public Certificates() {

    }

    public Certificates(LinkedTreeMap linkedTreeMap) {
        this.id = ((Double) linkedTreeMap.get("id")).intValue();
        this.name = linkedTreeMap.get("name").toString();
        this.bonus_price = ((Double) linkedTreeMap.get("bonus_price")).intValue();
        this.cover_url = linkedTreeMap.get("cover_url").toString();
        this.url = linkedTreeMap.get("url").toString();
    }


    public int id;

    public String name;

    public int bonus_price;

    public String cover_url;

    public String url;

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

    public int getBonus_price() {
        return bonus_price;
    }

    public void setBonus_price(int bonus_price) {
        this.bonus_price = bonus_price;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
