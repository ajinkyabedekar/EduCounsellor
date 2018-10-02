package com.education.counselor.trainer.student;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.education.counselor.trainer.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class StudentDashboardActivity extends AppCompatActivity {
    Button attend_class, add_query, query_list, courses_list, project_details, internship_list, placement_list, call_counsellor;
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
        setContentView(R.layout.activity_student_dashboard);
        attend_class = findViewById(R.id.attend_class);
        query_list=findViewById(R.id.query_list);
        attend_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AttendanceActivity.class));
            }
        });
        add_query = findViewById(R.id.add_query);
        add_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AddQueryActivity.class));
            }
        });
        query_list = findViewById(R.id.query_list);
        query_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), QueryListActivity.class));
            }
        });
        courses_list = findViewById(R.id.courses_list);
        courses_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), CoursesListActivity.class));
            }
        });
        project_details = findViewById(R.id.project_details);
        project_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ProjectDetailsActivity.class));
            }
        });
        internship_list = findViewById(R.id.internship_list);
        internship_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), InternshipListActivity.class));
            }
        });
        placement_list = findViewById(R.id.placement_list);
        placement_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), PlacementListActivity.class));
            }
        });
        call_counsellor = findViewById(R.id.call_counsellor);
        call_counsellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(StudentDashboardActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getBaseContext(), "Please Provide Permissions", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(StudentDashboardActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "7425835169"));
                    startActivity(intent);
                }
            }
        });
    }
}