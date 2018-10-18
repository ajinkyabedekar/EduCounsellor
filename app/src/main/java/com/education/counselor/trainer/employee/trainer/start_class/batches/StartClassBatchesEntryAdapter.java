package com.education.counselor.trainer.employee.trainer.start_class.batches;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.trainer.TrainerDashboardActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class StartClassBatchesEntryAdapter extends RecyclerView.Adapter<StartClassBatches> {
    private Context c;
    private ArrayList<StartClassBatchesEntryVo> details;
    private String center_key, course_key, batch_key = "", name = "abc";
    private DatabaseReference reference;

    StartClassBatchesEntryAdapter(Context c, ArrayList<StartClassBatchesEntryVo> details, String center_key, String course_key) {
        setHasStableIds(true);
        this.c = c;
        this.details = details;
        this.center_key = center_key;
        this.course_key = course_key;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public StartClassBatches onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_start_class_batches_adapter, viewGroup, false);
        return new StartClassBatches(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final StartClassBatches holder, @SuppressLint("RecyclerView") final int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.time.setText(details.get(i).getTime());
        reference = FirebaseDatabase.getInstance().getReference("centers").child(center_key).child("courses").child(course_key).child("batches");
        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = details.get(i).getName();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (Objects.equals(ds.child("name").getValue(String.class), name)) {
                                batch_key = ds.getKey();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(c, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reference.child(batch_key).child("Status").setValue("Started");
                        Toast.makeText(c, "Class Started", Toast.LENGTH_SHORT).show();
                        holder.start.setEnabled(false);
                        holder.start.setVisibility(View.GONE);
                        holder.stop.setEnabled(true);
                        holder.stop.setVisibility(View.VISIBLE);
                    }
                }, 1000);
            }
        });
        holder.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (Objects.equals(ds.child("name").getValue(String.class), name)) {
                                batch_key = ds.getKey();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(c, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reference.child(batch_key).child("Status").setValue("Completed");
                        reference = FirebaseDatabase.getInstance().getReference("ratings").push().child("name");
                        reference.setValue("Center : " + center_key + "\tCourse : " + course_key + "\tBatch : " + batch_key);
                        Toast.makeText(c, "Class Completed", Toast.LENGTH_SHORT).show();
                        c.startActivity(new Intent(c, TrainerDashboardActivity.class));
                    }
                }, 1000);
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}