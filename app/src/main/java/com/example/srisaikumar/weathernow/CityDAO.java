package com.example.srisaikumar.weathernow;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by srisaikumar on 6/30/2016.
 */
public class CityDAO {
    private SQLiteDatabase db;
    public CityDAO(SQLiteDatabase db){
        this.db=db;
    }

    public long save(Cities cities){
        ContentValues contentValues=new ContentValues();
        contentValues.put(CityTable.CITY_NAME,cities.getCityName());
        contentValues.put(CityTable.STATE_NAME,cities.getState());
        return db.insert(CityTable.cityTable,null,contentValues);
    }

    public boolean update(Cities cities){
        ContentValues contentValues=new ContentValues();
        contentValues.put(CityTable.CITY_NAME,cities.getCityName());
        contentValues.put(CityTable.STATE_NAME,cities.getState());
        return db.update(CityTable.cityTable,contentValues,CityTable.CITY_KEY+"="+cities.getCityKey(),null)>0;
    }

    public boolean delete(Cities cities){
        return db.delete(CityTable.cityTable,CityTable.CITY_KEY+"="+cities.getCityKey(),null)>0;
    }

    public Cities get(int id){
        Cities cities=null;
        Cursor c=db.query(CityTable.cityTable,new String[]{CityTable.CITY_KEY,CityTable.CITY_NAME,CityTable.STATE_NAME},
                    CityTable.CITY_KEY+"="+id,null,null,null,null,null);
        if(c!=null){
            c.moveToFirst();
            cities=this.buildFromCursor(c);
        }
        return cities;
    }

    public ArrayList<Cities> getAll(){
        ArrayList<Cities>citiesList=new ArrayList<>();
        Cursor c=db.query(CityTable.cityTable,new String[]{CityTable.CITY_KEY,CityTable.CITY_NAME,CityTable.STATE_NAME},
                null,null,null,null,null);
        if(c!=null){
            c.moveToFirst();
            do{
                Cities cities=this.buildFromCursor(c);
                if(cities!=null)
                    citiesList.add(cities);
            }while (c.moveToNext());
        }
        return citiesList;
    }

    public Cities buildFromCursor(Cursor c){
        Cities cities=null;
        if(c!=null){
            cities=new Cities();
            cities.setCityKey(c.getInt(0));
            cities.setCityName(c.getString(1));
            cities.setState(c.getString(2));
        }
        return cities;
    }
}
