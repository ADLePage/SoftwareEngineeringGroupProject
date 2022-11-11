package com.example.baads;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baads.databinding.FragmentAlarmclockBinding;

import java.util.Calendar;
//@Author Aidan LePage

//This file deals with creating functionality for the widgets on the alarm page,
//functionality for the alarm system, and alot of management of that page.
//Alarm clock sound used.
//Sound used: https://freesound.org/people/joedeshon/sounds/78562/
//Creative license: https://creativecommons.org/licenses/by/4.0/

//Credit to java2s, source http://www.java2s.com/example/java-api/android/app/notificationchannel/setsound-2-0.html
//Helpful in figuring out how to set up setSound for notificationChannel.
//Used their AttributeSounds format.

//Huge credit to Foxandroid, source https://www.youtube.com/watch?v=xSrVWFCtgaE.
//Much of the code regarding the alarm system and broadcasting is theirs and give total credit to them.
//Needed their code to create a notification system along with an alarm system.
public class AlarmClockOLD extends AppCompatActivity {
    private FragmentAlarmclockBinding binding;

    //Source https://www.youtube.com/watch?v=xSrVWFCtgaE
    //All credit goes to Foxandroid
    //Foxandroids private variables we need for the alarm system to work.
    private AlarmManager mainAlarm;
    private PendingIntent pendingIntent;
    //End of sourced code.
    private NotificationChannel channel;
    private NotificationManager notificationManager;


    public static String hour1;
    public static String minute1;
    public static boolean time1Flipped = false;
    public boolean isCorrectSyntax(String hour, String minute){
        if(hour.isEmpty()||minute.isEmpty()){
            return false;
        }
        if((Integer.parseInt(hour)>=0)
                &&(Integer.parseInt(hour)<=23)
                &&(Integer.parseInt(minute)>=0)
                &&(Integer.parseInt(minute)<=59)){
            return true;
        }
        return false;
    }
    //If loading page again, reload back user inputted values.
    private void setValuesBackToUserInput(){
        Switch alarmSwitch = findViewById(R.id.AlarmSwitch1);
        TextView textViewHour = findViewById(R.id.AlarmTimeInputHour1);
        TextView textViewMinute = findViewById(R.id.AlarmTimeInputMinute1);
        alarmSwitch.setChecked(time1Flipped);
        textViewHour.setText(hour1);
        textViewMinute.setText(minute1);
    }
    //Source https://www.youtube.com/watch?v=xSrVWFCtgaE
    //All credit goes to Foxandroid. Need for alarm to work.
    //This starts up the alarm and primes the broadcast to be able to tell user to wake up.
    private void startAlarmClock(Calendar calendar){
        mainAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        mainAlarm.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 1000,
                pendingIntent);
    }
    //Source https://www.youtube.com/watch?v=xSrVWFCtgaE
    //All credit goes to Foxandroid.
    //Needed to figure out how to do a cancel on the notification. Credit to Foxandroid again
    //This part creates an intent that is used to broadcast to the user using the MyReciever class
    //that they need to wake up.
    private void cancelAlarm(){
        Intent intent = new Intent(this,MyReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        if(mainAlarm == null){
            mainAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        notificationManager.deleteNotificationChannel("Alarm System");
        mainAlarm.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }
    private void doAlarmFunction(){
            time1Flipped = !time1Flipped;
            if(time1Flipped) {
                //Checks if input in the first alarm is correct.
                //Takes in hours in first parameter, and minutes in second
                //Source https://abhiandroid.com/ui/edittext
                //Been a while since i've done android studio,
                //needed to figure out how to manipulate variables.
                TextView textViewHour = findViewById(R.id.AlarmTimeInputHour1);
                TextView textViewMinute = findViewById(R.id.AlarmTimeInputMinute1);
                //https://stackoverflow.com/questions/4531396/get-value-of-a-edit-text-field
                //credit to svdree. Could not figure out how to get textvalues, on this stack overflow
                //They tell how to get them from inputs.
                if(isCorrectSyntax(
                        textViewHour.getText().toString(),
                        textViewMinute.getText().toString())) {
                    //Once again https://www.youtube.com/watch?v=xSrVWFCtgaE
                    //All credit goes to Foxandroid. All this is theirs.
                    //I needed code to be able to make a notification for the android alarm system.
                    createNotificationForAlarm();

                    String hour = textViewHour.getText().toString();
                    String minute = textViewMinute.getText().toString();
                    hour1 = hour;
                    minute1 = minute;

                    //Source https://www.youtube.com/watch?v=xSrVWFCtgaE
                    //All credit goes to Foxandroid. Could not figure out how to do the
                    //alarm function but thanks to them and their code I was able to implement. This part creates a calendar object
                    //That then gets set with user input.
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(textViewHour.getText().toString()));
                    calendar.set(Calendar.MINUTE,Integer.parseInt(textViewMinute.getText().toString()));
                    calendar.set(Calendar.SECOND,0);
                    calendar.set(Calendar.MILLISECOND,0);
                    //Toast.makeText(this, "Calendar Time:" + calendar.getTimeInMillis() + "\n" + "Actual Time:" + System.currentTimeMillis(), Toast.LENGTH_LONG).show();

                    //In the case the user wants to set an alarm for tomorrow.
                    if(calendar.getTimeInMillis()<System.currentTimeMillis()){
                        calendar.setTimeInMillis(calendar.getTimeInMillis()+(24*60*60*1000));
                    }

                    //This part sends the calendar to the event handler.

                    startAlarmClock(calendar);


                    //Testing Code
                    //TextView textview = findViewById(R.id.AlarmTimeInputHour2);
                    //textview.setText("Calculated:"+ calendar.getTimeInMillis());
                    //TextView textview2 = findViewById(R.id.AlarmTimeInputMinute2);
                    //textview2.setText("System value:"+ System.currentTimeMillis());
                }else{
                    Toast.makeText(this, "Error, input valid time", Toast.LENGTH_SHORT).show();
                    time1Flipped = false;
                    Switch alarmSwitch = findViewById(R.id.AlarmSwitch1);
                    alarmSwitch.setChecked(time1Flipped);
                    textViewHour.setText("");
                    textViewMinute.setText("");
                }
            }else{
                cancelAlarm();
            }
    }
    //https://www.youtube.com/watch?v=xSrVWFCtgaE
    //All credit goes to Foxandroid  (unless stated for java2s). needed to figure out how to create a notification channel
    //request for the alarm system. This builds and creates an object that will be used as a notification for the user to wake up.
    private void createNotificationForAlarm(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = "baadsstressreliefChannel";
            String description = "Channel For Alarm";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            //Source: http://www.java2s.com/example/java-api/android/app/notificationchannel/setsound-2-0.html
            //AudioAttribute creator, sourcing java2s's AudioAttribute creation
            //in order to be able to create an audioattribute to attach to an alarmsound.
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            channel = new NotificationChannel("Alarm System",name,importance);
            channel.setDescription(description);

            //Alarm clock sound used.
            //Sound used: https://freesound.org/people/joedeshon/sounds/78562/
            //Creative license: https://creativecommons.org/licenses/by/4.0/

            //Source: http://www.java2s.com/example/java-api/android/app/notificationchannel/setsound-2-0.html
            //Format for Uri creation and setting sound for channel. Needed to be able to set the sound
            Uri alarmSound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm_sound);
            channel.setSound(alarmSound,audioAttributes);
            //End of source.

            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentAlarmclockBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setContentView(R.layout.fragment_alarmclock);
        setValuesBackToUserInput();

        //Setting button function.
        Button button = findViewById(R.id.AlarmSwitch1);
        button.setOnClickListener(e->doAlarmFunction());
        }
    }