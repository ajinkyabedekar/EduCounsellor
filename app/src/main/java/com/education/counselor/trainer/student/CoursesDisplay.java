package com.education.counselor.trainer.student;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.education.counselor.trainer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CoursesDisplay extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference db;
    private ArrayList<courses> data=new ArrayList<>();
    private courses c;
    private courseAdapter adapter;
    private Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_display);
        Intent i=getIntent();
        final String status =i.getStringExtra("status");
        mcontext=this;
        recyclerView=findViewById(R.id.courseRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseDatabase.getInstance().getReference("courses");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    if(ds.child("details").child("status").getValue().equals(status))
                    {
                        data.add(new courses(ds.getKey(),ds.child("details").child("name").getValue().toString()));
                    }
                }
                adapter=new courseAdapter(mcontext,data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
