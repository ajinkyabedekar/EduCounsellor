package com.education.counselor.trainer.counsellor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.counsellor.attendance.AttendanceActivity;
import com.education.counselor.trainer.counsellor.internship.list.InternshipListActivity;
import com.education.counselor.trainer.counsellor.meeting.AuthorityMeetActivity;
import com.education.counselor.trainer.counsellor.meeting.HodMeetActivity;
import com.education.counselor.trainer.counsellor.meeting.RankethonMeetActivity;
import com.education.counselor.trainer.counsellor.meeting.StudentMeetActivity;
import com.education.counselor.trainer.counsellor.news.list.NewsListActivity;
import com.education.counselor.trainer.counsellor.placement.list.PlacementListActivity;
import com.education.counselor.trainer.counsellor.report.DailyReportActivity;
import com.education.counselor.trainer.counsellor.responsible_centers.list.ResponsibleCentersListActivity;
import com.education.counselor.trainer.counsellor.start_class.centers.StartClassCentersActivity;
import com.education.counselor.trainer.counsellor.startup.list.StartupListActivity;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class CounsellorDashboardActivity extends AppCompatActivity {
    Button my_account, start_class, attendance, responsible_centers, startup_list, news_list, placement_list, internship_list, authority_meet, hod_meet, rankethon_meet, student_meet, daily_report, logout;

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
        startup_list = findViewById(R.id.startup_list);
        startup_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), StartupListActivity.class));
            }
        });
        news_list = findViewById(R.id.news_list);
        news_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), NewsListActivity.class));
            }
        });
        placement_list = findViewById(R.id.placement_list);
        placement_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), PlacementListActivity.class));
            }
        });
        internship_list = findViewById(R.id.internship_list);
        internship_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), InternshipListActivity.class));
            }
        });
        authority_meet = findViewById(R.id.authority_meet);
        authority_meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AuthorityMeetActivity.class));
            }
        });
        hod_meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), HodMeetActivity.class));
            }
        });
        rankethon_meet = findViewById(R.id.rankethon_meet);
        rankethon_meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), RankethonMeetActivity.class));
            }
        });
        student_meet = findViewById(R.id.student_meet);
        student_meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), StudentMeetActivity.class));
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
    }
}