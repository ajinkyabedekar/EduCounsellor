package com.education.counselor.trainer.employee.counsellor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class CounsellorDashboardActivity extends AppCompatActivity {
    Button my_account, start_class, attendance,
            responsible_centers, media_update, success_video, daily_report, logout, coursewise, chat, notify;
    String key, token_id;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_dashboard);
        Intent i = getIntent();
        if (i.hasExtra("key")) {
            key = i.getStringExtra("key");
            db = FirebaseDatabase.getInstance().getReference("counsellor/" + key);
            Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                @Override
                public void onSuccess(GetTokenResult getTokenResult) {
                    token_id = getTokenResult.getToken();
                }
            });

            db.child("token").setValue(token_id);
        }
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
                startActivity(new Intent(getBaseContext(), CounsellorNotificationActivity.class));
                /*Intent intent = new Intent(getBaseContext(), StudentDashboardActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(), (int) System.currentTimeMillis(), intent, 0);


                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                int notifyID = 1;
                String CHANNEL_ID = "my_channel_01";// The id of the channel.
                CharSequence name = "ABCD";// The user-visible name of the channel.
                int importance = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    importance = NotificationManager.IMPORTANCE_HIGH;
                }
                NotificationChannel mChannel = null;
                Notification n;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                    notificationManager.createNotificationChannel(mChannel);
                    n  = new Notification.Builder(getBaseContext())
                            .setContentTitle("New mail from " + "test@gmail.com")
                            .setContentText("Subject")
                            .setSmallIcon(R.mipmap.ic_logo)
                            .setContentIntent(pIntent)
                            .setChannelId(CHANNEL_ID)
                            .setAutoCancel(true)
                            .build();
                } else {
                    n = new Notification.Builder(getBaseContext())
                            .setContentTitle("New mail from " + "test@gmail.com")
                            .setContentText("Subject")
                            .setSmallIcon(R.mipmap.ic_logo)
                            .setContentIntent(pIntent)
                            .setAutoCancel(true)
                            .build();
                }
                notificationManager.notify(0, n);*/
            }
        });
    }
}