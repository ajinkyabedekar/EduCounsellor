package com.education.counselor.trainer.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.education.counselor.trainer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class CoursesDisplay extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference db, s;
    private ArrayList<courses> data = new ArrayList<>();
    private courseAdapter adapter;
    private Context mcontext;
    private String name, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_display);
        Intent i = getIntent();
        final String status = i.getStringExtra("status");
        mcontext = this;
        recyclerView = findViewById(R.id.courseRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        s = FirebaseDatabase.getInstance().getReference("student");
        s.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.requireNonNull(snapshot.child("mail").getValue()).toString().equalsIgnoreCase(email)) {
                        name = (String) snapshot.child("name").getValue();
                        id = snapshot.getKey();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        db = FirebaseDatabase.getInstance().getReference("courses");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("details").child("status").getValue(), status)) {
                        data.add(new courses(Objects.requireNonNull(ds.child("details").child("name").getValue()).toString(), ds.getKey()));
                    }
                }
                adapter = new courseAdapter(mcontext, data, name, id);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
