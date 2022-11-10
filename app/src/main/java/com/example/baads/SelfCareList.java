package com.example.baads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SelfCareList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_care_list);

    }
}
//  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);

        ////Creates a binding to send user to the sound bar
       /* binding.positiveThoughts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SelfCareList.this)
                        .navigate(R.id.action_FirstFragment_to_positiveThoughts2);
            }*/
      //  });

