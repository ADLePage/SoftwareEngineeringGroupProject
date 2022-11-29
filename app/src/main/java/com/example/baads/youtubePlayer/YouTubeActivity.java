package com.example.baads.youtubePlayer;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.baads.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.security.KeyPairGenerator;


public class YouTubeActivity extends YouTubeBaseActivity {

    Button btn;
    Button btnChangeVideo;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayerView youTubePlayerView2;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayer.OnInitializedListener onInitializedListener2;
    YouTubePlayer youTubePlayer;
    YouTubePlayer youTubePlayer2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);

        final String[] videoId = {"lrhPTqholcc"};

        btn = findViewById(R.id.play);
        btnChangeVideo = findViewById(R.id.changeVideo);
        youTubePlayerView = findViewById(R.id.YoutubePlayerView);
       // youTubePlayerView2 = findViewById(R.id.YoutubePlayerView2);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoId[0]);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize("AIzaSyALdZO1D-c0JsQ4pDMlHzLHsfN8CPRFKAI", onInitializedListener);
            }

        });
        //8TuRYV71Rgo

        btnChangeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                videoId[0] = "8TuRYV71Rgo";

                recreate();

            }
        });

        }


    }

