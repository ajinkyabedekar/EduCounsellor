package com.education.counselor.trainer.student.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.education.counselor.trainer.student.course.list.CoursesListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.Random;
public class CourseDetailsActivity extends AppCompatActivity {
    TextView fee, next, stat, content, desc, id;
    Button review, project;
    Intent intent;
    String status;
    String access, email;
    int temp = 0;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String data[] = getIntent().getStringArrayExtra("cdata");
        setContentView(R.layout.activity_course_details);
        fee = findViewById(R.id.fee_sub_text);
        next = findViewById(R.id.nextPayText);
        stat = findViewById(R.id.courseStatText);
        content = findViewById(R.id.courseContentText);
        desc = findViewById(R.id.description_text);
        id = findViewById(R.id.sidText);
        review = findViewById(R.id.totalReview);
        project = findViewById(R.id.activeProjectBtn);
        intent = getIntent();
        if (intent.hasExtra("status")) {
            status = intent.getStringExtra("status");
        }
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("CoursesListEntryVo");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (data[0].equals(ds.getKey())) {
                        content.setText((String) ds.child("details").child("content").getValue());
                        stat.setText((String) ds.child("details").child("status").getValue());
                        id.setText(data[2]);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CourseDetailsActivity.this, new Random().nextInt(10) + "", Toast.LENGTH_SHORT).show();
            }
        });
        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), CoursesListActivity.class));
        finishAffinity();
    }
}