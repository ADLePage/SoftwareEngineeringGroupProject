package com.example.baads.positiveThoughts;

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
//Re-adapted for positive affirmations
//Sound used https://freesound.org/people/IENBA/sounds/545495/
//https://creativecommons.org/licenses/by/4.0/
public class MyReceiverThoughtNotificationDEPRECATED extends BroadcastReceiver {

    private String[] positiveThoughts = new String[]{
            "You are doing great!",
            "You are valued!",
            "Tough times end, tough people last."};

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        //Source: http://www.java2s.com/example/java-api/android/app/notificationchannel/setsound-2-0.html
        //Format for Uri creation.
        Uri positiveSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.positive_sound);

        MediaPlayer soundPlayer = MediaPlayer.create(context,positiveSound);
        soundPlayer.start();
        int random = (int) (Math.random()%3);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Positive Thoughts")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(positiveThoughts[random])
                .setContentText("Positive Affirmations")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());
    }
}