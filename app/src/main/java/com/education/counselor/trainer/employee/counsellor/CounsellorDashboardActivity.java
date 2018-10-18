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
import com.education.counselor.trainer.employee.counsellor.liveChatCollegeAuthority.CounsellorCollegeAuthorityLiveChat;
import com.education.counselor.trainer.employee.counsellor.liveChatSchoolAuthority.CounsellorSchoolAuthorityLiveChat;
import com.education.counselor.trainer.employee.counsellor.responsible_centers.list.ResponsibleCentersListActivity;
import com.education.counselor.trainer.employee.counsellor.start_class.centers.StartClassCentersActivity;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class CounsellorDashboardActivity extends AppCompatActivity {
    Button my_account, start_class, attendance,
            responsible_centers, media_update, success_video, daily_report, logout, coursewise, chatCollege, chatSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_dashboard);
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
        coursewise=findViewById(R.id.coursewise);
        coursewise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CoursewiseStudentList.class));
            }
        });
        chatCollege = findViewById(R.id.chatCollege);
        chatCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CounsellorCollegeAuthorityLiveChat.class));
            }
        });
        chatSchool = findViewById(R.id.chatSchool);
        chatSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CounsellorSchoolAuthorityLiveChat.class));
            }
        });
    }
}