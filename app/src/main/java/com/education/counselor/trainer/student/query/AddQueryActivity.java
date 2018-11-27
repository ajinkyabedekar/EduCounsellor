package com.education.counselor.trainer.student.query;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Objects;
public class AddQueryActivity extends AppCompatActivity {
    EditText query;
    Button submit;
    DatabaseReference db;
    FirebaseUser user;
    String email, key;
    String access;
    int temp = 0;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_query);
        query = findViewById(R.id.query);
        submit = findViewById(R.id.submit);
        db = FirebaseDatabase.getInstance().getReference("student");
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }
        db.addListenerForSingleValueEvent(new ValueEventListener() {
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
                if (query.getText().toString().equals("")) {
                    query.requestFocus();
                    query.setError("This Is A Required Field");
                } else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("student").child(key).child("query").push();
                    reference.child("query").setValue(query.getText().toString());
                    reference.child("status").setValue("PENDING");
                    Toast.makeText(getBaseContext(), "Query Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), StudentDashboardActivity.class));
                    finishAffinity();
                }
            }
        });
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