package com.example.baads.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.baads.R;
import com.example.baads.databinding.FragmentAlarmclockBinding;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
//@Author Aidan LePage

//https://stackoverflow.com/questions/42211527/getpackagename-in-fragment
//Helped with getting packagename in fragment.

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

/*
This class deals with the alarm function.
On click of the switch within the alarm fragment, it will
    Check whether or not the inputted data is correct.
    If correct, then it will submit it to a calendar object.
    That calendar object is checked whether or not the date is for today or tomorrow.
    Send it to a receiver
    Also builds a notification manager
        when the alarm sounds, it will play a notification and an alarm noise.
If the switch is turned off, it will delete
    the notification
    and try to stop the alarm noise.
 */
public class alarmActivityReworkFragment extends Fragment {

    private FragmentAlarmclockBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAlarmclockBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setValuesBackToUserInput();
        binding.AlarmSwitch1.setOnClickListener(e->doAlarmFunction());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Source https://www.youtube.com/watch?v=xSrVWFCtgaE
    //All credit goes to Foxandroid
    //Foxandroids private variables mainAlarm and pendingIntent that we need for the alarm system to work.
    private AlarmManager mainAlarm;
    private PendingIntent pendingIntent;

    private NotificationChannel channel;
    private NotificationManager notificationManager;

    public static String hour1;
    public static String minute1;
    public static boolean time1Flipped = false;

    //Checks whether or not the user input correct data.
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
        Switch alarmSwitch = getActivity().findViewById(R.id.AlarmSwitch1);
        TextView textViewHour = getActivity().findViewById(R.id.AlarmTimeInputHour1);
        TextView textViewMinute = getActivity().findViewById(R.id.AlarmTimeInputMinute1);
        alarmSwitch.setChecked(time1Flipped);
        textViewHour.setText(hour1);
        textViewMinute.setText(minute1);
    }
    //Source https://www.youtube.com/watch?v=xSrVWFCtgaE
    //All credit goes to Foxandroid. Need for alarm to work.
    //This starts up the alarm and primes the broadcast to be able to tell user to wake up.
    private void startAlarmClock(Calendar calendar){
        mainAlarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getActivity(), MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);
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
        Intent intent = new Intent(getActivity(),MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);

        if(mainAlarm == null){
            mainAlarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }
        notificationManager.deleteNotificationChannel("Alarm System");
        mainAlarm.cancel(pendingIntent);
        try {
            //This is the media player within the receiver. Due to some weird interaction I need to instead
            //have the sound play through the media player.
            MyReceiver.alarmSounder.stop();
        }catch(NullPointerException e){
            //This try catch is in-case the media player is not playing.
        }

        Toast.makeText(getActivity(), "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }
    private void doAlarmFunction(){
        time1Flipped = !time1Flipped;
        if(time1Flipped) {
            //Checks if input in the first alarm is correct.
            //Takes in hours in first parameter, and minutes in second
            //Source https://abhiandroid.com/ui/edittext
            //Been a while since i've done android studio,
            //needed to figure out how to manipulate variables.
            TextView textViewHour = getActivity().findViewById(R.id.AlarmTimeInputHour1);
            TextView textViewMinute = getActivity().findViewById(R.id.AlarmTimeInputMinute1);
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
                //Toast.makeText(getActivity().this, "Calendar Time:" + calendar.getTimeInMillis() + "\n" + "Actual Time:" + System.currentTimeMillis(), Toast.LENGTH_LONG).getActivity().show();
                Date date = calendar.getTime();

                //In the case the user wants to set an alarm for tomorrow.
                if(calendar.getTimeInMillis()<System.currentTimeMillis()){
                    calendar.setTimeInMillis(calendar.getTimeInMillis()+(24*60*60*1000));
                    Toast.makeText(getActivity().getApplication(), "Alarm set for tomorrow "+date.getHours()+":"+date.getMinutes(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity().getApplication(), "Alarm set for today "+date.getHours()+":"+date.getMinutes(), Toast.LENGTH_SHORT).show();
                }
                //This part sends the calendar to the event handler.
                startAlarmClock(calendar);
            }else{
                Toast.makeText(getActivity(), "Error, input valid time", Toast.LENGTH_SHORT).show();
                time1Flipped = false;
                Switch alarmSwitch = getActivity().findViewById(R.id.AlarmSwitch1);
                alarmSwitch.setChecked(time1Flipped);
                textViewHour.setText("");
                textViewMinute.setText("");
            }
        }else{
            //If you're switching the alarm off, stop the alarm.
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


            channel = new NotificationChannel("Alarm System",name,importance);
            channel.setDescription(description);

            notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}