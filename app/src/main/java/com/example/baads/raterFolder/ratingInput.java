package com.example.baads.raterFolder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.baads.databinding.FragmentFirst5Binding;
import com.example.baads.mainFiles.usernameStorage;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ratingInput extends Fragment {

    private FragmentFirst5Binding binding;
    private FirebaseFirestore databaseLoginInfoConnection;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirst5Binding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int userInput = 0;
        String date = "";
        Calendar calendar = Calendar.getInstance();
        Date actualTime = calendar.getTime();
        date = actualTime.toString();
        
        databaseLoginInfoConnection = FirebaseFirestore.getInstance();

        Map<String, Object> data1 = new HashMap<>();
//All you need to do is swap out"descriptive sentence" and "task" for their respective variables
        data1.put("Rating", userInput);


        databaseLoginInfoConnection.collection("users")
                .document(usernameStorage.username)
                .collection("stressRating")
                .document(date)
                .set(data1);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}