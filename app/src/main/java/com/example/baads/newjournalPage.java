package com.example.baads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;


public class newjournalPage extends AppCompatActivity {
     EditText titleedittext, descriptionedittext;
     ImageButton saveButtonjournal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newjournal_page);
        titleedittext = findViewById(R.id.journaltitletext);
        descriptionedittext=findViewById(R.id.journaltitledescription);
        saveButtonjournal=findViewById(R.id.saveButtonjournal);

      saveButtonjournal.setOnClickListener((v) -> saveJournal());
    }
    void saveJournal() {
        String journalTitle = titleedittext.getText().toString();
        String journaldescription = descriptionedittext.getText().toString();
        if (journalTitle == null || journalTitle.isEmpty() ) {
            titleedittext.setError("you have to put a title");
            return;

        }
        Journal journal = new Journal();
        journal.setTitle(journalTitle);
        journal.setDescription(journaldescription);
        journal.setTimestamp(Timestamp.now());
        savejournalfirebase(journal);
    }
    void savejournalfirebase(Journal journal) {
        DocumentReference documentReference;
        documentReference = used.getCollectionReferencejournal().document();
        documentReference.set(journal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    used.showToast(newjournalPage.this, "Journal added successfully");
                    finish();

                } else {
                    used.showToast(newjournalPage.this, "sorry couldn't add your journal");
                }
            }
        });

    }

}