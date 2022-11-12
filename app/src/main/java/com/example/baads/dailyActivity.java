package com.example.baads;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baads.databinding.ActivityDailyBinding;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.PrivateKey;
import java.text.DateFormat;
import java.util.Date;

public class dailyActivity extends AppCompatActivity {

   private Toolbar toolbar;
   private RecyclerView recyclerView;
   private FloatingActionButton floatingActionButton;
    private String task;
    private String description;
   private DatabaseReference reference;
   private String key  = "";



    private ProgressDialog loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_daily);
 toolbar= findViewById(R.id.dailytoolbar);
 setSupportActionBar(toolbar);
 getSupportActionBar().setTitle("your daily activities  :)");
recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        loader = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference().child("today activity ");


        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });


    }
    private void addTask() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myview = inflater.inflate(R.layout.input_file, null);
        dialog.setView(myview);
        AlertDialog dialog1 = dialog.create();
        dialog1.setCancelable(false);
        final EditText task =myview.findViewById(R.id.task);
        final EditText description = myview.findViewById(R.id.des);
        Button save = myview.findViewById(R.id.saveButton2);
        Button cancel =myview.findViewById(R.id.cancelButton2);
        save.setOnClickListener((v) -> {
            String myTask = task.getText().toString().trim();
            String myDescription = description.getText().toString().trim();
            String id = reference.getKey();
            String date = DateFormat.getDateInstance().format(new Date());
            if (TextUtils.isEmpty(myTask)) {
                task.setError("Task Required");
                return;
            }
            if (TextUtils.isEmpty(myDescription)) {
                description.setError("Description Required");
                return;
            } else {
                loader.setMessage("putting in your data ");
              //  loader.setCanceledOnTouchOutside(false);
              //  loader.show();
                Model model = new Model(myTask, myDescription, id, date);
                reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      if (task.isSuccessful()) {
                          Toast.makeText(dailyActivity.this, "Daily activity has been inserted successfully", Toast.LENGTH_SHORT).show();
                      }
                      else
                      {
                          String error = task.getException().toString();
                          Toast.makeText(dailyActivity.this, "daily activity" + error, Toast.LENGTH_SHORT).show();
                         loader.dismiss();
                      }
                    }
                });
            }

            dialog1.dismiss();
        });
        dialog1.show();

    }


}