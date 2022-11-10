package com.example.baads;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.baads.databinding.FragmentMainpageBinding;

public class MainPage extends Fragment {

    private FragmentMainpageBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMainpageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ////Creates a binding to send user to the sound bar
        binding.positiveThoughts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainPage.this)
                        .navigate(R.id.action_FirstFragment_to_positiveThoughts2);
            }
        });
        ////Creates a binding to send user to the sound bar
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainPage.this)
                        .navigate(R.id.action_FirstFragment_to_soundBar);
            }
        });
        //Creates a binding to send user to the music player
        binding.SoothingMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainPage.this)
                        .navigate(R.id.action_FirstFragment_to_musicPlayer);
            }
        });
        //Creates a binding to send user to the youtube page
        binding.YouTubePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainPage.this)
                        .navigate(R.id.action_FirstFragment_to_youtubePlayer);
            }
        });

        //Creates a binding to send user to the testing page.
        binding.TestingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainPage.this)
                        .navigate(R.id.action_FirstFragment_to_journalPage);
            }
        });

        //Creates a binding to send user to the Alarm Clock
        binding.AlarmClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainPage.this)
                        .navigate(R.id.action_FirstFragment_to_alarmClock);
            }
        });

        binding.video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainPage.this)
                        .navigate(R.id.action_FirstFragment_to_Video);
            }
        });

        binding.wiki2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainPage.this)
                        .navigate(R.id.action_FirstFragment_to_wikiTips);
            }
        });

        binding.agenda.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                NavHostFragment.findNavController(MainPage.this)
                        .navigate(R.id.action_FirstFragment_to_agenda);
            }
        });


        binding.SelfCareList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainPage.this)
                        .navigate(R.id.action_FirstFragment_to_selfCareList);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}