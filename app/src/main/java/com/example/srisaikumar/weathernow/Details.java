package com.example.srisaikumar.weathernow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by srisaikumar on 6/25/2016.
 */
public class Details extends AppCompatActivity {
    String city_state;
    Bitmap bitmap;
    int width;
    int height;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        city_state=intent.getStringExtra("LOCATION");
        try{
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setIcon(R.drawable.weather_logo);
        }catch (Exception e){}


        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setId(R.id.details_lin);

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int height=displayMetrics.heightPixels;
        setContentView(linearLayout);

        createDisplay(getWeatherItem(intent.getStringExtra("OBJECT")));
    }

    public void createDisplay(final int position){
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.details_lin);
        linearLayout.setBackgroundColor(Color.WHITE);
        final WeatherItemList weatherItemList=WeatherItemList.getInstance();
        LinearLayout linearLayout1=new LinearLayout(this);
        linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout1.setBackgroundColor(getResources().getColor(R.color.colorTitle));
        TextView location_label=new TextView(this,null,android.R.attr.textAppearanceSmall);
        location_label.setPadding(10,10,3,5);
        location_label.setText(R.string.curr_loc_label);
        TextView location=new TextView(this,null,android.R.attr.textAppearanceSmall);
        String title=city_state+" ("+weatherItemList.weatherItems.get(position).getTime()+")";
        location_label.setPadding(0,10,0,5);
        location.setText(title);
        location.setTypeface(null, Typeface.BOLD);
        linearLayout1.addView(location_label);
        linearLayout1.addView(location);
        linearLayout.addView(linearLayout1);

        LinearLayout linearLayout_b=new LinearLayout(this);
        linearLayout_b.setOrientation(LinearLayout.VERTICAL);
        linearLayout_b.setBackground(getResources().getDrawable(R.drawable.border_lin));

        LinearLayout linearLayout2=new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(0,30,0,0);
        linearLayout2.setLayoutParams(layoutParams2);
        linearLayout2.setOrientation(LinearLayout.VERTICAL);
        linearLayout2.setGravity(Gravity.CENTER_HORIZONTAL);
        ProgressBar progressBar=new ProgressBar(this);
        progressBar.setId(R.id.details_progress);
        linearLayout2.addView(progressBar);
        linearLayout2.setId(R.id.details_im_lin);
        linearLayout_b.addView(linearLayout2);
        new LoadImage().execute(weatherItemList.weatherItems.get(position).getIconUrl(),""+position);

        LinearLayout linearLayout3=new LinearLayout(this);
        linearLayout3.setOrientation(LinearLayout.VERTICAL);
        linearLayout3.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView temperature=new TextView(this,null,android.R.attr.textAppearanceLarge);
        temperature.setTextColor(getResources().getColor(R.color.colorTemp));
        temperature.setText(weatherItemList.weatherItems.get(position).getTemperature());
        temperature.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView cloud=new TextView(this,null,android.R.attr.textAppearanceLarge);
        cloud.setText(weatherItemList.weatherItems.get(position).getClouds());
        cloud.setTextColor(getResources().getColor(R.color.colorList));
        cloud.setGravity(Gravity.CENTER_HORIZONTAL);
        cloud.setPadding(0,0,0,30);
        linearLayout3.addView(temperature);
        linearLayout3.addView(cloud);
        linearLayout_b.addView(linearLayout3);

        LinearLayout linearLayout4=new LinearLayout(this);
        linearLayout4.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView max_label=new TextView(this);
        max_label.setText(R.string.max_label);
        TextView max_temp=new TextView(this);
        max_temp.setTypeface(null, Typeface.BOLD);
        max_temp.setText(getMaxTemp()+" Farenheit");
        linearLayout4.addView(max_label);
        linearLayout4.addView(max_temp);
        linearLayout_b.addView(linearLayout4);
        LinearLayout linearLayout5=new LinearLayout(this);
        linearLayout5.setGravity(Gravity.CENTER_HORIZONTAL);
        final LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,60);
        linearLayout5.setLayoutParams(layoutParams);
        TextView min_label=new TextView(this);
        min_label.setText(R.string.min_label);
        TextView min_temp=new TextView(this);
        min_temp.setTypeface(null,Typeface.BOLD);
        min_temp.setText(getMinTemp()+" Farenheit");
        linearLayout5.addView(min_label);
        linearLayout5.addView(min_temp);
        linearLayout_b.addView(linearLayout5);
        linearLayout.addView(linearLayout_b);


        TableLayout tableLayout=new TableLayout(this);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        tableLayout.setStretchAllColumns(true);

        TableRow tableRow1=new TableRow(this);
        TextView feelslike_label=new TextView(this);
        feelslike_label.setGravity(Gravity.CENTER_HORIZONTAL);
        feelslike_label.setText(R.string.feelslike);
        feelslike_label.setPadding(0,30,0,30);
        final TextView feelslike=new TextView(this);
        feelslike.setTypeface(null, Typeface.BOLD);
        feelslike.setText(weatherItemList.weatherItems.get(position).getFeelsLike()+" Farenheit");
        tableRow1.addView(feelslike_label);
        tableRow1.addView(feelslike);
        tableLayout.addView(tableRow1);

        TableRow tableRow2=new TableRow(this);
        TextView humidity_label=new TextView(this);
        humidity_label.setGravity(Gravity.CENTER_HORIZONTAL);
        humidity_label.setText(R.string.humidity);
        humidity_label.setPadding(0,0,0,30);
        TextView humidity=new TextView(this);
        humidity.setTypeface(null, Typeface.BOLD);
        humidity.setText(weatherItemList.weatherItems.get(position).getHumidity()+"%");
        tableRow2.addView(humidity_label);
        tableRow2.addView(humidity);
        tableLayout.addView(tableRow2);

        TableRow tableRow3=new TableRow(this);
        TextView dewpoint_label=new TextView(this);
        dewpoint_label.setGravity(Gravity.CENTER_HORIZONTAL);
        dewpoint_label.setText(R.string.dewpoint);
        dewpoint_label.setPadding(0,0,0,30);
        TextView dewpoint=new TextView(this);
        dewpoint.setTypeface(null, Typeface.BOLD);
        dewpoint.setText(weatherItemList.weatherItems.get(position).getDewPoint()+" Farenheit");
        tableRow3.addView(dewpoint_label);
        tableRow3.addView(dewpoint);
        tableLayout.addView(tableRow3);

        TableRow tableRow4=new TableRow(this);
        TextView pressure_label=new TextView(this);
        pressure_label.setGravity(Gravity.CENTER_HORIZONTAL);
        pressure_label.setText(R.string.pressure);
        pressure_label.setPadding(0,0,0,30);
        TextView pressure=new TextView(this);
        pressure.setTypeface(null, Typeface.BOLD);
        pressure.setText(weatherItemList.weatherItems.get(position).getPressure()+" hPa");
        tableRow4.addView(pressure_label);
        tableRow4.addView(pressure);
        tableLayout.addView(tableRow4);

        TableRow tableRow5=new TableRow(this);
        TextView clouds_label=new TextView(this);
        clouds_label.setGravity(Gravity.CENTER_HORIZONTAL);
        clouds_label.setText(R.string.clouds);
        clouds_label.setPadding(0,0,0,30);
        TextView clouds=new TextView(this);
        clouds.setTypeface(null, Typeface.BOLD);
        clouds.setText(weatherItemList.weatherItems.get(position).getClimateType());
        tableRow5.addView(clouds_label);
        tableRow5.addView(clouds);
        tableLayout.addView(tableRow5);

        TableRow tableRow6=new TableRow(this);
        TextView winds_label=new TextView(this);
        winds_label.setGravity(Gravity.CENTER_HORIZONTAL);
        winds_label.setText(R.string.winds);
        TextView winds=new TextView(this);
        winds.setTypeface(null, Typeface.BOLD);
        winds.setText(weatherItemList.weatherItems.get(position).getWindSpeed()+", "+weatherItemList.weatherItems.get(position).getWindDirection());
        tableRow6.addView(winds_label);
        tableRow6.addView(winds);
        tableLayout.addView(tableRow6);


        linearLayout.addView(tableLayout);


        RelativeLayout relativeLayout=new RelativeLayout(this);
        ImageButton previous=new ImageButton(this);
        previous.setImageResource(R.drawable.previous);
        previous.setScaleType(ImageView.ScaleType.FIT_XY);
        previous.setBackgroundColor(Color.TRANSPARENT);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout=(LinearLayout)findViewById(R.id.details_lin);
                if(linearLayout!=null)
                    linearLayout.removeAllViews();
                if(position==0)
                    createDisplay(weatherItemList.weatherItems.size()-1);
                else
                    createDisplay(position-1);
            }
        });
        relativeLayout.addView(previous);

        ImageButton next=new ImageButton(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        next.setLayoutParams(params);
        next.setImageResource(R.drawable.next);
        next.setScaleType(ImageView.ScaleType.FIT_XY);
        next.setBackgroundColor(Color.TRANSPARENT);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout=(LinearLayout)findViewById(R.id.details_lin);
                if(linearLayout!=null)
                    linearLayout.removeAllViews();
                createDisplay((position+1)%weatherItemList.weatherItems.size());
            }
        });
        relativeLayout.addView(next);
        linearLayout.addView(relativeLayout);


    }

    public String getMaxTemp(){
        WeatherItemList weatherItemList=WeatherItemList.getInstance();
        int max=0;
        for(int i=0;i<weatherItemList.weatherItems.size();i++){
            int x=getInteger(weatherItemList.weatherItems.get(i).getTemperature());
            if(x>max)
                max=x;
        }
        return max+"";
    }

    public int getInteger(String s){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='0'||s.charAt(i)=='1'||s.charAt(i)=='2'||s.charAt(i)=='3'||s.charAt(i)=='4'
                    ||s.charAt(i)=='5'||s.charAt(i)=='6'||s.charAt(i)=='7'||s.charAt(i)=='8'
                    ||s.charAt(i)=='9')
                sb.append(s.charAt(i));
        }
        return Integer.parseInt(sb.toString());
    }

    public String getMinTemp(){
        WeatherItemList weatherItemList=WeatherItemList.getInstance();
        int min=999;
        for(int i=0;i<weatherItemList.weatherItems.size();i++){
            int x=getInteger(weatherItemList.weatherItems.get(i).getTemperature());
            if(x<min)
                min=x;
        }
        return min+"";
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(params[0]).getContent());
            } catch (Exception e) {
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap image) {
            ImageView imageView;
            if(image!=null){
                imageView=new ImageView(Details.this);
                imageView.setImageBitmap(image);
                imageView.setMinimumHeight(200);
                imageView.setMinimumWidth(200);
                LinearLayout linearLayout=(LinearLayout)findViewById(R.id.details_im_lin);
                ProgressBar progressBar=(ProgressBar)findViewById(R.id.details_progress);
                linearLayout.removeView(progressBar);
                linearLayout.addView(imageView);
            }
        }
    }

    public int getWeatherItem(String time){
        WeatherItemList weatherItemList=WeatherItemList.getInstance();
        for(int i=0;i<weatherItemList.weatherItems.size();i++){
            if(weatherItemList.weatherItems.get(i).getTime().equals(time)){
                return i;
            }
        }
        return 0;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_addCity:
                Intent intent=new Intent(this, AddCity.class);
                startActivity(intent);
                return true;
            case R.id.action_clearSavedCity:
                MainActivity.SampleWeatherItemsList.getInstance().sampleWeatherItems.clear();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_overflow,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
