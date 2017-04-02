package com.example.srisaikumar.weathernow;

import java.io.Serializable;

/**
 * Created by srisaikumar on 6/28/2016.
 */
public class NotesItem implements Serializable {
    private String location;
    private String date;
    private String note;

    public NotesItem(){
        this.location="";
        this.date="";
        this.note="";
    }

    public NotesItem(String location, String date, String note){
        this.location=location;
        this.date=date;
        this.note=note;
    }

    public void setLocation(String location){
        this.location=location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }
}
