package com.example.baads.youtubePlayer;
  /* Copyright [2022] [Aidan LePage, Alex Case]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.*/
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.baads.R;
import com.example.baads.databinding.FragmentFirst4Binding;
import com.example.baads.mainFiles.MainActivity;
import com.example.baads.mainFiles.usernameStorage;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class YouTubeActivity extends YouTubeFailureRecoveryActivity {
    //Sourced from http://developers.google.com/youtube/android/player/
    //Downloaded from https://developers.google.com/static/youtube/android/player/downloads/YouTubeAndroidPlayerApi-1.2.2.zip
    private FragmentFirst4Binding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_first4);

        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize("AIzaSyALdZO1D-c0JsQ4pDMlHzLHsfN8CPRFKAI", this);

        findViewById(R.id.back_button).setOnClickListener(e->onOptionsItemSelected());

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo("lrhPTqholcc");
        }
    }

    //Sourced https://stackoverflow.com/questions/14545139/android-back-button-in-the-title-bar
    //Oh my goodness this is one of the weirdest things i've done
    //Creates a new intent for the mainactivity, starts it.
    //Flips a boolean in the usernameStorage.
    //When the login page is created, checks for this variable first.
    //if this is flipped, go to main page and flip the login variable again.
    //User never knows that they were are the login page and put back at the main page.
    public void onOptionsItemSelected(){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        usernameStorage.loggedIn = true;
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }

}
