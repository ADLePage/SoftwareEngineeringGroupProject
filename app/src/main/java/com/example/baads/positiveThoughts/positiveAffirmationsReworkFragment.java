package com.example.baads.positiveThoughts;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.baads.R;
import com.example.baads.databinding.ActivityPositiveThoughtsBinding;

import java.util.Calendar;


public class positiveAffirmationsReworkFragment extends Fragment {

    private ActivityPositiveThoughtsBinding binding;
    private boolean isSwitchFlipped = false;
    private AlarmManager mainAlarm;
    private PendingIntent pendingIntent;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ActivityPositiveThoughtsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    //Resets page back to user saved settings.
    private void resetPageToSave() {
        Switch thoughtEnabler = getActivity().findViewById(R.id.positiveThoughtEnabler);
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
        mainAlarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getActivity().getApplication(),MyReceiverThoughtNotification.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity().getApplication(),1,intent,0);
        mainAlarm.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 1000,
                pendingIntent);
    }

    //Source https://www.youtube.com/watch?v=xSrVWFCtgaE
    //All credit goes to android fox.
    //Repurposing their cancel alarm for cancelling notifications.
    private void cancelNotification(){
        Intent intent = new Intent(getActivity().getApplication(),MyReceiverThoughtNotification.class);

        pendingIntent = PendingIntent.getBroadcast(getActivity().getApplication(),1,intent,0);

        if(mainAlarm == null){
            mainAlarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }

        mainAlarm.cancel(pendingIntent);
        Toast.makeText(getActivity().getApplication(), "Positive Affirmations Cancelled", Toast.LENGTH_SHORT).show();
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

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resetPageToSave();

        createNotificationForAlarm();

        Button button = getActivity().findViewById(R.id.positiveThoughtEnabler);
        button.setOnClickListener(e->enablePositiveThoughts());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}