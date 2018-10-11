package com.education.counselor.trainer.admin.courses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

public class AddCoursesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText name, hours, details, content;
    Spinner category, status;
    Button submit;
    DatabaseReference db;
    long number;
    private String n = "", key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        name = findViewById(R.id.name);
        hours = findViewById(R.id.hours);
        details = findViewById(R.id.details);
        content = findViewById(R.id.content);
        category = findViewById(R.id.category);
        status = findViewById(R.id.status);
        submit = findViewById(R.id.submit);
        category.setOnItemSelectedListener(this);
        status.setOnItemSelectedListener(this);
        List<String> category_list = new ArrayList<>();
        List<String> status_list = new ArrayList<>();
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
        getKey();
        generate_random();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    name.requestFocus();
                    name.setError("This Is A Required Field");
                } else if (hours.getText().toString().equals("")) {
                    hours.requestFocus();
                    hours.setError("This Is A Required Field");
                } else if (details.getText().toString().equals("")) {
                    details.requestFocus();
                    details.setError("This Is A Required Field");
                } else if (content.getText().toString().equals("")) {
                    content.requestFocus();
                    content.setError("This Is A Required Field");
                } else if (category.getSelectedItem().toString().equals("Category")) {
                    Toast.makeText(getBaseContext(), "Please Select A Category", Toast.LENGTH_SHORT).show();
                } else if (status.getSelectedItem().toString().equals("Status")) {
                    Toast.makeText(getBaseContext(), "Please Select A Status", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("centers").child(key).child("courses").child(String.valueOf(number));
                    ref.child("name").setValue(name.getText().toString());
                    ref.child("hours").setValue(hours.getText().toString());
                    ref.child("details").setValue(details.getText().toString());
                    ref.child("content").setValue(content.getText().toString());
                    ref.child("category").setValue(category.getSelectedItem().toString());
                    ref.child("status").setValue(status.getSelectedItem().toString());
                    Toast.makeText(getBaseContext(), "Course Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), CoursesListActivity.class));
                }
            }
        });
    }

    private void getKey() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("centers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(snapshot.child("name").getValue(), String.valueOf(n))) {
                        key = snapshot.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void generate_random() {
        number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        validate_random();
    }

    private void validate_random() {
        db = FirebaseDatabase.getInstance().getReference("centers").child("courses");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.getKey(), String.valueOf(n))) {
                        generate_random();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}