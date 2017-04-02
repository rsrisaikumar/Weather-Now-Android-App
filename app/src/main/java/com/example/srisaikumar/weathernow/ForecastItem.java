package com.example.srisaikumar.weathernow;

import java.io.Serializable;

/**
 * Created by srisaikumar on 6/25/2016.
 */
public class ForecastItem implements Serializable {
    private String date;
    private String highTemp;
    private String lowTemp;
    private String clouds;
    private String iconURL;
    private String maxWindSpeed;
    private String windDirection;
    private String avgHumidity;

    public ForecastItem(){
        this.date="";
        this.highTemp="";
        this.lowTemp="";
        this.clouds="";
        this.iconURL="";
        this.maxWindSpeed="";
        this.windDirection="";
        this.avgHumidity="";
    }

    public ForecastItem(String date, String highTemp, String lowTemp, String clouds, String iconURL,
                        String maxWindSpeed, String windDirection, String avgHumidity){
        this.date=date;
        this.highTemp=highTemp;
        this.lowTemp=lowTemp;
        this.clouds=clouds;
        this.iconURL=iconURL;
        this.maxWindSpeed=maxWindSpeed;
        this.windDirection=windDirection;
        this.avgHumidity=avgHumidity;
    }

     public void setDate(String date){
         this.date=date;
     }

    public void setHighTemp(String highTemp){
        this.highTemp=highTemp;
    }

    public void setLowTemp(String lowTemp){
        this.lowTemp=lowTemp;
    }

    public void setClouds(String clouds){
        this.clouds=clouds;
    }

    public void setIconURL(String iconURL){
        this.iconURL=iconURL;
    }

    public void setMaxWindSpeed(String maxWindSpeed){
        this.maxWindSpeed=maxWindSpeed;
    }

    public void setWindDirection(String windDirection){
        this.windDirection=windDirection;
    }

    public void setAvgHumidity(String avgHumidity){
        this.avgHumidity=avgHumidity;
    }

    public String getDate() {
        return date;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public String getClouds() {
        return clouds;
    }

    public String getIconURL() {
        return iconURL;
    }

    public String getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getAvgHumidity() {
        return avgHumidity;
    }
}
