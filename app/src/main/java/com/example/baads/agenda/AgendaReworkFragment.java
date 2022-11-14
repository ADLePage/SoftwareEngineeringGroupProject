package com.example.baads.agenda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.baads.R;
import com.example.baads.databinding.ActivityAgendaBinding;

public class AgendaReworkFragment extends Fragment {

    private ActivityAgendaBinding binding;

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
        //textView
        TextView EventText = getActivity().findViewById(R.id.EventListText);


        //button
        Button sendEvent = getActivity().findViewById(R.id.editEventText);
        sendEvent.setOnClickListener(e -> saveEvent());

        //textEdit
        EditText eventEditText = getActivity().findViewById(R.id.editEventText);

    }

    public void saveEvent() {
        TextView EventText = getActivity().findViewById(R.id.EventListText);
        EditText eventEditText = getActivity().findViewById(R.id.editEventText);

        event = eventEditText.getText().toString();
        EventText.setText("");
        EventText.append(event);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String event;
}