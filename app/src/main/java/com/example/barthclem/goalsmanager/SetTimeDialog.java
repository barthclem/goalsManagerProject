package com.example.barthclem.goalsmanager;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by barthclem on 5/17/16.
 */
public class SetTimeDialog implements View.OnFocusChangeListener ,TimePickerDialog.OnTimeSetListener{
    private EditText editText;
    private Calendar calendar;
    private Context context;
    private String time="";
    private  ReminderDatabase db;

    private int hour,min;

    public SetTimeDialog(EditText editText,Context context) {
        this.editText=editText;
        this.editText.setOnFocusChangeListener(this);
        this.calendar=Calendar.getInstance();
        this.context=context;
    }
    public SetTimeDialog(Context context) {

        this.calendar=Calendar.getInstance();
        this.context=context;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //if text edit has focus
        if(hasFocus){
            int hour=calendar.get(Calendar.HOUR_OF_DAY);
            int minute=calendar.get(Calendar.MINUTE);
            new TimePickerDialog(context,this,hour,minute,true).show();


        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //this.editText.setText(hourOfDay+":"+minute);
        setHour(hourOfDay);
        setMin(minute);
        if(!time.equals("")){
            Log.e("morning hour",""+getHour());
            Log.e("morning min",""+getMin());
            boolean u=db.changeReminderTime(time,"on",hour,minute);
            Log.e("result",""+u);
        }

    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

}
