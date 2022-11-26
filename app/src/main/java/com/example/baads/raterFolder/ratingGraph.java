package com.example.baads.raterFolder;

import static android.content.ContentValues.TAG;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.baads.R;
import com.example.baads.databinding.FragmentSecondBinding;
import com.example.baads.mainFiles.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ratingGraph extends Fragment {

    private FragmentSecondBinding binding;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private TextView retrieveData;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("stressRating");

        retrieveData = getActivity().findViewById(R.id.idRetrieveData);

        getdata();
        
    }

    private <value> void getdata() {

        databaseReference.addValueEventListener(new ValueEventListener() {
        @Override

        public void onDataChange(@NonNull DataSnapshot snapshot) {

        String value = snapshot.getValue(String.class);

        retrieveData.setText(value);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCancelled(@NonNull DatabaseError error) {

           Toast.makeText(getApplicationContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
    }

    });

    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}