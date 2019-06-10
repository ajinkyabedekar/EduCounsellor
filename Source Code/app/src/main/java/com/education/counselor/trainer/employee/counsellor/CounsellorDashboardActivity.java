package com.education.counselor.trainer.employee.counsellor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.counsellor.attendance.AttendanceActivity;
import com.education.counselor.trainer.employee.counsellor.courseWiseStudents.CoursewiseStudentList;
import com.education.counselor.trainer.employee.counsellor.extra_buttons.DailyReportActivity;
import com.education.counselor.trainer.employee.counsellor.extra_buttons.media_update.MediaUpdateActivity;
import com.education.counselor.trainer.employee.counsellor.extra_buttons.success_video_and_review.SuccessVideoAndReviewActivity;
import com.education.counselor.trainer.employee.counsellor.live_chat.chatChoice;
import com.education.counselor.trainer.employee.counsellor.notification.CounsellorNotificationActivity;
import com.education.counselor.trainer.employee.counsellor.responsible_centers.list.ResponsibleCentersListActivity;
import com.education.counselor.trainer.employee.counsellor.start_class.centers.StartClassCentersActivity;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;
public class CounsellorDashboardActivity extends AppCompatActivity {
    Button my_account, start_class, attendance,
            responsible_centers, media_update, success_video, daily_report, logout, coursewise, chat, notify;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String email;
    private String name = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_dashboard);
        FirebaseMessaging.getInstance().subscribeToTopic("null").addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CounsellorDashboardActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
        if (user != null) {
            email = user.getEmail();
        }
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("counsellor");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(email, ds.child("mail").getValue(String.class)))
                        name = ds.child("name").getValue(String.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        my_account = findViewById(R.id.my_account);
        my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MyAccountActivity.class));
            }
        });
        start_class = findViewById(R.id.start_class);
        start_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), StartClassCentersActivity.class));
            }
        });
        attendance = findViewById(R.id.attendance);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AttendanceActivity.class));
            }
        });
        responsible_centers = findViewById(R.id.responsible_centers);
        responsible_centers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ResponsibleCentersListActivity.class));
            }
        });
        media_update = findViewById(R.id.media_update);
        media_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MediaUpdateActivity.class));
            }
        });
        success_video = findViewById(R.id.success_video);
        success_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SuccessVideoAndReviewActivity.class));
            }
        });
        daily_report = findViewById(R.id.daily_report);
        daily_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), DailyReportActivity.class));
            }
        });
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
            }
        });
        coursewise = findViewById(R.id.coursewise);
        coursewise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CoursewiseStudentList.class));
            }
        });
        chat = findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), chatChoice.class));
            }
        });
        notify = findViewById(R.id.notify);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CounsellorNotificationActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }
}