package com.education.counselor.trainer.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPlacementActivity extends AppCompatActivity {
    EditText name, department, company, package_name, location, student;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_placement);
        name = findViewById(R.id.name);
        department = findViewById(R.id.department);
        company = findViewById(R.id.company);
        package_name = findViewById(R.id.package_name);
        location = findViewById(R.id.location);
        student = findViewById(R.id.student);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    name.requestFocus();
                    name.setError("This Is A Required Field");
                } else if (department.getText().toString().equals("")) {
                    department.requestFocus();
                    department.setError("This Is A Required Field");
                } else if (company.getText().toString().equals("")) {
                    company.requestFocus();
                    company.setError("This Is A Required Field");
                } else if (package_name.getText().toString().equals("")) {
                    package_name.requestFocus();
                    package_name.setError("This Is A Required Field");
                } else if (location.getText().toString().equals("")) {
                    location.requestFocus();
                    location.setError("This Is A Required Field");
                } else if (student.getText().toString().equals("")) {
                    student.requestFocus();
                    student.setError("This Is A Required Field");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("placement").child(student.getText().toString());
                    myRef.child("name").setValue(name.getText().toString());
                    myRef.child("department").setValue(department.getText().toString());
                    myRef.child("company").setValue(company.getText().toString());
                    myRef.child("package_name").setValue(package_name.getText().toString());
                    myRef.child("location").setValue(location.getText().toString());
                    Toast.makeText(getBaseContext(), "Placement Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), PlacementListActivity.class));
                }
            }
        });
    }
}