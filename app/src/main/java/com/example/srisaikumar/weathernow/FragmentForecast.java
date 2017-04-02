package com.example.srisaikumar.weathernow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by srisaikumar on 6/24/2016.
 */
public class FragmentForecast extends Fragment {
    Activity activity;
    Bundle sis;
    String city_name;
    String state_name;
    String date1=null;

    public FragmentForecast(){
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View view=layoutInflater.inflate(R.layout.fragment_2,container,false);
        city_name=getArguments().getString("CITY");
        state_name=getArguments().getString("STATE");
        date1=getArguments().getString("DATE");
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
    }

    public void makeLayout(){
        new GetForecastData().execute(city_name,state_name);
    }

    public class GetForecastData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            ForecastItemList.getInstance().forecastItems.clear();
            String api_key="0c854303c9d56839";
            String city=params[0];
            String state=params[1];
            StringBuilder sb=new StringBuilder();
            try {
                ForecastItemList forecastItemList=ForecastItemList.getInstance();
                ForecastItem tempForecastItem=new ForecastItem();
                URL url=new URL("http://api.wunderground.com/api/"+api_key+"/forecast10day/q/"+state+"/"+city+".xml");
                URLConnection connection=(URLConnection)url.openConnection();
                InputStream inputStream=connection.getInputStream();
                XmlPullParserFactory xmlPullParserFactory=XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser=xmlPullParserFactory.newPullParser();
                xmlPullParser.setInput(inputStream,null);
                int event=xmlPullParser.getEventType();
                int startFlag=0, highTempFlag=0, lowTempFlag=0, mwspdFlag=0, wdirFlag=0;
                while (event!=xmlPullParser.END_DOCUMENT){
                    String name=xmlPullParser.getName();
                    switch (event){
                        case XmlPullParser.START_TAG:
                            if(name.equals("simpleforecast")){
                                startFlag=1;
                            }

                            if(startFlag==1) {
                                //date
                                if (name.equals("pretty")) {
                                    String date=xmlPullParser.nextText();
                                    StringBuilder sb1=new StringBuilder();
                                    for(int i=15;i<date.length();i++){
                                        sb1.append(date.charAt(i));
                                    }
                                    tempForecastItem.setDate(sb1.toString());
                                }
                                //highTemp
                                else if (name.equals("high")) {
                                    highTempFlag = 1;
                                } else if (highTempFlag == 1) {
                                    if (name.equals("fahrenheit")) {
                                        tempForecastItem.setHighTemp(xmlPullParser.nextText()+(char)0x00B0+"F");
                                        highTempFlag = 0;
                                    }
                                }
                                //lowTemp
                                else if (name.equals("low")) {
                                    lowTempFlag = 1;
                                } else if (lowTempFlag == 1) {
                                    if (name.equals("fahrenheit")) {
                                        tempForecastItem.setLowTemp(xmlPullParser.nextText()+(char)0x00B0+"F");
                                        lowTempFlag = 0;
                                    }
                                }
                                //clouds
                                else if (name.equals("conditions")) {
                                    tempForecastItem.setClouds(xmlPullParser.nextText());
                                }
                                //iconURL
                                else if (name.equals("icon_url")) {
                                    tempForecastItem.setIconURL(xmlPullParser.nextText());
                                }
                                //maxWindSpeed and Wind Direction
                                else if (name.equals("maxwind")) {
                                    mwspdFlag = 1;
                                } else if (mwspdFlag == 1) {
                                    if (name.equals("mph")) {
                                        tempForecastItem.setMaxWindSpeed(xmlPullParser.nextText());
                                    } else if (name.equals("dir")) {
                                        tempForecastItem.setWindDirection(xmlPullParser.nextText());
                                        mwspdFlag = 0;
                                    }
                                }
                                //avgHumidity
                                else if (name.equals("avehumidity")) {
                                    tempForecastItem.setWindDirection(xmlPullParser.nextText());
                                }
                                break;
                            }
                        case XmlPullParser.END_TAG:
                            if(name.equals("forecastday")&&startFlag==1){
                                forecastItemList.forecastItems.add(tempForecastItem);
                                tempForecastItem=new ForecastItem();
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
            LinearLayout linearLayout=(LinearLayout)getActivity().findViewById(R.id.fc_lin1);
            linearLayout.setVisibility(View.GONE);
            ListView listView=(ListView)getActivity().findViewById(R.id.fc_list);
            ForecastItemList forecastItemList=ForecastItemList.getInstance();
            ForecastView[] forecastView=new ForecastView[forecastItemList.forecastItems.size()];
            for(int i=0;i<forecastItemList.forecastItems.size();i++){
                forecastView[i]=new ForecastView(forecastItemList.forecastItems.get(i).getIconURL(),
                        forecastItemList.forecastItems.get(i).getDate(),
                        forecastItemList.forecastItems.get(i).getClouds(),
                        forecastItemList.forecastItems.get(i).getHighTemp(),
                        forecastItemList.forecastItems.get(i).getLowTemp());
            }
            listView.setAdapter(new ForecastDisplayAdapter(getActivity().getApplicationContext(),R.layout.list_view_fc,forecastView));

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    int flag=0;
                    for(int i=0;i<NotesItemList.getInstance().notesItem.size();i++){
                        if(NotesItemList.getInstance().notesItem.get(i).getDate().equals(ForecastItemList.getInstance().forecastItems.get(position).getDate())
                                &&NotesItemList.getInstance().notesItem.get(i).getLocation().equals(city_name+", "+state_name)){
                            NotesItemList.getInstance().notesItem.remove(i);
                            makeLayout();
                            Toast.makeText(getActivity().getApplicationContext(),"Note removed Successfully!",Toast.LENGTH_SHORT).show();
                            flag=1;
                            break;
                        }
                    }
                    if(flag==0) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), AddNotes.class);
                        intent.putExtra("CITY_STATE", city_name + ", " + state_name);
                        intent.putExtra("DATE", ForecastItemList.getInstance().forecastItems.get(position).getDate());
                        startActivity(intent);
                    }
                    return true;
                }
            });
        }
    }

    class ForecastView{
        String icon;
        String title;
        String climate;
        String highTemp;
        String lowTemp;
        ForecastView(String icon, String title, String climate, String highTemp, String lowTemp){
            this.icon=icon;
            this.title=title;
            this.climate=climate;
            this.highTemp=highTemp;
            this.lowTemp=lowTemp;
        }
    }


    class ForecastDisplayAdapter extends ArrayAdapter<ForecastView> {
        Context context;
        ForecastView[] forecastViews=null;
        int layoutResourceId;
        public ForecastDisplayAdapter(Context context, int resource, ForecastView[] objects) {
            super(context, resource, objects);
            this.context=context;
            this.layoutResourceId=resource;
            this.forecastViews=objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row=convertView;
            ForecastDataHolder forecastDataHolder=null;
            if(row==null){
                row=getLayoutInflater(sis).inflate(layoutResourceId,parent,false);
                forecastDataHolder=new ForecastDataHolder();
                forecastDataHolder.imageView=(ImageView)row.findViewById(R.id.image_view);
                forecastDataHolder.title=(TextView)row.findViewById(R.id.textView_1);
                forecastDataHolder.climate=(TextView)row.findViewById(R.id.textView_2);
                forecastDataHolder.highTemp=(TextView)row.findViewById(R.id.textView_3);
                forecastDataHolder.lowTemp=(TextView)row.findViewById(R.id.textView_4);
                forecastDataHolder.im=(ImageView)row.findViewById(R.id.fc_notes);
                row.setTag(forecastDataHolder);
            }
            else
                forecastDataHolder= (ForecastDataHolder) row.getTag();

            forecastDataHolder.highTemp.setText(forecastViews[position].highTemp);
            forecastDataHolder.lowTemp.setText(forecastViews[position].lowTemp);
            forecastDataHolder.climate.setText(forecastViews[position].climate);
            forecastDataHolder.title.setText(forecastViews[position].title);
            for(int i=0;i<NotesItemList.getInstance().notesItem.size();i++){
                if(NotesItemList.getInstance().notesItem.get(i).getDate().equals(ForecastItemList.getInstance().forecastItems.get(position).getDate()
                    )&&NotesItemList.getInstance().notesItem.get(i).getLocation().equals(city_name+", "+state_name)){
                    forecastDataHolder.im.setVisibility(View.VISIBLE);
                }
            }
            Picasso.with(context).load(forecastViews[position].icon).into(forecastDataHolder.imageView);
            return row;
        }


        class ForecastDataHolder{
            ImageView imageView;
            TextView title;
            TextView climate;
            TextView highTemp;
            TextView lowTemp;
            ImageView im;
        }
    }


}
