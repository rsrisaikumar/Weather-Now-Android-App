package com.example.srisaikumar.weathernow;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;

/**
 * Created by srisaikumar on 6/23/2016.
 */
public class AddCity extends AppCompatActivity {
    private static CityDataManager cityDataManager;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        try{
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setIcon(R.drawable.weather_logo);
        }catch (Exception e){}

        cityDataManager=new CityDataManager(this);

        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);

        final EditText edit_city=new EditText(this);
        LinearLayout.LayoutParams lp_city= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp_city.setMargins(0,50,0,30);
        edit_city.setLayoutParams(lp_city);
        edit_city.setHint(R.string.hint_editCity);
        linearLayout.addView(edit_city);
        final EditText edit_state=new EditText(this);
        LinearLayout.LayoutParams lp_state= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp_state.setMargins(0,0,0,100);
        edit_state.setLayoutParams(lp_state);
        edit_state.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        edit_state.setHint(R.string.hint_editState);
        linearLayout.addView(edit_state);

        final Button button_saveCity=new Button(this);
        LinearLayout.LayoutParams lp_saveCity= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp_saveCity.setMargins(0,100,0,0);
        button_saveCity.setLayoutParams(lp_saveCity);
        button_saveCity.setText(R.string.button_saveCity);
        button_saveCity.setAllCaps(false);
        button_saveCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(edit_city.getText().toString().trim())||isEmpty(edit_state.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"No field can be blank!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Cities cities=new Cities();
                cities.setCityName(edit_city.getText().toString());
                cities.setState(edit_state.getText().toString());
                cityDataManager.saveCity(cities);
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("CITY",edit_city.getText().toString().trim().replace(" ","_"));
                intent.putExtra("STATE",edit_state.getText().toString().trim());
                startActivity(intent);
            }
        });
        linearLayout.addView(button_saveCity);
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event){
        if(keycode==event.KEYCODE_BACK){
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
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


    @Override
    protected void onDestroy() {
        cityDataManager.close();
        super.onDestroy();
    }
}
