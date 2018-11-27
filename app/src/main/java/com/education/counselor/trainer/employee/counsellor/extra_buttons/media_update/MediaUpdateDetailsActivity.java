package com.education.counselor.trainer.employee.counsellor.extra_buttons.media_update;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.counsellor.CounsellorDashboardActivity;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
public class MediaUpdateDetailsActivity extends AppCompatActivity {
    EditText description, media;
    Button submit, delete;
    DatabaseReference reference;
    FirebaseUser user;
    String email, key, name, key2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_update_details);
        description = findViewById(R.id.description);
        media = findViewById(R.id.media);
        submit = findViewById(R.id.submit);
        delete = findViewById(R.id.delete);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }
        Intent intent = getIntent();
        if (intent.hasExtra("name")) {
            name = intent.getStringExtra("name");
        }
        reference = FirebaseDatabase.getInstance().getReference("counsellor");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("mail").getValue(String.class), email)) {
                        key = ds.getKey();
                        for (DataSnapshot d : ds.child("media_events").getChildren()) {
                            if (Objects.equals(d.child("media_event_link").getValue(String.class), name)) {
                                key2 = d.getKey();
                                description.setText(d.child("media_event_description").getValue(String.class));
                                media.setText(d.child("media_event_link").getValue(String.class));
                            }
                        }
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
                    reference = reference.child(key).child("media_events").child(key2);
                    reference.child("media_event_description").setValue(description.getText().toString());
                    reference.child("media_event_link").setValue(media.getText().toString());
                    Toast.makeText(getBaseContext(), "Media Event Updated Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), CounsellorDashboardActivity.class));
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(key).child("media_events").child(key2).removeValue();
                Toast.makeText(MediaUpdateDetailsActivity.this, "Media Event Deleted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), CounsellorDashboardActivity.class));
            }
        });
    }

    String access;
    boolean flag = false;

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
        db.addValueEventListener(new ValueEventListener() {
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
                        case "counsellor":
                            return;
                        default:
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getBaseContext(), LoginActivity.class));
                            finishAffinity();
                            Toast.makeText(getBaseContext(), "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Please Check Your Network Connection and Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}