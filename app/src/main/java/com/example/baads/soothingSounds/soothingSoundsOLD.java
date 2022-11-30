package com.example.baads.soothingSounds;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;

import com.example.baads.R;

public class soothingSoundsOLD extends AppCompatActivity {
    MediaPlayer oceanNoise;
    MediaPlayer whiteNoise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soothing_sounds);

        oceanNoise = MediaPlayer.create(this, R.raw.ocean_noise); //both mp3 files were pulled from https://mc2method.org/white-noise/
        whiteNoise = MediaPlayer.create(this, R.raw.white_noise);
    }

    public void playOceanNoise(View view) {
        oceanNoise.start();
    }

    public void pauseOceanNoise(View view) {
        oceanNoise.pause();
    }

    public void playWhiteNoise(View view) {
        whiteNoise.start();
    }

    public void pauseWhiteNoise(View view) {
        whiteNoise.pause();
    }

    }
