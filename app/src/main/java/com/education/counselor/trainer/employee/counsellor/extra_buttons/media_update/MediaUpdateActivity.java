package com.education.counselor.trainer.employee.counsellor.extra_buttons.media_update;

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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.counsellor.CounsellorDashboardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
public class MediaUpdateActivity extends AppCompatActivity {
    EditText description, media;
    Button submit;
    DatabaseReference reference;
    FirebaseUser user;
    String email, key;
    RecyclerView recyclerView;
    MediaUpdateEntryAdapter adapter;
    ProgressBar pg;
    Context mContext;
    private ArrayList<MediaUpdateEntryVo> details = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_update);
        description = findViewById(R.id.description);
        media = findViewById(R.id.media);
        submit = findViewById(R.id.submit);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }
        reference = FirebaseDatabase.getInstance().getReference("counsellor");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("mail").getValue(String.class), email)) {
                        key = ds.getKey();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (description.getText().toString().equals("")) {
                    description.requestFocus();
                    description.setError("This Is A Required Field");
                } else if (media.getText().toString().equals("")) {
                    media.requestFocus();
                    media.setError("This Is A Required Field");
                } else {
                    reference = reference.push();
                    reference.child("media_event_description").setValue(description.getText().toString());
                    reference.child("media_event_link").setValue(media.getText().toString());
                    Toast.makeText(getBaseContext(), "Media Event Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), CounsellorDashboardActivity.class));
                }
            }
        });
        mContext = this;
        pg = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reference = reference.child(key).child("media_events");
                pg.setVisibility(View.VISIBLE);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            MediaUpdateEntryVo s = new MediaUpdateEntryVo();
                            s.setName(ds.child("media_event_link").getValue(String.class));
                            details.add(s);
                        }
                        if (details.size() == 0) {
                            Toast.makeText(getBaseContext(), "No Media Events Found", Toast.LENGTH_SHORT).show();
                        }
                        adapter = new MediaUpdateEntryAdapter(mContext, details);
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
}