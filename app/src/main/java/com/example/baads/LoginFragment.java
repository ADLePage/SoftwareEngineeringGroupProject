package com.example.baads;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.baads.databinding.FragmentFirst2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginFragment extends Fragment {

    private FragmentFirst2Binding binding;
    private String username = "";
    private String password = "";
    private FirebaseFirestore databaseLoginInfoConnection;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirst2Binding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseLoginInfoConnection = FirebaseFirestore.getInstance();


        binding.skipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_FirstFragment);
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                username = String.valueOf(binding.username.getText());
                password = String.valueOf(binding.password.getText());
                if(!(username.isEmpty()||password.isEmpty())) {
                    //Sourced from https://firebase.google.com/docs/firestore/query-data/get-data?utm_source=studio
                    //Needed to be able to get a reference from the database.
                    //Sourced from the original firebase example.
                    DocumentReference docRef = databaseLoginInfoConnection.collection("users").document(username);
                    if(!(docRef.get().isSuccessful())){
                        binding.errorText.setText("ERROR: NO ACCOUNT TIED TO THAT USERNAME OR PASSWORD");
                    }
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    if(document.getData().containsValue(password)) {
                                        NavHostFragment.findNavController(LoginFragment.this)
                                                .navigate(R.id.action_loginFragment_to_FirstFragment);
                                    }
                                } else {
                                    binding.errorText.setText("ERROR: NO ACCOUNT TIED TO THAT USERNAME OR PASSWORD");
                                }
                            } else {
                                binding.errorText.setText("ERROR: NO ACCOUNT TIED TO THAT USERNAME OR PASSWORD");
                            }
                        }
                    });
                }
                else binding.errorText.setText("ERROR: NO ACCOUNT TIED TO THAT USERNAME OR PASSWORD");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}