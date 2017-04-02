package com.example.srisaikumar.weathernow;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by srisaikumar on 6/30/2016.
 */
public class CityDataManager {
    Context context;
    CityDBHandler cityDBHandler;
    SQLiteDatabase db;
    CityDAO cityDAO;

    public CityDataManager(Context context){
        this.context=context;
        cityDBHandler=new CityDBHandler(context);
        db=cityDBHandler.getWritableDatabase();
        cityDAO=new CityDAO(db);
    }

    public void close(){
        db.close();
    }

    public long saveCity(Cities cities){
        return cityDAO.save(cities);
    }

    public boolean updateCity(Cities cities){
        return cityDAO.update(cities);
    }

    public boolean deleteCity(Cities cities){
        return cityDAO.delete(cities);
    }

    public Cities getCities(int id){
        return cityDAO.get(id);
    }

    public ArrayList<Cities> getAllCities(){
        return cityDAO.getAll();
    }
}
