package com.education.counselor.trainer.student.course.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.education.counselor.trainer.student.StudentDashboardActivity;
import com.education.counselor.trainer.student.course.EnrolledCourseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class CoursesListActivity extends AppCompatActivity {
    ProgressBar pg;
    String status = null;
    String access, email;
    int temp = 0;
    boolean flag = false;
    private RecyclerView recyclerView;
    private ArrayList<CoursesListEntryVo> data = new ArrayList<>();
    private CoursesListEntryAdapter adapter;
    private Context mContext;
    private String name, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_display);
        Intent i = getIntent();
        status = i.getStringExtra("status");
        pg = findViewById(R.id.progress);
        mContext = this;
        recyclerView = findViewById(R.id.course_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pg.setVisibility(View.VISIBLE);
        DatabaseReference s = FirebaseDatabase.getInstance().getReference("student");
        s.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.requireNonNull(snapshot.child("mail").getValue()).toString().equalsIgnoreCase(email)) {
                        name = (String) snapshot.child("name").getValue();
                        id = snapshot.getKey();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("courses");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (status != null) {
                        if (Objects.equals(ds.child("details").child("status").getValue(String.class), status)) {
                            data.add(new CoursesListEntryVo(Objects.requireNonNull(ds.child("details").child("name").getValue(String.class)), ds.getKey()));
                        } else {
                            Toast.makeText(mContext, "No Courses Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        data.add(new CoursesListEntryVo(Objects.requireNonNull(ds.child("details").child("name").getValue(String.class)), ds.getKey()));
                    }
                }
                if (data.size() == 0) {
                    Toast.makeText(getBaseContext(), "No Courses Found", Toast.LENGTH_SHORT).show();
                }
                adapter = new CoursesListEntryAdapter(mContext, data, name, id, status);
                pg.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
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
        if (status != null) {
            startActivity(new Intent(getBaseContext(), EnrolledCourseActivity.class));
            finishAffinity();
        } else {
            startActivity(new Intent(getBaseContext(), StudentDashboardActivity.class));
            finishAffinity();
        }
    }
}