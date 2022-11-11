package com.example.baads.journalPage;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baads.R;


public class journalPageOLD extends AppCompatActivity {
     Button newButton, saveButton,openButton;
     EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_page);



        openButton = (Button)findViewById(R.id.openButton);
        editText = (EditText)findViewById(R.id.text);
        newButton = (Button)findViewById(R.id.newButton);
        saveButton = (Button)findViewById(R.id.saveButton);
    }

    public void buttonAct(View wow) {
        final EditText fileName = new EditText(this);
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setView(fileName);

        if (wow.getId() == R.id.saveButton) {
            ad.setMessage("SAVE TODAY'S STRESS JOURNAL(00/00/0000)");
            ad.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        FileOutputStream fout = openFileOutput(fileName.getText().toString() + ".txt", MODE_PRIVATE);
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
        if (wow.getId() == R.id.openButton) {
            ad.setMessage("OPEN STRESS JOURNAL(00/00/0000)");
            ad.setPositiveButton("OPEN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int j;
                    editText.setText("");
                    try {
                        FileInputStream fin = openFileInput(fileName.getText().toString() + ".txt");
                        while ((j = fin.read()) != -1) {
                            editText.setText((editText.getText().toString() + Character.toString((char) j)));
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error Occured: " + e, Toast.LENGTH_LONG).show();

                    }
                }
            });
            ad.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            ad.show();
        }

        if (wow.getId() == R.id.newButton) {
            editText.setText("");
        }
    }
}