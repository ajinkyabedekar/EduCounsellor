package com.education.counselor.trainer.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;

public class EnrolledCourseActivity extends AppCompatActivity {
Button active ,inactive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolled_course);
        active=findViewById(R.id.active);
        inactive=findViewById(R.id.inactive);
        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EnrolledCourseActivity.this,CoursesDisplay.class);
                intent.putExtra("status","active");
                startActivity(intent);
            }
        });
        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EnrolledCourseActivity.this,CoursesDisplay.class);
                intent.putExtra("status","inactive");
                startActivity(intent);
            }
        });
    }
}