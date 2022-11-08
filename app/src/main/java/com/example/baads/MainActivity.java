package com.example.baads;

import static android.content.ContentValues.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.baads.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.Menu;
import android.view.MenuItem;
//Source: https://creativecommons.org/publicdomain/zero/1.0/
//Source: https://freesound.org/people/Provan9/sounds/345684/
//Used sound for Admin Notifications
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private NotificationManager notificationManager;

    //Source: https://developer.android.com/develop/ui/views/notifications/build-notification
    //Sourced from the android developer notification.
    //Used this in order to create admin notifications.
    private void createAdminNotificationChannel(String value) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Admin Channel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Admin Notification")
                .setContentText(value)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(value))
                .setTimeoutAfter(15000)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Admin Notification";
            String description = "Admin Notification Channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Admin Channel", name, importance);
            channel.setDescription(description);

            //Source: http://www.java2s.com/example/java-api/android/app/notificationchannel/setsound-2-0.html
            //AudioAttribute creator, sourcing java2s's AudioAttribute creation
            //in order to be able to create an audioattribute to attach to an alarmsound.
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            //Source: https://creativecommons.org/publicdomain/zero/1.0/
            //Source: https://freesound.org/people/Provan9/sounds/345684/
            //Used sound for Admin Notifications

            //Source: http://www.java2s.com/example/java-api/android/app/notificationchannel/setsound-2-0.html
            //Format for Uri creation and setting sound for channel. Needed to be able to set the sound
            Uri alarmSound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.admin_alert);
            channel.setSound(alarmSound,audioAttributes);

            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(100, builder.build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Taken from android studio tutorial within the app itself.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(value!=null) {
                    createAdminNotificationChannel(value);
                }
                value = null;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}