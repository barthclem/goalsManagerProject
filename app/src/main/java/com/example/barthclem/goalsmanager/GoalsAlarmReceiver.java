package com.example.barthclem.goalsmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

/**
 * Created by barthclem on 5/16/16.
 */
public class GoalsAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"message received...processing order",Toast.LENGTH_SHORT).show();
        Intent service1=new Intent(context,GoalAlarmService.class);
        context.startService(service1);
        ///}


    }
}

