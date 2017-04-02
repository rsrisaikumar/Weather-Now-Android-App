package com.example.srisaikumar.weathernow;

import java.io.Serializable;

/**
 * Created by srisaikumar on 6/29/2016.
 */
public class Cities implements Serializable {
    private int cityKey;
    private String cityName;
    private String state;

    public Cities(){}

    public Cities(int cityKey, String cityName, String state){
        this.cityKey=cityKey;
        this.cityName=cityName;
        this.state=state;
    }

    public void setCityKey(int cityKey) {
        this.cityKey = cityKey;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCityKey() {
        return cityKey;
    }

    public String getCityName() {
        return cityName;
    }

    public String getState() {
        return state;
    }
}
