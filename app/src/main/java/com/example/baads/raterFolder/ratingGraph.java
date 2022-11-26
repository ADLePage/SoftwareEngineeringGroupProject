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
import com.example.baads.addNewActivity;
import com.example.baads.databinding.FragmentSecondbindingBinding;
import com.example.baads.mainFiles.MainActivity;
import com.example.baads.mainFiles.usernameStorage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ratingGraph extends Fragment {

    private FragmentSecondbindingBinding binding;
    FirebaseFirestore firebaseDatabase;
    DatabaseReference databaseReference;
    private TextView retrieveDataText;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondbindingBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    //Sourced https://firebase.google.com/docs/firestore/query-data/get-data
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDatabase = FirebaseFirestore.getInstance();
        retrieveDataText = getActivity().findViewById(R.id.retrieveData);
        String[] result = new String[1];
        result[0] ="";
        firebaseDatabase.collection("users").document(usernameStorage.username).collection("stressRating").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                for (QueryDocumentSnapshot document : task.getResult()) {
                    result[0] += document.getData().get("Rating") + "\n";
                }
                retrieveDataText.setText(result[0]);
            } else {
                Log.d(addNewActivity.TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    private <value> void getdata() {

        databaseReference.addValueEventListener(new ValueEventListener() {
        @Override

        public void onDataChange(@NonNull DataSnapshot snapshot) {
        String value = snapshot.getValue(String.class);
        retrieveDataText.setText(value);
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