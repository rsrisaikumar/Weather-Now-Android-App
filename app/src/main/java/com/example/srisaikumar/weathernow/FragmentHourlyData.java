package com.example.srisaikumar.weathernow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by srisaikumar on 6/24/2016.
 */
public class FragmentHourlyData extends Fragment {
    Bundle sis;
    Activity activity;
    ViewGroup c;
    LinearLayout linearLayout;
    String city_name;
    String state_name;
    public FragmentHourlyData(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        sis=savedInstanceState;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View view=layoutInflater.inflate(R.layout.fragment_1,container,false);
        linearLayout=(LinearLayout)view.findViewById(R.id.hd_lin);
        city_name=getArguments().getString("CITY");
        state_name=getArguments().getString("STATE");
        TextView tv=(TextView)view.findViewById(R.id.loc);
        tv.setText(city_name+", "+state_name);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sis=savedInstanceState;
        activity=getActivity();
        makeLayout();
        Log.d("demo","OnActivityCreated");
    }

    public void makeLayout(){
        new GetHourlyData().execute(city_name,state_name);
    }


    public class GetHourlyData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            WeatherItemList.getInstance().weatherItems.clear();
            String api_key="0c854303c9d56839";
            String city=params[0];
            String state=params[1];
            StringBuilder sb=new StringBuilder();
            try {
                WeatherItemList weatherItemList=WeatherItemList.getInstance();
                WeatherItem tempWeatherItem=new WeatherItem();
                URL url=new URL("http://api.wunderground.com/api/"+api_key+"/hourly/q/"+state+"/"+city+".xml");
                URLConnection connection=(URLConnection)url.openConnection();
                InputStream inputStream=connection.getInputStream();
                XmlPullParserFactory xmlPullParserFactory=XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser=xmlPullParserFactory.newPullParser();
                xmlPullParser.setInput(inputStream,null);
                int event=xmlPullParser.getEventType();
                int timeFlag=0, tempFlag=0, dewPointFlag=0, feelsLikeFlag=0, wspdFlag=0, wdirFlag=0, pressureFlag=0;
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
                                }
                            }
                            //clouds
                            else if(name.equals("condition")){
                                tempWeatherItem.setClouds(xmlPullParser.nextText());
                            }
                            //dew point
                            else if(name.equals("dewpoint")){
                                dewPointFlag=1;
                            }
                            else if(dewPointFlag==1){
                                if(name.equals("english")){
                                    tempWeatherItem.setDewPoint(xmlPullParser.nextText());
                                    dewPointFlag=0;
                                }
                            }
                            //icon url
                            else if(name.equals("icon_url")){
                                tempWeatherItem.setIconUrl(xmlPullParser.nextText());
                            }
                            //wind speed
                            else if(name.equals("wspd")){
                                wspdFlag=1;
                            }
                            else if(wspdFlag==1){
                                if(name.equals("english")){
                                    tempWeatherItem.setWindSpeed(xmlPullParser.nextText());
                                    wspdFlag=0;
                                }
                            }
                            //wind direction
                            else if(name.equals("wdir")){
                                wdirFlag=1;
                            }
                            else if(wdirFlag==1){
                                if(name.equals("dir")){
                                    tempWeatherItem.setWindDirection(xmlPullParser.nextText());
                                }
                                else if(name.equals("degrees")){
                                    String temp=xmlPullParser.nextText()+" "+tempWeatherItem.getWindDirection();
                                    tempWeatherItem.setWindDirection(temp);
                                    wdirFlag=0;
                                }
                            }
                            //climate type
                            else if(name.equals("wx")){
                                tempWeatherItem.setClimateType(xmlPullParser.nextText());
                            }
                            //humidity
                            else if(name.equals("humidity")){
                                tempWeatherItem.setHumidity(xmlPullParser.nextText());
                            }
                            //feels like
                            else if(name.equals("feelslike")){
                                feelsLikeFlag=1;
                            }
                            else if(feelsLikeFlag==1){
                                if(name.equals("english")){
                                    tempWeatherItem.setFeelsLike(xmlPullParser.nextText());
                                    feelsLikeFlag=0;
                                }
                            }
                            //pressure
                            else if(name.equals("mslp")){
                                pressureFlag=1;
                            }
                            else if(pressureFlag==1){
                                if(name.equals("metric")){
                                    tempWeatherItem.setPressure(xmlPullParser.nextText());
                                    pressureFlag=0;
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(name.equals("forecast")){
                                weatherItemList.weatherItems.add(tempWeatherItem);
                                tempWeatherItem=new WeatherItem();
                            }
                            break;

                    }
                    event=xmlPullParser.next();
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;


        }

        @Override
        protected void onPostExecute(String s) {
            LinearLayout linearLayout=(LinearLayout)getActivity().findViewById(R.id.hd_lin1);
            linearLayout.setVisibility(View.GONE);
            ListView listView=(ListView)getActivity().findViewById(R.id.hd_list);
            WeatherItemList weatherItemList=WeatherItemList.getInstance();
            WeatherView[] weatherView=new WeatherView[weatherItemList.weatherItems.size()];
            for(int i=0;i<weatherItemList.weatherItems.size();i++){
                weatherView[i]=new WeatherView(weatherItemList.weatherItems.get(i).getIconUrl(),
                        weatherItemList.weatherItems.get(i).getTime(),
                        weatherItemList.weatherItems.get(i).getClimateType(),
                        weatherItemList.weatherItems.get(i).getTemperature());
            }
            listView.setAdapter(new HourlyDisplayAdapter(getActivity().getApplicationContext(),R.layout.list_view_hd,weatherView));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(getActivity().getApplicationContext(),Details.class);
                    intent.putExtra("OBJECT",position);
                    intent.putExtra("LOCATION",city_name+", "+state_name);
                    startActivity(intent);
                }
            });
        }


    }

    class WeatherView{
        String icon;
        String title;
        String climate;
        String temperature;
        WeatherView(String icon, String title, String climate, String temperature){
            this.icon=icon;
            this.title=title;
            this.climate=climate;
            this.temperature=temperature;
        }
    }
    class HourlyDisplayAdapter extends ArrayAdapter<WeatherView> {
        Context context;
        WeatherView[] weatherViews=null;
        int layoutResourceId;
        public HourlyDisplayAdapter(Context context, int resource, WeatherView[] objects) {
            super(context, resource, objects);
            this.context=context;
            this.layoutResourceId=resource;
            this.weatherViews=objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row=convertView;
            HourlyDataHolder hourlyDataHolder=null;
            if(row==null){
                row=getLayoutInflater(sis).inflate(layoutResourceId,parent,false);
                hourlyDataHolder=new HourlyDataHolder();
                hourlyDataHolder.imageView=(ImageView)row.findViewById(R.id.image_view);
                hourlyDataHolder.title=(TextView)row.findViewById(R.id.textView_1);
                hourlyDataHolder.climate=(TextView)row.findViewById(R.id.textView_2);
                hourlyDataHolder.temperature=(TextView)row.findViewById(R.id.textView_3);
                row.setTag(hourlyDataHolder);
            }
            else
                hourlyDataHolder= (HourlyDataHolder) row.getTag();

            hourlyDataHolder.temperature.setText(weatherViews[position].temperature);
            hourlyDataHolder.climate.setText(weatherViews[position].climate);
            hourlyDataHolder.title.setText(weatherViews[position].title);
            Picasso.with(context).load(weatherViews[position].icon).into(hourlyDataHolder.imageView);
            return row;
        }


        class HourlyDataHolder{
            ImageView imageView;
            TextView title;
            TextView climate;
            TextView temperature;
        }
    }


}
