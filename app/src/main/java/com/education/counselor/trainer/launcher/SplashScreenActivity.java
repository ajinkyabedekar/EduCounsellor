package com.education.counselor.trainer.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.admin.AdminDashboardActivity;
import com.education.counselor.trainer.authority.college.AuthorityCollegeDashboardActivity;
import com.education.counselor.trainer.authority.school.AuthoritySchoolDashboardActivity;
import com.education.counselor.trainer.employee.counsellor.CounsellorDashboardActivity;
import com.education.counselor.trainer.employee.trainer.TrainerDashboardActivity;
import com.education.counselor.trainer.student.StudentDashboardActivity;
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
    boolean flag = false;
    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ((ImageView) findViewById(R.id.iv)).setImageResource(R.mipmap.ic_logo);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference();
        checkUser(user);
    }
    private void checkUser(final FirebaseUser user) {
        if (user != null) {
            email = user.getEmail();
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
                            case "admin":
                                startActivity(new Intent(getBaseContext(), AdminDashboardActivity.class));
                                finishAffinity();
                                break;
                            case "college_authority":
                                startActivity(new Intent(getBaseContext(), AuthorityCollegeDashboardActivity.class));
                                finishAffinity();
                                break;
                            case "school_authority":
                                startActivity(new Intent(getBaseContext(), AuthoritySchoolDashboardActivity.class));
                                finishAffinity();
                                break;
                            case "counsellor":
                                startActivity(new Intent(getBaseContext(), CounsellorDashboardActivity.class));
                                finishAffinity();
                                break;
                            case "trainer":
                                startActivity(new Intent(getBaseContext(), TrainerDashboardActivity.class));
                                finishAffinity();
                                break;
                            case "student":
                                startActivity(new Intent(getBaseContext(), StudentDashboardActivity.class));
                                finishAffinity();
                                break;
                            default:
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finishAffinity();
                                break;
                        }
                    } else {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finishAffinity();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finishAffinity();
        }
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}