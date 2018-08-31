package com.education.counselor.trainer;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), StudentLoginActivity.class));
            }
        });
        counsellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CounsellorLoginActivity.class));
            }
        });
        authority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AuthorityLoginActivity.class));
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AdminLoginActivity.class));
            }
        });
    }
}