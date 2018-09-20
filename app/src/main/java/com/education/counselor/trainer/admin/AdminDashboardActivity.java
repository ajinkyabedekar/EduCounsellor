package com.education.counselor.trainer.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboardActivity extends AppCompatActivity {
    Button logout, add_student, edit_student, student_list, startup_list, placement_list, edit_counsellor, news, centers, courses, add_employee, employee_list;
    private boolean pressed = false;

    @Override
    public void onBackPressed() {
        if (pressed) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            return;
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
        setContentView(R.layout.activity_admin_dashboard);
        logout = findViewById(R.id.logout);
        add_student = findViewById(R.id.add_student);
        edit_student = findViewById(R.id.edit_student);
        student_list = findViewById(R.id.student_list);
        startup_list = findViewById(R.id.startup_list);
        placement_list = findViewById(R.id.placement_list);
        edit_counsellor = findViewById(R.id.edit_counsellor);
        news = findViewById(R.id.news);
        centers = findViewById(R.id.centers);
        courses = findViewById(R.id.courses);
        add_employee = findViewById(R.id.add_employee);
        employee_list = findViewById(R.id.employee_list);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
            }
        });
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddStudentActivity.class));
            }
        });
        edit_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), EditStudentActivity.class));
            }
        });
        student_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AllStudentsActivity.class));
            }
        });
        startup_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), StartupCentersActivity.class));
            }
        });
        placement_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), PlacementCentersActivity.class));
            }
        });
        edit_counsellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CounsellorCentersActivity.class));
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), NewsCentersActivity.class));
            }
        });
        centers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CentersListActivity.class));
            }
        });
        courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CoursesCentersActivity.class));
            }
        });
        add_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddEmployeeActivity.class));
            }
        });
        employee_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), EmployeesListActivity.class));
            }
        });
    }
}