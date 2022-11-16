package com.example.baads;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;


import com.example.baads.dailyAdapter.dailyActivityAdapter;
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


public class dailyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private String task;
    private String description;
    private DatabaseReference reference;
    private String key = "";
    private FirebaseFirestore firestore;
    private dailyActivityAdapter adapter;
    private List<dailyactivityModel> mlist;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_daily);
        toolbar = findViewById(R.id.dailytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("your daily activities  :)");
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.fab);
        firestore = FirebaseFirestore.getInstance();


        //  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //  linearLayoutManager.setReverseLayout(true);
        //   linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        // recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(dailyActivity.this));
        //  loader = new ProgressDialog(this);
        //  reference = Firebase
        //  reference = FirebaseDatabase.getInstance().getReference().child("today activity ");


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewActivity.newInstance().show(getSupportFragmentManager(), addNewActivity.TAG);
            }
        });
        mlist = new ArrayList<>();
        adapter = new dailyActivityAdapter(dailyActivity.this, mlist);
        recyclerView.setAdapter(adapter);
        showData();
    }

    private void showData() {
     firestore.collection("daily activity").addSnapshotListener(new EventListener<QuerySnapshot>() {
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

  /*
    private void addTask() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myview = inflater.inflate(R.layout.input_file, null);
        dialog.setView(myview);
        AlertDialog dialog1 = dialog.create();
        dialog1.setCancelable(false);
        final EditText task = myview.findViewById(R.id.task);
        final EditText description = myview.findViewById(R.id.des);
        Button save = myview.findViewById(R.id.saveButton2);
        Button cancel = myview.findViewById(R.id.cancelButton2);
        save.setOnClickListener((v) -> {
            String myTask = task.getText().toString().trim();
            String myDescription = description.getText().toString().trim();
            String id = reference.getKey();
            String date = DateFormat.getDateInstance().format(new Date());
            if (TextUtils.isEmpty(myTask)) {
                task.setError("Task Required");
                return;
            }
        });
    }

   */
}