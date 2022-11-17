package com.example.baads.agenda;

import static com.example.baads.addNewActivity.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.baads.R;
import com.example.baads.databinding.ActivityAgendaBinding;
import com.example.baads.mainFiles.usernameStorage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class AgendaReworkFragment extends Fragment {

    private ActivityAgendaBinding binding;
    private FirebaseFirestore databaseAgendaConnection;
    private String event;
    private String date;


    public AgendaReworkFragment() {
        date = null;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ActivityAgendaBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize connection to data base
        databaseAgendaConnection = FirebaseFirestore.getInstance();

        CollectionReference callref = databaseAgendaConnection
                .collection("users")
                .document(usernameStorage.username)
                .collection("Agenda");
        callref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });



        //button
        Button sendEvent = getActivity().findViewById(R.id.newEventButton);
        sendEvent.setOnClickListener(e -> saveEvent());
    }
    public void saveEvent() {
                //editText
                EditText eventEditText = getActivity().findViewById(R.id.editEventText);
                //stores user input in a string variable to be later added to database
                event = eventEditText.getText().toString();

                //textView
                TextView EventText = getActivity().findViewById(R.id.EventListText);
                EventText.setText(eventEditText.getText().toString());
                //gets the current time upon button press
                Calendar calendar = Calendar.getInstance();
                Date actualTime = calendar.getTime();
                date = actualTime.toString();

                Map<String, Object> data1 = new HashMap<>();
                data1.put("agendaEvent", event);

                databaseAgendaConnection.collection("users")
                        .document(usernameStorage.username)
                        .collection("Agenda")
                        .document(date)
                        .set(data1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}