/*
*************************************
*      Author:Yogesh Sharma         *
*************************************
___________________________________________________
  Shows the Dashboard of te current user logged in 
____________________________________________________
*/
package com.education.counselor.trainer.student;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.education.counselor.trainer.student.attendance.AttendanceActivity;
import com.education.counselor.trainer.student.course.EnrolledCourseActivity;
import com.education.counselor.trainer.student.course.list.CoursesListActivity;
import com.education.counselor.trainer.student.internship.InternshipListActivity;
import com.education.counselor.trainer.student.placement.PlacementListActivity;
import com.education.counselor.trainer.student.query.AddQueryActivity;
import com.education.counselor.trainer.student.query.list.QueryListActivity;
import com.education.counselor.trainer.student.webview.PayOnlineActivity;
import com.education.counselor.trainer.student.webview.RankethonWebsiteActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;
public class StudentDashboardActivity extends AppCompatActivity {
    Button logout, attend_class, add_query, query_list, courses_list, project_details, internship_list, placement_list, call_counsellor, enrolled_courses, pay_online, paytm_neft, rankethon_website;
    DatabaseReference db;
    String url = "";
    WebView webview;
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

    String access, email;
    int temp = 0;
    boolean flag = false;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        db = FirebaseDatabase.getInstance().getReference("webview");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    url = ds.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        logout = findViewById(R.id.logout);
        attend_class = findViewById(R.id.attend_class);
        add_query = findViewById(R.id.add_query);
        query_list = findViewById(R.id.query_list);
        FirebaseMessaging.getInstance().subscribeToTopic("counsellor");
        FirebaseMessaging.getInstance().subscribeToTopic("reviewclass");
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finishAffinity();
            }
        });
        attend_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(StudentDashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getBaseContext(), "Please Provide Permissions", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(StudentDashboardActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    startActivity(new Intent(getBaseContext(), AttendanceActivity.class));
                    finishAffinity();
                }
            }
        });
        add_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AddQueryActivity.class));
                finishAffinity();
            }
        });
        query_list = findViewById(R.id.query_list);
        query_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), QueryListActivity.class));
                finishAffinity();
            }
        });
        courses_list = findViewById(R.id.courses_list);
        courses_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), CoursesListActivity.class));
                finishAffinity();
            }
        });
        project_details = findViewById(R.id.project_details);
        project_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ProjectDetailsActivity.class));
                finishAffinity();
            }
        });
        internship_list = findViewById(R.id.internship_list);
        internship_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), InternshipListActivity.class));
                finishAffinity();
            }
        });
        placement_list = findViewById(R.id.placement_list);
        placement_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), PlacementListActivity.class));
                finishAffinity();
            }
        });
        enrolled_courses = findViewById(R.id.enrolled_course);
        enrolled_courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), EnrolledCourseActivity.class));
                finishAffinity();
            }
        });
        call_counsellor = findViewById(R.id.call_counsellor);
        call_counsellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(StudentDashboardActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getBaseContext(), "Please Provide Permissions", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(StudentDashboardActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
                } else if (ContextCompat.checkSelfPermission(StudentDashboardActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getBaseContext(), "Please Provide Permissions", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(StudentDashboardActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 3);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    startActivityForResult(intent, 2);
                }
            }
        });
        pay_online = findViewById(R.id.pay_online);
        pay_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), PayOnlineActivity.class));
                finishAffinity();
            }
        });
        paytm_neft = findViewById(R.id.paytm_neft);
        paytm_neft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), PayOnlineActivity.class));
                finishAffinity();
            }
        });
        rankethon_website = findViewById(R.id.rankethon_website);
        rankethon_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), RankethonWebsiteActivity.class));
                finishAffinity();
            }
        });
        webview = findViewById(R.id.webview);
        if (url != null) {
            webview.setVisibility(View.GONE);
        } else {
            webview.setVisibility(View.VISIBLE);
        }
        webview.setWebViewClient(new MyBrowser());
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            checkUser(user);
        }
    }

    private void checkUser(final FirebaseUser user) {
        email = user.getEmail();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : ds.getChildren()) {
                        if (Objects.equals(dataSnapshot1.child("mail").getValue(String.class), email)) {
                            access = ds.getKey();
                            flag = true;
                            break;
                        }
                    }
                    if (flag)
                        break;
                }
                if (access != null) {
                    switch (access) {
                        case "student":
                            return;
                        default:
                            temp = 1;
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if (temp == 1) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            finishAffinity();
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(url);
            return true;
        }
    }
}
