package com.example.baads.selfCareList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.baads.R;
import com.example.baads.databinding.ActivitySelfCareListBinding;
import com.example.baads.mainFiles.LoginFragment;
import com.example.baads.mainFiles.usernameStorage;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class selfCareListReworkedFragment extends Fragment {

    private FirebaseFirestore databaseLoginInfoConnection;
    private ActivitySelfCareListBinding binding;
    //DocumentReference savedIdeasRef = db.collection("Practice mindfulness").document("SavedIdeasList");

    String tips[]
            = { "Practice mindfulness", "Take a break",
            "Play video games", "Listen to music",
            "Read a book", "Listen to a podcast",
            "Reflect on things you are grateful for",
            "Eat a healthy meal", "Engage in exercise",
            "Go for a walk", "Drink water", "Practice good sleep hygiene",
            "Call/text a friend", "Connect with nature",
            "Meditate"};


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ActivitySelfCareListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseLoginInfoConnection = FirebaseFirestore.getInstance();

        ArrayAdapter<String> tasks = new ArrayAdapter<String>(getActivity(),
                R.layout.selfcarelist_item_view, R.id.itemTextView,
                tips);


        ListView listTasks = (ListView) getActivity().findViewById(R.id.listView);
        listTasks.setAdapter(tasks);
/*
        binding.listView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Map<String, Object> data1 = new HashMap<>();

                data1.put("idea1", "Practice mindfulness");
                databaseLoginInfoConnection.collection("users")
                        .document(usernameStorage.username)
                        .collection("SavedIdeas")
                        .document("SavedIdeasList")
                        .set(data1);
            }
        });
       //*/


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}