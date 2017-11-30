package com.example.barthclem.goalsmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements StartPage.OnFragmentInteractionListener,forgotPassword.OnFragmentInteractionListener{

    SharedPreferences pref;
    boolean isFirstLaunch;
    GoalsDataBaseManager db;
    ReminderDatabase AlarmDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new GoalsDataBaseManager(this);
        int userCount=db.getName("USERDETAILS").getCount();



        pref= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        isFirstLaunch=pref.getBoolean("FirstLaunch",true);
        AlarmDb=new ReminderDatabase(this);

        Intent mainintent=getIntent();

        if(isFirstLaunch&&(userCount==0)){
            SharedPreferences.Editor edit=pref.edit();
            edit.putBoolean("FirstLaunch",false);
            edit.apply();

           if(AlarmDb.intiatiateReminder(ReminderDatabase.MORNING, ReminderDatabase.STATUSON,6,30))
               Toast.makeText(this,"Upload Morning successful",Toast.LENGTH_SHORT).show();
            if(AlarmDb.intiatiateReminder(ReminderDatabase.EVENING, ReminderDatabase.STATUSON,18,30))
            Toast.makeText(this,"Upload Evening successful",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,Welcome.class);
            startActivity(intent);
            }//close
        else{
            getSupportFragmentManager().beginTransaction().add(R.id.fragement_container,new StartPage()).commit();

        }

    }//close this first runner method


    @Override
    public void onLinkClick() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,new forgotPassword()).commit();
    }//close onLinkClick

    @Override
    public void responseToAnswer() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,new ChangeLastPassword()).commit();

    }
}
