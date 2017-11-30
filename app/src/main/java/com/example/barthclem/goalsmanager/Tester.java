package com.example.barthclem.goalsmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Intent;

import android.database.Cursor;

import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;


public class Tester extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,GoalReminderSetting.OnFragmentInteractionListener,testGoalFragement.OnFragmentInteractionListener{

    ReminderDatabase db;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=new ReminderDatabase(this);
        setContentView(R.layout.activity_tester);
        getSupportFragmentManager().beginTransaction().replace(R.id.viewListContainer,new testGoalFragement()).addToBackStack(null).commit();


        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.navView);
        toolbar = (Toolbar) findViewById(R.id.toolBar);

        toolbar = (Toolbar) findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.app_name,R.string.about) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();




        queryReminderDatabase();
        setMorningReminder(timer[0],timer[1]);
        //setEveningReminder(timer[2],timer[3]);
  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_page,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id){
            case R.id.action_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.viewListContainer,new GoalReminderSetting()).addToBackStack(null).commit();
                break;
            case R.id.changePassword:
                getSupportFragmentManager().beginTransaction().replace(R.id.viewListContainer,new ChangePassword()).addToBackStack(null).commit();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGoalSelected(Uri goalUri) {

    }

    @Override
    public void onAddSubGoalButtonClick(int mainGoalId) {
      getSupportFragmentManager().beginTransaction().replace(R.id.viewListContainer,subGoalFragement.newInstance(mainGoalId)).addToBackStack(null).commit();

    }





//this part of the program handles the reminder part of the application
//Contributing classes are GoalsAlarmReceiver and GoalAlarmService
    //int for reminder
    private int hour,minute;
    private int []timer =new int[4];
    public void setMorningReminder(int hour,int min){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);

        if(Calendar.getInstance().after(calendar)){
            calendar.add(Calendar.DATE,1);
        }

        Intent intent=new Intent(this,GoalsAlarmReceiver.class);

        PendingIntent pendingIntent= PendingIntent.getBroadcast(this,0,intent,0);

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);


    }
    public void setEveningReminder(int hour,int min){

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);

        if(Calendar.getInstance().after(calendar)){
            calendar.add(Calendar.DATE,1);
        }

        Intent intent=new Intent(this,GoalsAlarmReceiver.class);

        PendingIntent pendingIntent= PendingIntent.getBroadcast(this,0,intent,0);

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);


    }





public void queryReminderDatabase(){
    int i=0;
    Cursor cursor=db.selectTime();

    if(cursor.moveToFirst()){

        while(!cursor.isAfterLast()){
            timer[i]=cursor.getInt(2);
            timer[i+1]=cursor.getInt(3);

            Log.e("Time ",""+cursor.getString(cursor.getColumnIndex(db.TIME)));
            i=2;
            cursor.moveToNext();
        }
    }
}




    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(item.isChecked())
            item.setChecked(false)
            ;
        else
            item.setChecked(true);
        drawerLayout.closeDrawers();
        switch(item.getItemId()){

            case R.id.add_task:
                return true;

            case R.id.taskView:
                getSupportFragmentManager().beginTransaction().replace(R.id.viewListContainer,new testGoalFragement()).addToBackStack(null).commit();
                return true;

            case R.id.reminder:
                getSupportFragmentManager().beginTransaction().replace(R.id.viewListContainer,new GoalReminderSetting()).addToBackStack(null).commit();
                return true;

            case R.id.show_graph:
                getSupportFragmentManager().beginTransaction().replace(R.id.viewListContainer,new GraphFragment()).addToBackStack(null).commit();

                return true;

            default:
                return true;
        }
    }
}