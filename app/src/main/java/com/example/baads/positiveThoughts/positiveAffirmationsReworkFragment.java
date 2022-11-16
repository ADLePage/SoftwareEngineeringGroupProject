package com.example.baads.positiveThoughts;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
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



public class positiveAffirmationsReworkFragment extends Fragment {

    private ActivityPositiveThoughtsBinding binding;
    public static boolean isSwitchFlipped = false;
    private positiveAffirmationsThread thread;

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

    //Source: https://www.tutorialspoint.com/how-to-create-a-thread-in-java
    //Source: https://www.youtube.com/watch?v=xSrVWFCtgaE
    //Credit for Foxandroid and tutorialspoint for how to create this. Combined both into a thread that
    //In the background will make notifications and sounds.
    private void buttonAction() throws InterruptedException {
        isSwitchFlipped = !isSwitchFlipped;
        if(isSwitchFlipped) {
            thread = new positiveAffirmationsThread(getActivity());
        }else {
            Toast.makeText(getActivity().getApplication(), "Positive Affirmations Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resetPageToSave();

        Button button = getActivity().findViewById(R.id.positiveThoughtEnabler);
        button.setOnClickListener(e-> {
            try {
                buttonAction();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}