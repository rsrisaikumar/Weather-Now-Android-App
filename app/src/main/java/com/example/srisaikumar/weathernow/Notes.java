package com.example.srisaikumar.weathernow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.List;

/**
 * Created by srisaikumar on 6/28/2016.
 */
public class Notes extends AppCompatActivity {
    LinearLayout linearLayout=null;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notesview);
        linearLayout=(LinearLayout)findViewById(R.id.note_lin);
        ListView listView=(ListView)findViewById(R.id.note_list);
        NotesView[] notesViews=new NotesView[NotesItemList.getInstance().notesItem.size()];
        for(int i=0;i<NotesItemList.getInstance().notesItem.size();i++){
            notesViews[i]=new NotesView(NotesItemList.getInstance().notesItem.get(i).getNote(),
                    NotesItemList.getInstance().notesItem.get(i).getLocation(),
                    NotesItemList.getInstance().notesItem.get(i).getDate());
        }
        listView.setAdapter(new NoteListAdapter(getApplicationContext(),R.layout.list_notes,notesViews));
        listView.setEnabled(false);
    }

    public class NotesView{
        public String note;
        public String location;
        public String date;

        public NotesView(){
            this.note="";
            this.location="";
            this.date="";
        }
        public NotesView(String note, String location, String date){
            this.note=note;
            this.location=location;
            this.date=date;
        }
    }

    public class NoteListAdapter extends ArrayAdapter<NotesView>{

        Context context;
        int layoutresourceId;
        NotesView[] notesViews=null;
        public NoteListAdapter(Context context, int resource, NotesView[] objects) {
            super(context, resource, objects);
            this.context=context;
            this.layoutresourceId=resource;
            this.notesViews=objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row=convertView;
            NotesHolder notesHolder=null;
            if(row==null){
                row=getLayoutInflater().inflate(R.layout.list_notes,parent,false);
                notesHolder=new NotesHolder();
                notesHolder.note=(TextView)row.findViewById(R.id.note_context);
                notesHolder.location=(TextView)row.findViewById(R.id.note_location);
                notesHolder.date=(TextView)row.findViewById(R.id.note_date);
                row.setTag(notesHolder);
            }
            else
                notesHolder= (NotesHolder) row.getTag();

            notesHolder.note.setText(notesViews[position].note);
            notesHolder.location.setText(notesViews[position].location);
            notesHolder.date.setText(notesViews[position].date);
            return row;
        }

        public class NotesHolder{
            TextView note;
            TextView location;
            TextView date;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(Notes.this,MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

}
