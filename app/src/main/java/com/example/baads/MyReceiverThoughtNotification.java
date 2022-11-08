package com.example.baads;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

//By using the broadcast receiver default class
//And sourcing from https://www.youtube.com/watch?v=xSrVWFCtgaE
//Huge credit to Foxandroid
//I was having trouble setting up the notification and sound part for the alarm,
//and used this code to be able to set up an alarm system
//A majority of code is here thanks to them.
//Re-adapted for positive affirmations
public class MyReceiverThoughtNotification extends BroadcastReceiver {

    private String[] positiveThoughts = new String[]{
            "You are doing great!",
            "You are valued!",
            "Tough times end, tough people last."};

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Positive Thoughts")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(positiveThoughts[(int) (Math.random()%3)])
                .setContentText("Positive Affirmations")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());
    }
}