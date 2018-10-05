package com.education.counselor.trainer.counsellor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;

public class CounsellorDashboardActivity extends AppCompatActivity {
    Button start_class, attendance, responsible_centers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_dashboard);
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
    }
}