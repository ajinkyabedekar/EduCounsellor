package com.education.counselor.trainer.admin;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import com.education.counselor.trainer.R;
public class CoursesListActivity extends AppCompatActivity {
    Button course;
    Switch course_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_list);
        course = findViewById(R.id.course);
        course_status = findViewById(R.id.course_status);
    }
}