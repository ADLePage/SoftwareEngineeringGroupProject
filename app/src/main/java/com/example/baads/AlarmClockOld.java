/*
package com.example.baads;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//https://developer.android.com/reference/android/widget/Switch
//Referencing the android studio widget definition for how to interact with widget.

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.baads.databinding.FragmentAlarmclockBinding;

public class AlarmClockOld extends Fragment {
    private FragmentAlarmclockBinding binding;

    AlarmManager mainAlarm;
    PendingIntent mainIntent;

    public static String hour1;
    public static String minute1;
    public static String hour2;
    public static String minute2;
    public static String hour3;
    public static String minute3;
    public static String hour4;
    public static String minute4;
    public static String hour5;
    public static String minute5;

    public static boolean time1Flipped = false;
    public static boolean time2Flipped = false;
    public static boolean time3Flipped = false;
    public static boolean time4Flipped = false;
    public static boolean time5Flipped = false;

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

    private void setValuesBackToUserInput(){
        binding.AlarmSwitch1.setChecked(time1Flipped);
        binding.AlarmSwitch2.setChecked(time2Flipped);
        binding.AlarmSwitch3.setChecked(time3Flipped);
        binding.AlarmSwitch4.setChecked(time4Flipped);
        binding.AlarmSwitch5.setChecked(time5Flipped);

        binding.AlarmTimeInputHour1.setText(hour1);
        binding.AlarmTimeInputHour2.setText(hour2);
        binding.AlarmTimeInputHour3.setText(hour3);
        binding.AlarmTimeInputHour4.setText(hour4);
        binding.AlarmTimeInputHour5.setText(hour5);

        binding.AlarmTimeInputMinute1.setText(minute1);
        binding.AlarmTimeInputMinute2.setText(minute2);
        binding.AlarmTimeInputMinute3.setText(minute3);
        binding.AlarmTimeInputMinute4.setText(minute4);
        binding.AlarmTimeInputMinute5.setText(minute5);
    }



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentAlarmclockBinding.inflate(inflater, container, false);

        //Reset Test for error generator.
        binding.tester.setText("");

        //Resets back to values user set.
        setValuesBackToUserInput();

        binding.AlarmSwitch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time1Flipped = !time1Flipped;
                if(time1Flipped) {
                    //Checks if input in the first alarm is correct.
                    //Takes in hours in first parameter, and minutes in second
                    if(isCorrectSyntax(binding.AlarmTimeInputHour1.getText().toString()
                            ,binding.AlarmTimeInputMinute1.getText().toString())) {
                        binding.tester.setText("");
                        //https://stackoverflow.com/questions/4531396/get-value-of-a-edit-text-field
                        //credit to svdree. Could not figure out how to get textvalues, on this stack overflow
                        //They tell how to get them from inputs.
                        hour1 = binding.AlarmTimeInputHour1.getText().toString();
                        minute1 = binding.AlarmTimeInputMinute1.getText().toString();
                        int hour = Integer.parseInt(hour1);
                        int minute = Integer.parseInt(minute1);

                        //long returnLong = (Long.parseLong(hour1) * 3600000) + (Long.parseLong(minute1) * 60000);
                        //binding.AlarmTimeInputHour2.setText(String.valueOf(returnLong));

                    }else{
                        binding.tester.setText("Error, input valid time");
                        time1Flipped = false;
                        binding.AlarmSwitch1.setChecked(time1Flipped);
                        binding.AlarmTimeInputHour1.setText("");
                        binding.AlarmTimeInputMinute1.setText("");
                    }
                }
            }
        });
        binding.AlarmSwitch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.AlarmSwitch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.AlarmSwitch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.AlarmSwitch5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}*/