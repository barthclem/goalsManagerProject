package com.example.barthclem.goalsmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by barthclem on 5/20/16.
 */
public class GoodMorningAlarmService extends Service {
    private NotificationManager manager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Toast.makeText(this,"welcome to the alarm section and notification part",Toast.LENGTH_SHORT).show();

        Intent intents=new Intent(this,WhatisYourFocus.class);
        PendingIntent pend=PendingIntent.getActivity(this,(int)System.currentTimeMillis(),intents,0);

        NotificationCompat.Builder notify=new NotificationCompat.Builder(this);
        notify.setContentTitle("What is your focus for today")
                .setContentText("Open your Focus Mate App to see your goals for today")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("Good Morning,Focus Mate")
                .setContentIntent(pend)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(Notification.PRIORITY_HIGH)
                .build();


        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notify.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
