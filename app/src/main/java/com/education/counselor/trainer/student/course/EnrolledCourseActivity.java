/*
*************************************
*      Author:Yogesh Sharma         *
*************************************
*/


package com.education.counselor.trainer.student.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;
public class EnrolledCourseActivity extends AppCompatActivity {
    Button active, inactive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enrolled_courses);
        active = findViewById(R.id.active);
        inactive = findViewById(R.id.inactive);
        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnrolledCourseActivity.this, CoursesListActivity.class);
                intent.putExtra("status", "active");
                startActivity(intent);
            }
        });
        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnrolledCourseActivity.this, CoursesListActivity.class);
                intent.putExtra("status", "inactive");
                startActivity(intent);
            }
        });
    }
}
