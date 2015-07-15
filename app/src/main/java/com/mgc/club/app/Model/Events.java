package com.mgc.club.app.Model;

import java.io.Serializable;

/**
 * Created by savva on 07.07.2015.
 */
public class Events implements Serializable {
    private int id;
    private String name;
    private String text;
    private String start;
    private String finish;
    private String eventcover;


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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getEventcover() {
        return eventcover;
    }

    public void setEventcover(String eventcover) {
        this.eventcover = eventcover;
    }
}
