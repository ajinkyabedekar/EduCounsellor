package com.education.counselor.trainer.launcher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.login.AdminLoginActivity;
import com.education.counselor.trainer.login.StudentLoginActivity;
import com.education.counselor.trainer.login.authority.AuthorityChoiceActivity;
import com.education.counselor.trainer.login.employee.EmployeeChoiceActivity;
public class LoginActivity extends AppCompatActivity {
    Button student, counsellor, authority, admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        student = findViewById(R.id.student);
        counsellor = findViewById(R.id.counsellor);
        authority = findViewById(R.id.authority);
        admin = findViewById(R.id.admin);
        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            requestPermission();
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), StudentLoginActivity.class));
            }
        });
        counsellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), EmployeeChoiceActivity.class));
            }
        });
        authority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AuthorityChoiceActivity.class));
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AdminLoginActivity.class));
            }
        });
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }
}