package com.example.baads;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.baads.databinding.FragmentYoutubeplayerBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.baads.databinding.ActivityVideoBinding;

public class Video extends AppCompatActivity {

    Context context = this;

    private AppBarConfiguration appBarConfiguration;
    private ActivityVideoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);


        VideoView videoView = findViewById(R.id.relaxing_video);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.relaxing_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController( context );
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

    }
    @Override
    public boolean onSupportNavigateUp () {
        NavController navController = Navigation.findNavController(this, R.id.action_FirstFragment_to_Video);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}