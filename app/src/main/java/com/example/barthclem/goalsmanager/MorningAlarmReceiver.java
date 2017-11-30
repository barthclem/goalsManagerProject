package com.example.barthclem.goalsmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by barthclem on 5/20/16.
 */
public class MorningAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service=new Intent(context,GoodMorningAlarmService.class);
        context.startService(service);
    }
}
