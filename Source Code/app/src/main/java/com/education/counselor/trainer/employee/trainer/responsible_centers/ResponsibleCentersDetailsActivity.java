package com.education.counselor.trainer.employee.trainer.responsible_centers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.trainer.extra_buttons.DailyReportActivity;
import com.education.counselor.trainer.employee.trainer.meeting.AuthorityMeetActivity;
import com.education.counselor.trainer.employee.trainer.meeting.HodMeetActivity;
import com.education.counselor.trainer.employee.trainer.meeting.RankethonMeetActivity;
import com.education.counselor.trainer.employee.trainer.meeting.StudentMeetActivity;
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
}