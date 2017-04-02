package com.example.srisaikumar.weathernow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;

/**
 * Created by srisaikumar on 6/25/2016.
 */
public class AddNotes extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent in=getIntent();
        final String cs=in.getStringExtra("CITY_STATE");
        final String date=in.getStringExtra("DATE");

        final LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText editText=new EditText(this);
        editText.setHint(R.string.noteText);
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setSingleLine();
        editText.setMaxEms(30);

        Button button=new Button(this);
        button.setAllCaps(false);
        button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.saveNote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(editText.getText().toString())){
                    Toast.makeText(getApplicationContext(),"field cannot be empty!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),CityData.class);
                intent.putExtra("NOTE",editText.getText().toString());
                intent.putExtra("DATE",date);
                intent.putExtra("CITY_STATE",cs);
                int flag=0;
                for (int i = 0; i < NotesItemList.getInstance().notesItem.size(); i++) {
                    if (NotesItemList.getInstance().notesItem.get(i).getDate().equals(date)) {
                        NotesItemList.getInstance().notesItem.get(i).setNote(editText.getText().toString());
                        flag=1;
                        break;
                    }
                }
                if(flag==0){
                    NotesItemList.getInstance().notesItem.add(new NotesItem(cs, date, editText.getText().toString()));
                }
                Toast.makeText(getApplicationContext(),"Note saved Successfully!",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        linearLayout.addView(editText);
        linearLayout.addView(button);

        setContentView(linearLayout);

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
