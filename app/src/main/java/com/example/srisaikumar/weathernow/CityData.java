package com.example.srisaikumar.weathernow;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by srisaikumar on 6/23/2016.
 */
public class CityData extends AppCompatActivity {
    String city_name, state_name;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    String date=null;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_w);

        Intent intent=getIntent();
        final String[] temp=intent.getStringExtra("CITY_STATE").split(", ");
        city_name=temp[0];
        state_name=temp[1];
        date=intent.getStringExtra("DATE");
        TextView tv=new TextView(this);
        tv.setText(city_name+", "+state_name);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.weather_logo);
        setSupportActionBar(toolbar);

        viewPager=(ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.colorListTitle)));
        tabLayout.setupWithViewPager(viewPager);

    }


    public void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        FragmentHourlyData fragmentHourlyData=new FragmentHourlyData();
        Bundle bundle=new Bundle();
        bundle.putString("CITY",city_name);
        bundle.putString("STATE",state_name);
        bundle.putString("DATE",date);
        fragmentHourlyData.setArguments(bundle);
        FragmentForecast fragmentForecast=new FragmentForecast();
        fragmentForecast.setArguments(bundle);
        adapter.addFragment(fragmentHourlyData,getResources().getString(R.string.hourly_data));
        adapter.addFragment(fragmentForecast,getResources().getString(R.string.forecast));
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList=new ArrayList<>();
        private final List<String> mFragmentTitleList=new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(CityData.this,MainActivity.class);
            startActivity(intent);
        }
        return true;
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
