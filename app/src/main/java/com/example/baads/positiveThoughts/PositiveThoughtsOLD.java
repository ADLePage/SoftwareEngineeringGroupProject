package com.example.baads.positiveThoughts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.baads.R;

import java.util.Calendar;


//@Author Aidan LePage
//Huge credit to Foxandroid. Re-adapted my AlarmClockNew class using a lot of their
//code from their tutorial https://www.youtube.com/watch?v=xSrVWFCtgaE
//for notification manager and a receiver class.
public class PositiveThoughtsOLD extends AppCompatActivity {

    private com.example.baads.databinding.ActivityPositiveThoughtsBinding binding;
    private boolean isSwitchFlipped = false;
    private AlarmManager mainAlarm;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = com.example.baads.databinding.ActivityPositiveThoughtsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setContentView(R.layout.activity_positive_thoughts);

        resetPageToSave();

        createNotificationForAlarm();

        Button button = findViewById(R.id.positiveThoughtEnabler);
        button.setOnClickListener(e->enablePositiveThoughts());
    }

    //Resets page back to user saved settings.
    private void resetPageToSave() {
        Switch thoughtEnabler = findViewById(R.id.positiveThoughtEnabler);
        thoughtEnabler.setChecked(isSwitchFlipped);
    }

    //Source https://www.youtube.com/watch?v=xSrVWFCtgaE
    //All credit goes to android fox. Repurposing their alarm clock for the creation
    //of a notification manager that displays poisitive messages.
    private void enablePositiveThoughts() {
        isSwitchFlipped = !isSwitchFlipped;
        if(isSwitchFlipped){
            Calendar calendar = Calendar.getInstance();
            //moves it a minute in the future.
            calendar.setTimeInMillis(System.currentTimeMillis()+60000);
            startThoughtNotifications(calendar);
        }else{
            cancelNotification();
        }

    }
    //Source https://www.youtube.com/watch?v=xSrVWFCtgaE
    //All credit goes to android fox. Repurposing their starting alarm notification function for the usage
    //in a notification manager than gives positive affirmations to the user.
    private void startThoughtNotifications(Calendar calendar) {
        mainAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, MyReceiverThoughtNotification.class);
        pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        mainAlarm.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 1000,
                pendingIntent);
    }

    //Source https://www.youtube.com/watch?v=xSrVWFCtgaE
    //All credit goes to android fox.
    //Repurposing their cancel alarm for cancelling notifications.
    private void cancelNotification(){
        Intent intent = new Intent(this,MyReceiverThoughtNotification.class);

        pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        if(mainAlarm == null){
            mainAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        mainAlarm.cancel(pendingIntent);
        Toast.makeText(this, "Positive Affirmations Cancelled", Toast.LENGTH_SHORT).show();
    }

    //https://www.youtube.com/watch?v=xSrVWFCtgaE
    //All credit goes to androidfox. Repurposing this creation of notification for alarms to display positive
    //affirmations for the user.
    private void createNotificationForAlarm(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = "baadsstressreliefChannel";
            String description = "Channel For Positive Affirmations";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Positive Thoughts",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}