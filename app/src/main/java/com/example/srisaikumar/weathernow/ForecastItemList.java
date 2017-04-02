package com.example.srisaikumar.weathernow;

import java.util.ArrayList;

/**
 * Created by srisaikumar on 6/25/2016.
 */
public class ForecastItemList {
    public ArrayList<ForecastItem> forecastItems;
    private static ForecastItemList forecastItemList;
    private ForecastItemList(){
        forecastItems=new ArrayList<>();
    }

    public static ForecastItemList getInstance(){
        if(forecastItemList==null){
            forecastItemList=new ForecastItemList();
        }
        return forecastItemList;
    }
}
