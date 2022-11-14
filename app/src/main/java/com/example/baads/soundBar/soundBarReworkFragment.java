package com.example.baads.soundBar;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;


import com.example.baads.R;
import com.example.baads.databinding.ActivitySoundBarBinding;


public class soundBarReworkFragment extends Fragment {

    private ActivitySoundBarBinding binding;
    MediaPlayer oceanNoise;
    MediaPlayer whiteNoise;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ActivitySoundBarBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        oceanNoise = MediaPlayer.create(getActivity(), R.raw.ocean_noise); //both mp3 files were pulled from https://mc2method.org/white-noise/
        whiteNoise = MediaPlayer.create(getActivity(), R.raw.white_noise);

        AppCompatImageButton oceanButtonPlay = getActivity().findViewById(R.id.playOcean);
        oceanButtonPlay.setOnClickListener(e->oceanNoise.start());

        AppCompatImageButton oceanButtonPause = getActivity().findViewById(R.id.pauseOcean);
        oceanButtonPause.setOnClickListener(e->oceanNoise.stop());

        AppCompatImageButton whiteNoisePlay = getActivity().findViewById(R.id.playWhite);
        whiteNoisePlay.setOnClickListener(e->whiteNoise.start());

        AppCompatImageButton whiteNoisePause = getActivity().findViewById(R.id.pauseWhite);
        whiteNoisePause.setOnClickListener(e->whiteNoise.stop());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}