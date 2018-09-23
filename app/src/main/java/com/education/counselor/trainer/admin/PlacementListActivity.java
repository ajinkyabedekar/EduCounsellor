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

public class PlacementListActivity extends AppCompatActivity {
    Button add_placement;
    RecyclerView recyclerView;
    DatabaseReference db;
    EditText name;
    PlacementListEntryAdapter adapter;
    ProgressBar pg;
    Context mContext;
    private ArrayList<PlacementListEntryVo> details = new ArrayList<>();
    private String n = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_list);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        add_placement = findViewById(R.id.add_placement);
        add_placement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getBaseContext(),AddPlacementActivity.class);
                in.putExtra("name",n);
                startActivity(in);
            }
        });
        mContext = this;
        pg = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseDatabase.getInstance().getReference("placements");
        pg.setVisibility(View.VISIBLE);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("center").getValue(String.class), n)) {
                        PlacementListEntryVo s = new PlacementListEntryVo();
                        s.setName(Objects.requireNonNull(ds.child("name").getValue()).toString());
                        details.add(s);
                    }
                }
                if (details.size() == 0) {
                    Toast.makeText(getBaseContext(), "No Placement Found", Toast.LENGTH_SHORT).show();
                }
                adapter = new PlacementListEntryAdapter(mContext, details);
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