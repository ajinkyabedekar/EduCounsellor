package com.education.counselor.trainer.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

public class CoursesListActivity extends AppCompatActivity {
    Button add_course;
    RecyclerView recyclerView;
    DatabaseReference db;
    EditText name;
    CoursesListEntryAdapter adapter;
    ProgressBar pg;
    Context mContext;
    private ArrayList<CoursesListEntryVo> details = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_list);
        add_course = findViewById(R.id.add_course);
        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddCoursesActivity.class));
            }
        });
        mContext = this;
        pg = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseDatabase.getInstance().getReference("courses");
        pg.setVisibility(View.VISIBLE);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CoursesListEntryVo s = new CoursesListEntryVo();
                    s.setName(Objects.requireNonNull(ds.child("name").getValue()).toString());
                    s.setPhone(Objects.requireNonNull(ds.child("mobile_number").getValue()).toString());
                    details.add(s);
                }
                if (details.size() == 0) {
                    Toast.makeText(getBaseContext(), "No Courses Found", Toast.LENGTH_SHORT).show();
                }
                adapter = new CoursesListEntryAdapter(mContext, details);
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