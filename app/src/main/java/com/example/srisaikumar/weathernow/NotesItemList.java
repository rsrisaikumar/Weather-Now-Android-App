package com.example.srisaikumar.weathernow;

import java.util.ArrayList;

/**
 * Created by srisaikumar on 6/28/2016.
 */
public class NotesItemList {
    public ArrayList<NotesItem> notesItem;

    private NotesItemList(){
        notesItem=new ArrayList<>();
    }

    private static NotesItemList notesItemList;
    public static NotesItemList getInstance(){
        if(notesItemList==null)
            notesItemList=new NotesItemList();
        return notesItemList;
    }

}
