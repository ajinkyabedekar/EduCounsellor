package com.education.counselor.trainer.admin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
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

public class StartupCentersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference db;
    EditText name;
    StartupCentersEntryAdapter adapter;
    ProgressBar pg;
    Context mContext;
    private ArrayList<StartupCentersEntryVo> details = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        pg.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_centers);
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
                    StartupCentersEntryVo s = new StartupCentersEntryVo();
                    s.setName(Objects.requireNonNull(ds.child("name").getValue()).toString());
                    details.add(s);
                }
                adapter = new StartupCentersEntryAdapter(mContext, details);
                pg.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);
                Toast.makeText(mContext, adapter.getItemCount() + " centers", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}