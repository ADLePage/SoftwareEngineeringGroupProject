package com.example.baads.dailyModel;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class dailyID {
    @Exclude
    public String dailyID;

    public  <T extends  dailyID> T withId(@NonNull final String id){
        this.dailyID = id;
        return  (T) this;
    }

}
