/*
*************************************
*      Author:Yogesh Sharma         *
*************************************
  -------------------------------------------------------------------------------------------------
 |     Its a Placementlist activity where it is used to view the list of  all Placement   present  |
  --------------------------------------------------------------------------------------------------

*/
package com.education.counselor.trainer.student.placement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.education.counselor.trainer.student.StudentDashboardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    DatabaseReference db, ref;
    PlacementListEntryAdapter adapter;
    ProgressBar pg;
    Context mContext;
    private ArrayList<PlacementListEntryVo> details = new ArrayList<>();
    private String n = "";
    FirebaseUser user;
    String access, email;
    int temp = 0;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_list3);
        add_placement = findViewById(R.id.add_placement);
        add_placement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), AddPlacementActivity.class);
                in.putExtra("name", n);
                startActivity(in);
                finishAffinity();
            }
        });
        mContext = this;
        pg = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }
        ref = FirebaseDatabase.getInstance().getReference("student");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("mail").getValue(String.class), email)) {
                        n = ds.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                db = FirebaseDatabase.getInstance().getReference("placements");
                pg.setVisibility(View.VISIBLE);
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (Objects.equals(ds.getKey(), n)) {
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
        }, 1000);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            checkUser(user);
        }
    }

    private void checkUser(final FirebaseUser user) {
        email = user.getEmail();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : ds.getChildren()) {
                        if (Objects.equals(dataSnapshot1.child("mail").getValue(String.class), email)) {
                            access = ds.getKey();
                            flag = true;
                            break;
                        }
                    }
                    if (flag)
                        break;
                }
                if (access != null) {
                    switch (access) {
                        case "student":
                            return;
                        default:
                            temp = 1;
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if (temp == 1) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            finishAffinity();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), StudentDashboardActivity.class));
        finishAffinity();
    }
}
