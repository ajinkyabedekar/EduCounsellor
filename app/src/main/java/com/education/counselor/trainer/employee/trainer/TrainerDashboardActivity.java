package com.education.counselor.trainer.employee.trainer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.counsellor.courseWiseStudents.CoursewiseStudentList;
import com.education.counselor.trainer.employee.trainer.attendance.AttendanceActivity;
import com.education.counselor.trainer.employee.trainer.extra_buttons.DailyReportActivity;
import com.education.counselor.trainer.employee.trainer.extra_buttons.media_update.MediaUpdateActivity;
import com.education.counselor.trainer.employee.trainer.extra_buttons.success_video_and_review.SuccessVideoAndReviewActivity;
import com.education.counselor.trainer.employee.trainer.live_chat.chatChoice;
import com.education.counselor.trainer.employee.trainer.responsible_centers.list.ResponsibleCentersListActivity;
import com.education.counselor.trainer.employee.trainer.start_class.centers.StartClassCentersActivity;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class TrainerDashboardActivity extends AppCompatActivity {
    Button my_account, start_class, attendance, responsible_centers, daily_report, logout, coursewise, chat, media_update, success_video;
    String access, email;
    boolean flag = false;
    private boolean pressed = false;

    @Override
    public void onBackPressed() {
        if (pressed) {
            finishAffinity();
        }
        this.pressed = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                pressed = false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_dashboard);
        my_account = findViewById(R.id.my_account);
        my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MyAccountActivity.class));
                finishAffinity();
            }
        });
        start_class = findViewById(R.id.start_class);
        start_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), StartClassCentersActivity.class));
                finishAffinity();
            }
        });
        attendance = findViewById(R.id.attendance);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AttendanceActivity.class));
                finishAffinity();
            }
        });
        responsible_centers = findViewById(R.id.responsible_centers);
        responsible_centers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ResponsibleCentersListActivity.class));
                finishAffinity();
            }
        });
        daily_report = findViewById(R.id.daily_report);
        daily_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), DailyReportActivity.class));
                finishAffinity();
            }
        });
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finishAffinity();
            }
        });
        coursewise = findViewById(R.id.coursewise);
        coursewise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CoursewiseStudentList.class));
                finishAffinity();
            }
        });
        chat = findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), chatChoice.class));
                finishAffinity();
            }
        });
        media_update = findViewById(R.id.media_update);
        media_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MediaUpdateActivity.class));
                finishAffinity();
            }
        });
        success_video = findViewById(R.id.success_video);
        success_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SuccessVideoAndReviewActivity.class));
                finishAffinity();
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