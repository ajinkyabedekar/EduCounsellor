package com.education.counselor.trainer.admin.courses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.admin.courses.list.CoursesListActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoursesDetailsActivity extends AppCompatActivity {
    EditText name, hours, details, content, enrollments, batches, year, month;
    Spinner category, status;
    Button detailed, submit, delete;
    DatabaseReference studentData;
    private String n = "", course_id, center, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_details);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        if (i.hasExtra("center")) {
            center = i.getStringExtra("center");
        }
        name = findViewById(R.id.name);
        hours = findViewById(R.id.hours);
        details = findViewById(R.id.details);
        content = findViewById(R.id.content);
        enrollments = findViewById(R.id.enrollments);
        batches = findViewById(R.id.batches);
        year = findViewById(R.id.year);
        month = findViewById(R.id.month);
        category = findViewById(R.id.category);
        status = findViewById(R.id.status);
        detailed = findViewById(R.id.detailed);
        submit = findViewById(R.id.submit);
        delete = findViewById(R.id.delete);
        final List<String> category_list = new ArrayList<>();
        final List<String> status_list = new ArrayList<>();
        category_list.add("Category");
        category_list.add("School");
        category_list.add("College");
        status_list.add("Status");
        status_list.add("Inactive");
        status_list.add("Active");
        ArrayAdapter<String> category_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, category_list);
        ArrayAdapter<String> status_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, status_list);
        category.setAdapter(category_adapter);
        status.setAdapter(status_adapter);
        studentData = FirebaseDatabase.getInstance().getReference("centers");
        studentData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("name").getValue(String.class), center)) {
                        for (DataSnapshot d : ds.child("courses").getChildren()) {
                            if (Objects.equals(d.child("name").getValue(String.class), n)) {
                                name.setText(n);
                                hours.setText(d.child("hours").getValue(String.class));
                                details.setText(d.child("details").getValue(String.class));
                                content.setText(d.child("content").getValue(String.class));
                                enrollments.setText(d.child("enrollments").getValue(String.class));
                                batches.setText(d.child("batch_count").getValue(String.class));
                                year.setText(d.child("enrollments_year_wise").getValue(String.class));
                                month.setText(d.child("enrollments_month_wise").getValue(String.class));
                                category.setSelection(category_list.indexOf(d.child("category").getValue(String.class)));
                                status.setSelection(status_list.indexOf(d.child("status").getValue(String.class)));
                                course_id = d.getKey();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        getKey();
        detailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e[] = new EditText[]{name, hours, details, content, enrollments, batches, year, month};
                if (check(e)) {
                    Toast.makeText(CoursesDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else
                    update(key, course_id);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentData.child(course_id).removeValue();
                Toast.makeText(getBaseContext(), "Course Deleted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), CoursesListActivity.class));
            }
        });
    }

    private void update(String key, String course_id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("centers").child(key).child("courses").child(course_id);
        myRef.child("name").setValue(name.getText().toString());
        myRef.child("hours").setValue(hours.getText().toString());
        myRef.child("details").setValue(details.getText().toString());
        myRef.child("content").setValue(content.getText().toString());
        myRef.child("enrollments").setValue(enrollments.getText().toString());
        myRef.child("batch_count").setValue(batches.getText().toString());
        myRef.child("enrollments_year_wise").setValue(year.getText().toString());
        myRef.child("enrollments_month_wise").setValue(month.getText().toString());
        myRef.child("category").setValue(category.getSelectedItem().toString());
        myRef.child("status").setValue(status.getSelectedItem().toString());
        Toast.makeText(getBaseContext(), "Course Updated Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getBaseContext(), CoursesListActivity.class));
    }

    private void getKey() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("centers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(snapshot.child("name").getValue(), String.valueOf(center))) {
                        key = snapshot.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean check(EditText[] e) {
        for (EditText ed : e) {
            if (TextUtils.isEmpty(ed.getText().toString())) {
                ed.requestFocus();
                ed.setError("This Is A Required Field");
                return true;
            }
        }
        return false;
    }
}