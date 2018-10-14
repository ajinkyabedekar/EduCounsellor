package com.education.counselor.trainer.login.employee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.login.employee.login.CounsellorLoginActivity;
import com.education.counselor.trainer.login.employee.login.TrainerLoginActivity;

public class EmployeeChoiceActivity extends AppCompatActivity {
    Button school, college;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_choice);
        school = findViewById(R.id.school);
        college = findViewById(R.id.college);
        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CounsellorLoginActivity.class));
            }
        });
        college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), TrainerLoginActivity.class));
            }
        });
    }
}