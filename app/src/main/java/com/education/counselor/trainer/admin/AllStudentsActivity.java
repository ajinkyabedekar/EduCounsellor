package com.education.counselor.trainer.admin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllStudentsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference db;

    @Override
    protected void onStart() {
        super.onStart();
        pg.setVisibility(View.VISIBLE);
    }

    EditText name,phone;
    studentAdapter adapter;
    ProgressBar pg;
    private ArrayList<studentDetails> details=new ArrayList<>();
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);
    mContext=this;
    pg=findViewById(R.id.progress);
    recyclerView=(RecyclerView)findViewById(R.id.recycle);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    db= FirebaseDatabase.getInstance().getReference("student");
    pg.setVisibility(View.VISIBLE);
    db.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot ds:dataSnapshot.getChildren())
            {
                studentDetails s=new studentDetails();
                s.setName(ds.child("name").getValue().toString());
                s.setPhone(ds.child("mobile_number").getValue().toString());
                details.add(s);
            }
            adapter=new studentAdapter(mContext,details);
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