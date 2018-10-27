package com.education.counselor.trainer.student;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.education.counselor.trainer.student.attendance.AttendanceActivity;
import com.education.counselor.trainer.student.course.EnrolledCourseActivity;
import com.education.counselor.trainer.student.internship.InternshipListActivity;
import com.education.counselor.trainer.student.placement.PlacementListActivity;
import com.education.counselor.trainer.student.query.AddQueryActivity;
import com.education.counselor.trainer.student.query.QueryListActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StudentDashboardActivity extends AppCompatActivity {
    Button logout, attend_class, add_query, query_list, courses_list, project_details, internship_list, placement_list, call_counsellor;
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
        logout = findViewById(R.id.logout);
        attend_class = findViewById(R.id.attend_class);
        add_query = findViewById(R.id.add_query);
        query_list = findViewById(R.id.query_list);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
            }
        });
        attend_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AttendanceActivity.class));
            }
        });
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
                startActivity(new Intent(getBaseContext(), EnrolledCourseActivity.class));
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
                if (ContextCompat.checkSelfPermission(StudentDashboardActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getBaseContext(), "Please Provide Permissions", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(StudentDashboardActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
                } else if (ContextCompat.checkSelfPermission(StudentDashboardActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getBaseContext(), "Please Provide Permissions", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(StudentDashboardActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 2);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                try (Cursor c = getContentResolver().query(uri, new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.TYPE
                }, null, null, null)) {
                    if (c != null && c.moveToFirst()) {
                        String number = c.getString(0);
                        showSelectedNumber(number);
                    }
                }
            }
        }
    }

    public void showSelectedNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(intent);
    }
}