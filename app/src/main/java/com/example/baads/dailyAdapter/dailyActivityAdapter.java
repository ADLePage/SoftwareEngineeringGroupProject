package com.example.baads.dailyAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baads.addNewActivity;
import com.example.baads.dailyActivity;
import com.example.baads.dailyModel.dailyactivityModel;
import com.example.baads.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class dailyActivityAdapter extends RecyclerView.Adapter<dailyActivityAdapter.MyViewHolder> {

    private List<dailyactivityModel> bands;
    private dailyActivity activity;
    private FirebaseFirestore firestore;

    public dailyActivityAdapter(dailyActivity dailyactivity , List<dailyactivityModel> todoList){
        this.bands = bands;
        activity = dailyactivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dailyyy_do , parent , false);
        firestore = FirebaseFirestore.getInstance();

        return new MyViewHolder(view);
    }

    public void deleteTask(int position){
        dailyactivityModel dailyactivitymodel = bands.get(position);
        firestore.collection("daily activity").document(dailyactivitymodel.dailyID).delete();
        bands.remove(position);
        notifyItemRemoved(position);
    }
    public Context getContext(){
        return activity;
    }
    public void editTask(int position){
        dailyactivityModel dailyactivitymodel = bands.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("daily activity" , dailyactivitymodel.getdailyActivity() );
        bundle.putString("due" ,dailyactivitymodel.getDue());
        bundle.putString("id" , dailyactivitymodel.dailyID);

       addNewActivity addnewactivtiy = new addNewActivity();
        addnewactivtiy.setArguments(bundle);
        addnewactivtiy.show(activity.getSupportFragmentManager() , addnewactivtiy.getTag());
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        dailyactivityModel Dailyactivitymodel = bands.get(position);
        holder.mCheckBox.setText(Dailyactivitymodel.getdailyActivity());

        holder.mDueDateTv.setText("Due On" +Dailyactivitymodel.getDue());

        holder.mCheckBox.setChecked(toBoolean(Dailyactivitymodel.getStatus()));

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    firestore.collection("daily activity").document(Dailyactivitymodel.dailyID).update("status" , 1);
                }else{
                    firestore.collection("daily activity").document(Dailyactivitymodel.dailyID).update("status" , 0);
                }
            }
        });

    }

    private boolean toBoolean(int status){

        return status != 0;
    }

    @Override
    public int getItemCount() {
        return bands.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mDueDateTv;
        CheckBox mCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mDueDateTv = itemView.findViewById(R.id.due_date_tv);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);

        }
    }
}