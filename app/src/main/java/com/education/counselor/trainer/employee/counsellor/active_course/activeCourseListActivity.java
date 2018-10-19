package com.education.counselor.trainer.employee.counsellor.active_course;

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

public class activeCourseListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference db;
    activeCourseListEntryAdapter adapter;
    ProgressBar pg;
    Context mContext;
    private ArrayList<activeCourseListEntryVo> details = new ArrayList<>();
    private String n = "", stat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_counsellor_courses);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        if (i.hasExtra("stat")) {
            stat = i.getStringExtra("stat");
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
                    if (Objects.equals(ds.child("name").getValue(String.class), n)) {
                        for (DataSnapshot d : ds.child("courses").getChildren()) {
                            if (Objects.equals(d.child("status").getValue(String.class), stat)) {
                                activeCourseListEntryVo s = new activeCourseListEntryVo();
                                s.setName(Objects.requireNonNull(d.child("name").getValue()).toString());
                                s.setPhone(n);
                                details.add(s);
                            }
                        }
                    }
                }
                if (details.size() == 0)
                    Toast.makeText(getBaseContext(), "No Active Courses Found", Toast.LENGTH_SHORT).show();

                adapter = new activeCourseListEntryAdapter(mContext, details);
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