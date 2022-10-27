package com.example.baads;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baads.databinding.FragmentAlarmclockBinding;

import java.util.Calendar;
//This file deals with creating functionality for the widgets on the alarm page,
//functionality for the alarm system,
//and alot of management of that page.
//Huge credit to Foxandroid, source https://www.youtube.com/watch?v=xSrVWFCtgaE.
//Much of the code regarding the alarm system and broadcasting is theirs and give total credit to them.
//Needed their code to create a notification system along with an alarm system.
public class AlarmClockNew extends AppCompatActivity {
    private FragmentAlarmclockBinding binding;

    //Source https://www.youtube.com/watch?v=xSrVWFCtgaE
    //All credit goes to android fox.
    //Fox androids private variables we need for the alarm system to work.
    private AlarmManager mainAlarm;
    private PendingIntent pendingIntent;
    //End of code.

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
        return true;
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
    //All credit goes to android fox. Need for alarm to work.
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
    //All credit goes to android fox.
    //Needed to figure out how to do a cancel on the notification. Credit to android fox again
    //This part creates an intent that is used to broadcast to the user using the MyReciever class
    //that they need to wake up.
    private void cancelAlarm(){
        Intent intent = new Intent(this,MyReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        if(mainAlarm == null){
            mainAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

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
                TextView textviewTester = findViewById(R.id.tester);
                //https://stackoverflow.com/questions/4531396/get-value-of-a-edit-text-field
                //credit to svdree. Could not figure out how to get textvalues, on this stack overflow
                //They tell how to get them from inputs.
                if(isCorrectSyntax(
                        textViewHour.getText().toString(),
                        textViewMinute.getText().toString())) {
                    String hour = textViewHour.getText().toString();
                    String minute = textViewMinute.getText().toString();
                    hour1 = hour;
                    minute1 = minute;

                    //Source https://www.youtube.com/watch?v=xSrVWFCtgaE
                    //All credit goes to android fox. Could not figure out how to do the
                    //alarm function but thanks to them and their code I was able to implement. This part creates a calendar object
                    //That then gets set with user input.
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(textViewHour.getText().toString()));
                    calendar.set(Calendar.MINUTE,Integer.parseInt(textViewMinute.getText().toString()));
                    calendar.set(Calendar.SECOND,0);
                    calendar.set(Calendar.MILLISECOND,0);

                    //This part sends the calendar to the event handler.
                    startAlarmClock(calendar);

                    TextView textview = findViewById(R.id.AlarmTimeInputHour2);
                    textview.setText("Calculated:"+ calendar.getTimeInMillis());

                    TextView textview2 = findViewById(R.id.AlarmTimeInputMinute2);
                    textview2.setText("System value:"+ System.currentTimeMillis());
                }else{
                    textviewTester.setText("Error, input valid time");
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
    //All credit goes to android fox. needed to figure out how to create a notification channel
    //request for the alarm system. This builds and creates an object that will be used as a notification for the user to wake up.
    private void createNotificationForAlarm(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = "baadsstressreliefChannel";
            String description = "Channel For Alarm";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Alarm System",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
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

        //Once again https://www.youtube.com/watch?v=xSrVWFCtgaE
        //All credit goes to android fox. All this is theirs.
        //I needed code to be able to make a notification for the android alarm system.
        createNotificationForAlarm();

        //Setting button function.
        Button button = findViewById(R.id.AlarmSwitch1);
        button.setOnClickListener(e->doAlarmFunction());
        }
    }