package com.example.baads.positiveThoughts;


import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.baads.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

//Source: https://www.tutorialspoint.com/how-to-create-a-thread-in-java
//Trying to figure out how to implement positive affirmations to randomize messages given to user.
//Had an idea of running a thread and putting it to sleep for a duration so the user isn't bombarded with messages
//although I had no idea how threads worked in java.

//Sourced from https://firebase.google.com/docs/firestore/query-data/aggregation-queries
//Also sourced from //Sourced from https://firebase.google.com/docs/firestore/query-data/get-data?utm_source=studio
//Needed to source in order to be able to create queries

//Source: https://www.youtube.com/watch?v=xSrVWFCtgaE
//Used Foxandroids tutorial to create notification channels.

//Source: https://freesound.org/people/IENBA/sounds/545495/
//License: https://creativecommons.org/licenses/by/4.0/
//Used sound for Positive notifications

public class positiveAffirmationsThread implements Runnable {
    private String[] positiveThoughts = new String[]{
            "You are doing great!",
            "You are valued!",
            "Tough times end, tough people last."};

    Thread positiveAffirmations;
    Activity activityThread;
    private NotificationManager notificationManager;
    private MediaPlayer notificationSound;
    private FirebaseFirestore databaseLoginInfoConnection;
    private static int countVariable;
    positiveAffirmationsThread(Activity activity){
        activityThread = activity;
        databaseLoginInfoConnection = FirebaseFirestore.getInstance();
        positiveAffirmations = new Thread(this, "positiveAffirmation");
        positiveAffirmations.start();
    }
    @Override
    public void run() {
        //Source: http://www.java2s.com/example/java-api/android/app/notificationchannel/setsound-2-0.html
        //Format for Uri creation.
        Uri alarmSound = Uri.parse("android.resource://" + activityThread.getPackageName() + "/" + R.raw.positive_sound);
        MediaPlayer notificationSound = MediaPlayer.create(activityThread,alarmSound);
        DocumentReference docRef;

        while (positiveAffirmationsReworkFragment.isSwitchFlipped) {
            final String[] affirmation = {"Affirmation"};
            final String[] returnValue = new String[1];
            //initializing value INCASE for some reason it cannot connect to the database fast enough.
            returnValue[0]= "You've got this!";
            //https://stackoverflow.com/questions/40800288/math-random-generates-same-number
            //Math.random() was being weird, so this helps with a random number generator.
            //This is used to count how many affirmations we have in our database.

            CollectionReference collection = databaseLoginInfoConnection.collection("Affirmations");
            Query query = collection;
            AggregateQuery countingAffirmations = query.count();
            countingAffirmations.get(AggregateSource.SERVER).addOnCompleteListener(task ->{
                if(task.isSuccessful()){
                    AggregateQuerySnapshot snapshot = task.getResult();
                    countVariable = (int) snapshot.getCount();
                    //Due to how i'm using an array, I have to sub one from the count to traverse it.
                    countVariable= countVariable-1;

                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Random random = new Random();
            int randomValue = random.nextInt()%countVariable;
            randomValue = Math.abs(randomValue);
            affirmation[0] +=randomValue;

            //This gets a document reference to the affirmations collection. And pulls the value stored within to display as a notification.
            docRef = databaseLoginInfoConnection.collection("Affirmations").document(affirmation[0]);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    //If the task is complete, go into the if statement.
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            returnValue[0] = document.getData().values().toString();
                            //Gets rid of brackets
                            returnValue[0] = returnValue[0].replace("[","");
                            returnValue[0] = returnValue[0].replace("]","");
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
            //Needed to wait for the onComplete to complete and pull something
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Source: Foxandroid
            //Creates a notification channel.
            NotificationCompat.Builder builder = new NotificationCompat.Builder(activityThread, "Positive Thoughts")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Affirmation Notification")
                    .setContentText(returnValue[0])
                    .setTimeoutAfter(5000)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "Positive Notification";
                String description = "Affirmation Notification Channel";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel("Positive Thoughts", name, importance);
                channel.setDescription(description);

                //Source: https://freesound.org/people/IENBA/sounds/545495/
                //License: https://creativecommons.org/licenses/by/4.0/
                //Used sound for Positive notifications
                notificationSound.start();

                notificationManager = activityThread.getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activityThread);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(100, builder.build());
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
