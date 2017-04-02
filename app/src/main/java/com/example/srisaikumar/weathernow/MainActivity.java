package com.example.srisaikumar.weathernow;

import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    static CityDataManager cityDataManager;
    String city_name, state_name;
    static int set=0;
    ArrayList<Cities> citiesArrayList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setIcon(R.drawable.weather_logo);
        }catch (Exception e){}

        cityDataManager=new CityDataManager(this);

        Intent intent=getIntent();
        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setId(R.id.main_lin);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        setContentView(linearLayout);






        if(set==0) {
            city_name = intent.getStringExtra("CITY");
            state_name = intent.getStringExtra("STATE");
        }
        if(city_name!=null&&state_name!=null) {
            int flag=0;
            if(SampleWeatherItemsList.getInstance().sampleWeatherItems.size()>0){
                for(int i=0;i<SampleWeatherItemsList.getInstance().sampleWeatherItems.size();i++){
                    if(SampleWeatherItemsList.getInstance().sampleWeatherItems.get(i).title.equalsIgnoreCase(city_name+", "+state_name)){
                        flag=1;
                        break;
                    }
                }
            }
            if(flag==0) {
                if(linearLayout!=null)
                    linearLayout.removeAllViews();
                ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
                linearLayout.addView(progressBar);
                new GetWeatherDataTask().execute(city_name, state_name);

            }
            else{
                displayView();
            }
        }
        else if(SampleWeatherItemsList.getInstance().sampleWeatherItems.size()>0)
            displayView();
        else{
            TextView text_def=new TextView(this);
            text_def.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
            text_def.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
            text_def.setText(R.string.text_def);
            linearLayout.addView(text_def);
        }
    }

    public void displayView(){
        final LinearLayout linearLayout=(LinearLayout)findViewById(R.id.main_lin);
        if(linearLayout!=null)
            linearLayout.removeAllViews();

        SampleWeatherItemsList sampleWeatherItemsList=SampleWeatherItemsList.getInstance();
        if(sampleWeatherItemsList.sampleWeatherItems.size()==0){
            TextView text_def=new TextView(this);
            text_def.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
            text_def.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
            text_def.setText(R.string.text_def);
            linearLayout.addView(text_def);
            return;
        }
        linearLayout.setGravity(Gravity.NO_GRAVITY);
        ListView listView=new ListView(this);
        Iterator iterator=sampleWeatherItemsList.sampleWeatherItems.iterator();
        SampleWeatherItems[] sampleWeatherItems=new SampleWeatherItems[sampleWeatherItemsList.sampleWeatherItems.size()];
        int i=0;
        while(iterator.hasNext()){
            SampleWeatherItems sampleWeatherItem= (SampleWeatherItems) iterator.next();
            sampleWeatherItems[i]=new SampleWeatherItems(sampleWeatherItem.title,sampleWeatherItem.temperature);
            i++;
        }

        listView.setAdapter(new MainDisplayAdapter(this,R.layout.list_view,sampleWeatherItems));
        linearLayout.addView(listView);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SampleWeatherItemsList.getInstance().sampleWeatherItems.remove(position);
                displayView();
                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this, CityData.class);
                intent.putExtra("CITY_STATE",SampleWeatherItemsList.getInstance().sampleWeatherItems.get(position).title);
                startActivity(intent);
            }
        });
    }


    private class GetWeatherDataTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            WeatherItemList.getInstance().weatherItems.clear();
            String api_key="0c854303c9d56839";
            String city=params[0];
            String state=params[1];
            Date date=new Date();
            String m;
            int h;
            if(date.getHours()>12) {
                m = "PM";
                h=date.getHours()-12;
            }
            else if(date.getHours()==12){
                m="PM";
                h=date.getHours();
            }
            else if(date.getHours()==0){
                m="AM";
                h=12;
            }
            else {
                m = "AM";
                h=date.getHours();
            }
            String d=h+":00 "+m;
            String tem="-";
            try {
                WeatherItem tempWeatherItem=new WeatherItem();
                URL url=new URL("http://api.wunderground.com/api/"+api_key+"/hourly/q/"+state+"/"+city+".xml");
                URLConnection connection= url.openConnection();
                InputStream inputStream=connection.getInputStream();
                XmlPullParserFactory xmlPullParserFactory=XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser=xmlPullParserFactory.newPullParser();
                xmlPullParser.setInput(inputStream,null);
                int event=xmlPullParser.getEventType();
                int timeFlag=0, tempFlag=0;
                while (event!=xmlPullParser.END_DOCUMENT){
                    String name=xmlPullParser.getName();
                    switch (event){
                        case XmlPullParser.START_TAG:
                            //time
                            if(name.equals("FCTTIME")){
                                timeFlag=1;
                            }
                            else if(timeFlag==1){
                                if(name.equals("civil")){
                                    tempWeatherItem.setTime(xmlPullParser.nextText());
                                    timeFlag=0;
                                }
                            }
                            //temperature
                            else if(name.equals("temp")){
                                tempFlag=1;
                            }
                            else if(tempFlag==1){
                                if(name.equals("english")){
                                    tempWeatherItem.setTemperature(xmlPullParser.nextText()+(char)0x00B0+"F");
                                    tempFlag=0;
                                    if(tempWeatherItem.getTime().equals(d)){
                                        tem=tempWeatherItem.getTemperature();
                                    }
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(name.equals("forecast")){
                                tempWeatherItem=new WeatherItem();
                            }
                            break;

                    }
                    event=xmlPullParser.next();
                }
                int flag=0;
                for(int i=0;i<SampleWeatherItemsList.getInstance().sampleWeatherItems.size();i++){
                    if(SampleWeatherItemsList.getInstance().sampleWeatherItems.get(i).title.equalsIgnoreCase(city+", "+state)){
                        flag=1;
                        break;
                    }
                }
                if(flag==0) {
                    SampleWeatherItemsList sampleWeatherItemsList = SampleWeatherItemsList.getInstance();
                    sampleWeatherItemsList.sampleWeatherItems.add(new SampleWeatherItems(city + ", " + state, tem));
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            displayView();
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_addCity:
                set=0;
                Intent intent=new Intent(this, AddCity.class);
                startActivity(intent);
                return true;
            case R.id.action_clearSavedCity:
                SampleWeatherItemsList.getInstance().sampleWeatherItems.clear();
                set=1;
                finish();
                startActivity(getIntent());
                break;
            case R.id.action_viewNotes:
                Intent intent3=new Intent(this, Notes.class);
                startActivity(intent3);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_overflow,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event){
        if(keycode==event.KEYCODE_BACK){
            finish();
            moveTaskToBack(true);
            System.exit(0);
            return true;
        }
        return false;
    }


    private class SampleWeatherItems{
        String title;
        String temperature;
        SampleWeatherItems() {
            this.title="";
            this.temperature="";
        }
        SampleWeatherItems(String title, String temperature){
            this.title=title;
            this.temperature=temperature;
        }
    }

    private class MainDisplayAdapter extends ArrayAdapter<SampleWeatherItems>{
        int layoutResourceId;
        Context context;
        SampleWeatherItems[] sampleWeatherItems=null;
        public MainDisplayAdapter(Context context, int resource, SampleWeatherItems[] objects) {
            super(context, resource, objects);
            this.context=context;
            this.layoutResourceId=resource;
            this.sampleWeatherItems=objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View row=convertView;
            WeatherItemsHolder weatherItemsHolder=null;
            if(row==null){
                LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
                row=layoutInflater.inflate(layoutResourceId,parent,false);
                weatherItemsHolder=new WeatherItemsHolder();
                weatherItemsHolder.text1=(TextView)row.findViewById(R.id.text_1);
                weatherItemsHolder.text2=(TextView)row.findViewById(R.id.text_2);
                row.setTag(weatherItemsHolder);
            }
            else{
                weatherItemsHolder= (WeatherItemsHolder) row.getTag();
            }

            weatherItemsHolder.text1.setText(sampleWeatherItems[position].title);
            weatherItemsHolder.text2.setText(sampleWeatherItems[position].temperature);
            return row;
        }

         class WeatherItemsHolder{
            TextView text1;
            TextView text2;
        }
    }
    public static class SampleWeatherItemsList{
        public ArrayList<SampleWeatherItems>sampleWeatherItems;
        public SampleWeatherItemsList(){
            sampleWeatherItems=new ArrayList<>();
        }
        private static SampleWeatherItemsList sampleWeatherItemsList;
        public static SampleWeatherItemsList getInstance(){
            if(sampleWeatherItemsList==null)
                sampleWeatherItemsList=new SampleWeatherItemsList();
            return sampleWeatherItemsList;
        }

    }


}
