package com.example.barthclem.goalsmanager;

import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by barthclem on 5/13/16.
 */
public class WhatIsYourFocusAdapter extends CursorAdapter
{

    private TextView title,duration;
    private Button deleteButton,editButton,rating,priority;
    private CheckBox status;
    private EditText editTitle;
    private Cursor cursor;
    private int counter=0;
    private ViewSwitcher textSwitcher;

    private String checkBoxStatus,newTitle;// this is the checkbox that shows the status of the goal
    private int position;
    public WhatisYourFocusAdapter(Context context) {
        super(context,null,false);
        cursor=null;

    }//close constructors

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView=inflater.inflate(R.layout.each_focus_task,parent,false);

        return convertView;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        title = (TextView) view.findViewById(R.id.taskTitle);
        duration = (TextView) view.findViewById(R.id.taskDuration);

        String cursorDate=cursor.getString(cursor.getColumnIndex(AddSubContent.COLUMN_DUEDATE));

        //setting the content of the tag
        //if(new WhatIsYourFocusPopulator().todayGoals(cursorDate)){
            title.setText(cursor.getString(1));
            duration.setText(new DurationFormatPrinter(cursorDate).Printer());
            counter++;
        //}

        //if(counter==0){
          // swapCursor(cursor);
        //}



    }



    @Override
    public Cursor swapCursor(Cursor newCursor) {
        return super.swapCursor(newCursor);
    }




}
