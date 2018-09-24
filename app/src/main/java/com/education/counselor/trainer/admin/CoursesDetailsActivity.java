package com.education.counselor.trainer.admin;

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
    private String n = "", course_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_details);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
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
        studentData = FirebaseDatabase.getInstance().getReference("courses");
        studentData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("name").getValue(String.class), n)) {
                        name.setText(n);
                        hours.setText(ds.child("hours").getValue(String.class));
                        details.setText(ds.child("details").getValue(String.class));
                        content.setText(ds.child("content").getValue(String.class));
                        enrollments.setText(ds.child("category").getValue(String.class));
                        batches.setText(ds.child("batch_count").getValue(String.class));
                        year.setText(ds.child("enrollments_year_wise").getValue(String.class));
                        month.setText(ds.child("enrollments_month_wise").getValue(String.class));
                        category.setSelection(category_list.indexOf(ds.child("category").getValue(String.class)));
                        status.setSelection(status_list.indexOf(ds.child("status").getValue(String.class)));
                        course_id = ds.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                    update();
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

    private void update() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("courses").child(course_id);
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