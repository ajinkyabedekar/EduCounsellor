package com.education.counselor.trainer.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.admin.AdminDashboardActivity;
import com.education.counselor.trainer.authority.college.AuthorityCollegeDashboardActivity;
import com.education.counselor.trainer.authority.school.AuthoritySchoolDashboardActivity;
import com.education.counselor.trainer.counsellor.CounsellorDashboardActivity;
import com.education.counselor.trainer.student.StudentDashboardActivity;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity {
    String email, access;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ((ImageView) findViewById(R.id.iv)).setImageResource(R.mipmap.ic_logo);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference();
        if (user != null) {
            email = user.getEmail();
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1 : ds.getChildren()) {
                            if (Objects.equals(dataSnapshot1.child("mail").getValue(String.class), email)) {
                                access = ds.getKey();
                            }
                        }
                    }
                    if (access != null) {
                        switch (access) {
                            case "admin":
                                startActivity(new Intent(getBaseContext(), AdminDashboardActivity.class));
                                break;
                            case "college_authority":
                                startActivity(new Intent(getBaseContext(), AuthorityCollegeDashboardActivity.class));
                                break;
                            case "school_authority":
                                startActivity(new Intent(getBaseContext(), AuthoritySchoolDashboardActivity.class));
                                break;
                            case "counsellor":
                                startActivity(new Intent(getBaseContext(), CounsellorDashboardActivity.class));
                                break;
                            case "student":
                                startActivity(new Intent(getBaseContext(), StudentDashboardActivity.class));
                                break;
                            default:
                                Toast.makeText(getBaseContext(), "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((ImageView) findViewById(R.id.iv)).setImageResource(R.mipmap.ic_logo);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                    }, 1000);
                }
            }, 1000);
        }
    }
}