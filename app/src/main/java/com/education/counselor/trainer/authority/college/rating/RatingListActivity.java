package com.education.counselor.trainer.authority.college.rating;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.authority.college.AuthorityCollegeDashboardActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
public class RatingListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference db;
    RatingListEntryAdapter adapter;
    ProgressBar pg;
    Context mContext;
    String[] key = new String[5];
    float currentRating;
    private ArrayList<RatingListEntryVo> details = new ArrayList<>();
    DatabaseReference save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_list);
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
                    key[0] = ds.getKey();
                    for (DataSnapshot sd : ds.getChildren()) {
                        key[1] = sd.getKey();
                        for (DataSnapshot d : sd.getChildren()) {
                            key[2] = d.getKey();
                            for (DataSnapshot s : d.getChildren()) {
                                key[3] = s.getKey();
                                for (DataSnapshot dataSnapshot1 : s.getChildren()) {
                                    key[4] = dataSnapshot1.getKey();
                                    if (Objects.equals(dataSnapshot1.child("Status").getValue(String.class), "Completed")) {
                                        RatingListEntryVo entryVo = new RatingListEntryVo();
                                        currentRating = dataSnapshot1.child("rating").getValue(Float.class);
                                        entryVo.setName(dataSnapshot1.child("name").getValue(String.class));
                                        entryVo.setRating(currentRating);
                                        details.add(entryVo);
                                    }
                                }
                            }
                        }
                    }
                }
                if (details.size() == 0) {
                    Toast.makeText(getBaseContext(), "No Classes Found", Toast.LENGTH_SHORT).show();
                }
                adapter = new RatingListEntryAdapter(mContext, details);
                pg.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        save = FirebaseDatabase.getInstance().getReference("centers/" + key[0] + "/" + key[1] + "/" + key[2] + "/" + key[3] + "/" + key[4]);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if (currentRating != adapter.getRating()) {
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    save.child("rating").setValue(adapter.getRating());
                    Toast.makeText(mContext, "Changes saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), AuthorityCollegeDashboardActivity.class));
                    finishAffinity();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getBaseContext(), AuthorityCollegeDashboardActivity.class));
                    finishAffinity();
                }
            });
            alertDialogBuilder.setTitle("Save Changes?");
            alertDialogBuilder.create().show();
        } else {
            startActivity(new Intent(getBaseContext(), AuthorityCollegeDashboardActivity.class));
            finishAffinity();
        }
    }
}