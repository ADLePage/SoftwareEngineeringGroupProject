package com.example.baads.journalPage;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.baads.R;
import com.example.baads.databinding.ActivityJournalPageBinding;


import java.io.FileInputStream;
import java.io.FileOutputStream;

public class journalPageFragmentReworked extends Fragment {
    Button newButton, saveButton,openButton;
    EditText editText;
    private ActivityJournalPageBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ActivityJournalPageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void buttonAct(View wow) {
        final EditText fileName = new EditText(getActivity());
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        ad.setView(fileName);

        if (wow.getId() == R.id.saveButton) {
            ad.setMessage("SAVE TODAY'S STRESS JOURNAL(00/00/0000)");
            ad.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        FileOutputStream fout = getActivity().openFileOutput(fileName.getText().toString() + ".txt", MODE_PRIVATE);
                        fout.write(editText.getText().toString().getBytes());
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error Occured:" + e, Toast.LENGTH_LONG).show();
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
                        FileInputStream fin = getActivity().openFileInput(fileName.getText().toString() + ".txt");
                        while ((j = fin.read()) != -1) {
                            editText.setText((editText.getText().toString() + Character.toString((char) j)));
                        }

                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error Occured: " + e, Toast.LENGTH_LONG).show();

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

    private void saveButtonAction(){
        final EditText fileName = new EditText(getActivity());
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        ad.setView(fileName);
        ad.setMessage("SAVE TODAY'S STRESS JOURNAL(00/00/0000)");
        ad.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    FileOutputStream fout = getActivity().openFileOutput(fileName.getText().toString() + ".txt", MODE_PRIVATE);
                    fout.write(editText.getText().toString().getBytes());
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Error Occured:" + e, Toast.LENGTH_LONG).show();
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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        openButton = getActivity().findViewById(R.id.openButton);
        editText = getActivity().findViewById(R.id.text);
        newButton = getActivity().findViewById(R.id.newButton);
        saveButton = getActivity().findViewById(R.id.saveButton);

        saveButton.setOnClickListener(e->saveButtonAction());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}