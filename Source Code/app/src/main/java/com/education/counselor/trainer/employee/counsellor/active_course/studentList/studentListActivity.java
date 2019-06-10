package com.education.counselor.trainer.employee.counsellor.active_course.studentList;

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
public class studentListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference db;
    studentEntryAdapter adapter;
    ProgressBar pg;
    Context mContext;
    private ArrayList<studentListEntryVo> details = new ArrayList<>();
    private String n = "", p = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_counsellor_students);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        if (i.hasExtra("pname")) {
            p = i.getStringExtra("pname");
        }
        mContext = this;
        pg = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseDatabase.getInstance().getReference("student");
        pg.setVisibility(View.VISIBLE);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                details.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("project_details").child("name").getValue(String.class), p) &&
                            Objects.equals(ds.child("centre").getValue(String.class), n)) {
                        studentListEntryVo s = new studentListEntryVo();
                        s.setName(Objects.requireNonNull(ds.child("name")).getValue(String.class));
                        s.setPhone(ds.child("mobile_number").getValue(String.class));
                        details.add(s);
                    }
                }
                if (details.size() == 0)
                    Toast.makeText(getBaseContext(), "No Students Found ", Toast.LENGTH_SHORT).show();
                adapter = new studentEntryAdapter(mContext, details);
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