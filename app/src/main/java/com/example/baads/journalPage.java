package com.example.baads;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baads.databinding.ActivityJournalPageBinding;


public class journalPage extends AppCompatActivity {
     Button newButton,saveButton,openButton;
     EditText editText;
     private ActivityJournalPageBinding binding;

     public void saveButtonFunction(){
         final EditText fileName = new EditText(this);
         AlertDialog.Builder ad = new AlertDialog.Builder(this);
         ad.setView(fileName);
         try {
             FileOutputStream fout = openFileOutput(fileName.getText().toString() + ".txt", MODE_WORLD_READABLE);
             fout.write(editText.getText().toString().getBytes());
         } catch (Exception e) {
             Toast.makeText(getApplicationContext(), "Error Occured:" + e, Toast.LENGTH_LONG).show();
         }
     }


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_journal_page);

        newButton = (Button) findViewById(R.id.newButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        openButton = (Button) findViewById(R.id.openButton);
        editText = (EditText) findViewById(R.id.text);

        saveButton.setOnClickListener(e->saveButtonFunction());
        saveButton.setText("Save Button");
        //saveButton.
    }


/*
    public void buttonAction(View v) {
        final EditText fileName = new EditText(this);
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setView(fileName);
        if (v.getId() == R.id.saveButton) {
            ad.setMessage("SAVE TODAY'S JOURNAL");
            ad.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        FileOutputStream fout = openFileOutput(fileName.getText().toString() + ".txt", MODE_WORLD_READABLE);
                        fout.write(editText.getText().toString().getBytes());
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error Occured:" + e, Toast.LENGTH_LONG).show();
                    }
                }
            });
            ad.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            ad.show();
        }
             if (v.getId()== R.id.openButton) {
                 ad.setMessage("OPEN THIS JOURNAL");
                 ad.setPositiveButton("OPEN", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         int j;
                         editText.setText("");
                         try {
                             FileInputStream fin = openFileInput(fileName.getText().toString()+".txt");
                             while (( j = fin.read()) !=-1) {
                                 editText.setText((editText.getText().toString() + Character.toString((char) j)));
                             }

                         }catch (Exception e) {
                             Toast.makeText(getApplicationContext(), "Error Occured: " +e, Toast.LENGTH_LONG).show();

                         }
                     }
                 });
                 ad.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.cancel();
                     }
                 });

                 ad.show();
             }
            if(v.getId()==R.id.newButton) {
                editText.setText(" ");
        }
    }
*/
    }