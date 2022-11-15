package com.example.baads.alarm;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.baads.R;

//By using the broadcast receiver default class
//And sourcing from https://www.youtube.com/watch?v=xSrVWFCtgaE
//Huge credit to Foxandroid
//I was having trouble setting up the notification and sound part for the alarm,
//and used this code to be able to set up an alarm system
//A majority of code is here thanks to them.
public class MyReceiver extends BroadcastReceiver {

    public static MediaPlayer alarmSounder;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //Source: http://www.java2s.com/example/java-api/android/app/notificationchannel/setsound-2-0.html
        //Format for Uri creation.
        Uri alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.alarm_sound);

        alarmSounder = MediaPlayer.create(context,alarmSound);
        alarmSounder.start();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Alarm System")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("BAADS stress relief Alarm Manager")
                .setContentText("Wake up time!")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());
    }
}