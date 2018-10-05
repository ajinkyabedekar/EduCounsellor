package com.education.counselor.trainer.counsellor;

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

public class InternshipListActivity extends AppCompatActivity {
    Button add_internship;
    RecyclerView recyclerView;
    DatabaseReference db;
    EditText name;
    InternshipListEntryAdapter adapter;
    ProgressBar pg;
    Context mContext;
    private ArrayList<InternshipListEntryVo> details = new ArrayList<>();
    private String n = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internship_list4);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        add_internship = findViewById(R.id.add_internship);
        add_internship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), AddInternshipActivity.class);
                in.putExtra("name", n);
                startActivity(in);
            }
        });
        mContext = this;
        pg = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseDatabase.getInstance().getReference("internships");
        pg.setVisibility(View.VISIBLE);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("center").getValue(String.class), n)) {
                        InternshipListEntryVo s = new InternshipListEntryVo();
                        s.setName(Objects.requireNonNull(ds.child("name").getValue()).toString());
                        details.add(s);
                    }
                }
                if (details.size() == 0) {
                    Toast.makeText(getBaseContext(), "No Internships Found", Toast.LENGTH_SHORT).show();
                }
                adapter = new InternshipListEntryAdapter(mContext, details);
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