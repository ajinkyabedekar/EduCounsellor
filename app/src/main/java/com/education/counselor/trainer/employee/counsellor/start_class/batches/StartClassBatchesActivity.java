package com.education.counselor.trainer.employee.counsellor.start_class.batches;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class StartClassBatchesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference db;
    StartClassBatchesEntryAdapter adapter;
    ProgressBar pg;
    Context mContext;
    String course = "", center = "", center_key, course_key;
    private ArrayList<StartClassBatchesEntryVo> details = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_class_batches);
        Intent i = getIntent();
        if (i.hasExtra("course")) {
            course = i.getStringExtra("course");
        }
        if (i.hasExtra("center")) {
            center = i.getStringExtra("center");
        }
        mContext = this;
        pg = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseDatabase.getInstance().getReference("centers");
        pg.setVisibility(View.VISIBLE);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("name").getValue(String.class), center)) {
                        center_key = ds.getKey();
                        for (DataSnapshot d : ds.child("courses").getChildren()) {
                            if (Objects.equals(d.child("name").getValue(String.class), course)) {
                                course_key = d.getKey();
                                for (DataSnapshot sd : d.child("batches").getChildren()) {
                                    StartClassBatchesEntryVo s = new StartClassBatchesEntryVo();
                                    s.setName(sd.child("name").getValue(String.class));
                                    s.setTime(sd.child("time").getValue(String.class));
                                    details.add(s);
                                }
                            }
                        }
                    }
                }
                if (details.size() == 0) {
                    Toast.makeText(getBaseContext(), "No Batches Found", Toast.LENGTH_SHORT).show();
                }
                adapter = new StartClassBatchesEntryAdapter(mContext, details, center_key, course_key);
                pg.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}