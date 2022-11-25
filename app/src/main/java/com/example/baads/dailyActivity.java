package com.example.baads;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;


import com.example.baads.dailyAdapter.dailyactivityAdapter;
import com.example.baads.dailyModel.dailyactivityModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class dailyActivity extends AppCompatActivity implements closeListener {


    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private FirebaseFirestore firestore;
    private dailyactivityAdapter adapter;
    private List<dailyactivityModel> mlist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_daily);

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.fab);
        firestore = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(dailyActivity.this));


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewActivity.newInstance().show(getSupportFragmentManager(), addNewActivity.TAG);
            }
        });
        mlist = new ArrayList<>();
        adapter = new dailyactivityAdapter(dailyActivity.this , mlist);
        recyclerView.setAdapter(adapter);
        showData();
    }

    private void showData() {
     firestore.collection("Dailyactivity").orderBy("time" , Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
         @Override
         public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
             for (DocumentChange documentChange : value.getDocumentChanges()) {
                 if (documentChange.getType() == DocumentChange.Type.ADDED) {
                     String id = documentChange.getDocument().getId();
                     dailyactivityModel dailyactivitymodel = documentChange.getDocument().toObject(dailyactivityModel.class).withId(id);
                    mlist.add(dailyactivitymodel);
                    adapter.notifyDataSetChanged();
                 }
             }
             Collections.reverse(mlist);

         }

     });
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
       mlist.clear();
        showData();
       adapter.notifyDataSetChanged();
    }


}