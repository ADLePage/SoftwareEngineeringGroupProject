package com.example.baads.dailyAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.baads.R;
import com.example.baads.dailyActivity;
import com.example.baads.dailyModel.dailyactivityModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class dailyactivityAdapter extends RecyclerView.Adapter<dailyactivityAdapter.MyViewHolder> {

    private List<dailyactivityModel> bands;
    private final dailyActivity activity;
    private FirebaseFirestore firestore;

    public dailyactivityAdapter(dailyActivity dactivity, List<dailyactivityModel> mlist) {
        this.bands = bands;
        activity = dactivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dailyyy_do, parent, false);
        firestore = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        dailyactivityModel dailyactivitymodel = bands.get(position);
        holder.checkbox1.setText(dailyactivitymodel.getdailyActivity());
        holder.duedate1.setText("DUE ON" + dailyactivitymodel.getDue());
        holder.checkbox1.setChecked(toBoolean(dailyactivitymodel.getStatus()));
        holder.checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    firestore.collection("Dailyactivity").document(dailyactivitymodel.dailyID).update("Status", 1);
                } else {
                    firestore.collection("Dailyactivity").document(dailyactivitymodel.dailyID).update("Status", 0);

                }
            }

        });
    }

    private boolean toBoolean(int status) {
        return status != 0;
    }

    @Override
    public int getItemCount() {

        return 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView duedate1;
        CheckBox checkbox1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            duedate1 = itemView.findViewById(R.id.datey);
            checkbox1 = itemView.findViewById(R.id.checky);

        }
    }
}
