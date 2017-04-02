package com.example.srisaikumar.weathernow;

import java.io.Serializable;

/**
 * Created by srisaikumar on 6/17/2016.
 */
public class WeatherItem implements Serializable{
    private String time;
    private String temperature;
    private String dewPoint;
    private String clouds;
    private String iconUrl;
    private String windSpeed;
    private String windDirection;
    private String climateType;
    private String humidity;
    private String feelsLike;
    private String maximumTemp;
    private String minimumTemp;
    private String pressure;


    public WeatherItem(){
        this.time="";
        this.temperature="";
        this.dewPoint="";
        this.clouds="";
        this.iconUrl="";
        this.windSpeed="";
        this.windDirection="";
        this.climateType="";
        this.humidity="";
        this.feelsLike="";
        this.maximumTemp="";
        this.minimumTemp="";
        this.pressure="";
    }

    public WeatherItem(String time, String temperature, String dewPoint, String clouds, String iconUrl,
                       String windSpeed, String windDirection, String climateType, String humidity,
                       String feelsLike, String maximumTemp, String minimumTemp, String pressure){
        this.time=time;
        this.temperature=temperature;
        this.dewPoint=dewPoint;
        this.clouds=clouds;
        this.iconUrl=iconUrl;
        this.windSpeed=windSpeed;
        this.windDirection=windDirection;
        this.climateType=climateType;
        this.humidity=humidity;
        this.feelsLike=feelsLike;
        this.maximumTemp=maximumTemp;
        this.minimumTemp=minimumTemp;
        this.pressure=pressure;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setDewPoint(String dewPoint) {
        this.dewPoint = dewPoint;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public void setClimateType(String climateType) {
        this.climateType = climateType;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public void setMaximumTemp(String maximumTemp) {
        this.maximumTemp = maximumTemp;
    }

    public void setMinimumTemp(String minimumTemp) {
        this.minimumTemp = minimumTemp;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTime() {
        return time;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public String getClouds() {
        return clouds;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getClimateType() {
        return climateType;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getMaximumTemp() {
        return maximumTemp;
    }

    public String getMinimumTemp() {
        return minimumTemp;
    }

    public String getPressure() {
        return pressure;
    }
}
