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
    Button btn2;
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

        btn = findViewById(R.id.play);
        btn2 = findViewById(R.id.play2);
        youTubePlayerView = findViewById(R.id.YoutubePlayerView);
        youTubePlayerView2 = findViewById(R.id.YoutubePlayerView2);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("lrhPTqholcc");
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

        onInitializedListener2 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer2, boolean b) {
                youTubePlayer2.loadVideo("8TuRYV71Rgo");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView2.initialize("AIzaSyALdZO1D-c0JsQ4pDMlHzLHsfN8CPRFKAI", onInitializedListener);
            }

        });

    }

}