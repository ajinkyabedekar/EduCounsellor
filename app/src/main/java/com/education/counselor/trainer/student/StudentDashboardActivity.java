package com.education.counselor.trainer.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;

public class StudentDashboardActivity extends AppCompatActivity {
    Button attend_class, add_query, query_list, courses_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        attend_class = findViewById(R.id.attend_class);
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
    }
}