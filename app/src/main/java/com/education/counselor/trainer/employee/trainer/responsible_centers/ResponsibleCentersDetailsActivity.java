package com.education.counselor.trainer.employee.trainer.responsible_centers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.trainer.extra_buttons.DailyReportActivity;
import com.education.counselor.trainer.employee.trainer.meeting.AuthorityMeetActivity;
import com.education.counselor.trainer.employee.trainer.meeting.HodMeetActivity;
import com.education.counselor.trainer.employee.trainer.meeting.RankethonMeetActivity;
import com.education.counselor.trainer.employee.trainer.meeting.StudentMeetActivity;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ResponsibleCentersDetailsActivity extends AppCompatActivity {
    String n = "";
    Button authority_meet, hod_meet, rankethon_meet, student_meet, daily_report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsible_centers_details2);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        authority_meet = findViewById(R.id.authority_meet);
        authority_meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getBaseContext(), AuthorityMeetActivity.class);
                in.putExtra("name", n);
                startActivity(in);
            }
        });
        hod_meet = findViewById(R.id.hod_meet);
        hod_meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getBaseContext(), HodMeetActivity.class);
                in.putExtra("name", n);
                startActivity(in);
            }
        });
        rankethon_meet = findViewById(R.id.rankethon_meet);
        rankethon_meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getBaseContext(), RankethonMeetActivity.class);
                in.putExtra("name", n);
                startActivity(in);
            }
        });
        student_meet = findViewById(R.id.student_meet);
        student_meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getBaseContext(), StudentMeetActivity.class);
                in.putExtra("name", n);
                startActivity(in);
            }
        });
        daily_report = findViewById(R.id.daily_report);
        daily_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getBaseContext(), DailyReportActivity.class);
                in.putExtra("name", n);
                startActivity(in);
            }
        });
    }

    String access, email;
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
                        case "trainer":
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