package com.example.barthclem.goalsmanager;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static java.security.AccessController.getContext;


public class GoalReminderSetting extends Fragment {
    private Switch switchButton;
    private Button morningButton,eveningButton;
    private int morningHour,morningMin,eveningHour,eveningMin;

    private OnFragmentInteractionListener mListener;
    ReminderDatabase db;

    public GoalReminderSetting() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         db=new ReminderDatabase(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_goal_reminder_setting, container, false);
        switchButton=(Switch) view.findViewById(R.id.switchButton);
        morningButton=(Button)view.findViewById(R.id.morningTimeButton);
        eveningButton=(Button)view.findViewById(R.id.eveningTimeButton);



        switchButton.setOnCheckedChangeListener(statusListener);



        //SetTimeDialog morning=new SetTimeDialog(db,db.MORNING,morningButton,getContext());

        //SetTimeDialog evening=new SetTimeDialog(db,db.EVENING,eveningButton,getContext());

        eveningButton.setOnClickListener(new View.OnClickListener() {
            Calendar calendar=Calendar.getInstance();
            @Override
            public void onClick(View v) {
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int minute=calendar.get(Calendar.MINUTE);

                new TimePickerDialog(getContext(),timeStorm,hour,minute,true).show();

            }
        });



        morningButton.setOnClickListener(new View.OnClickListener() {
            Calendar calendar=Calendar.getInstance();
            @Override
            public void onClick(View v) {
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int minute=calendar.get(Calendar.MINUTE);

                new TimePickerDialog(getContext(),timeStorm1,hour,minute,true).show();

            }
        });
        return view;
    }





    public TimePickerDialog.OnTimeSetListener timeStorm=new TimePickerDialog.OnTimeSetListener(){
        int hour,min;
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setHour(hourOfDay);
            setMin(minute);

            Toast.makeText(getContext(),"The time set is "+hourOfDay+" : "+minute,Toast.LENGTH_SHORT).show();
            db.changeReminderTime(db.EVENING,"on",hourOfDay,minute);
            Log.e("REset min",""+minute);
            Log.e("Reste Hour",""+hourOfDay);
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

    };

    public TimePickerDialog.OnTimeSetListener timeStorm1=new TimePickerDialog.OnTimeSetListener(){
        int hour,min;
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setHour(hourOfDay);
            setMin(minute);
            db.changeReminderTime(db.MORNING,"on",hourOfDay,minute);
            Toast.makeText(getContext(),"The time set is "+hourOfDay+" : "+minute,Toast.LENGTH_SHORT).show();
            Log.e("REset min",""+minute);
            Log.e("Reste Hour",""+hourOfDay);
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

    };






    public CompoundButton.OnCheckedChangeListener statusListener=new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String switcher="";
            if(switchButton.isChecked()){
                switcher="off";}
                else switcher="on";
              db.changeReminderTime("EVENING",switcher,eveningHour,eveningMin);
              db.changeReminderTime("MORNING",switcher,morningHour,morningMin);
        }

    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {

        }
    }




    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

    }
}
