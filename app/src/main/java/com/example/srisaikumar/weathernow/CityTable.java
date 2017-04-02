package com.example.srisaikumar.weathernow;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by srisaikumar on 6/30/2016.
 */
public class CityTable {
    //Cities Table
    public static final String cityTable="Cities";
    //Cities Columns
    public static final String CITY_KEY="cityKey";
    public static final String CITY_NAME="cityName";
    public static final String STATE_NAME="state";

    public static void onCreate(SQLiteDatabase db){
        String CREATE_CITYDATA="CREATE TABLE "+cityTable+"("
                +CITY_KEY+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CITY_NAME+" TEXT, "+STATE_NAME+" TEXT)";
        try{
            db.execSQL(CREATE_CITYDATA);
        }catch (Exception e){}
    }

    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS "+cityTable);
        CityTable.onCreate(db);
    }


}
