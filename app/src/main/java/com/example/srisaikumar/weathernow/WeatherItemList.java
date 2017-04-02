package com.example.srisaikumar.weathernow;

import com.example.srisaikumar.weathernow.WeatherItem;

import java.util.ArrayList;

/**
 * Created by srisaikumar on 6/17/2016.
 */
public class WeatherItemList {
    public ArrayList<WeatherItem> weatherItems;
    private static WeatherItemList weatherItemList;

    public WeatherItemList(){
        weatherItems=new ArrayList<>();
    }

    public static WeatherItemList getInstance(){
        if(weatherItemList==null){
            weatherItemList=new WeatherItemList();
        }
        return weatherItemList;
    }


}
