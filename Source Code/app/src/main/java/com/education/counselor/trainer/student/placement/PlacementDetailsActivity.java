/*
*************************************
*      Author:Yogesh Sharma         *
*************************************
 --------------------------------------------------------------------------------------------------------------------------
 |     Its an placement activity detail class which shhow  details of the individual  placement of the registered student |
  -------------------------------------------------------------------------------------------------------------------------

*/
package com.education.counselor.trainer.student.placement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
public class PlacementDetailsActivity extends AppCompatActivity {
    EditText name, department, company, package_name, location, student;
    Button submit, delete;
    DatabaseReference studentData;
    private String n = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_details2);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        name = findViewById(R.id.name);
        department = findViewById(R.id.department);
        company = findViewById(R.id.company);
        package_name = findViewById(R.id.package_name);
        location = findViewById(R.id.location);
        student = findViewById(R.id.student);
        submit = findViewById(R.id.submit);
        delete = findViewById(R.id.delete);
        studentData = FirebaseDatabase.getInstance().getReference("placements");
        student.setEnabled(false);
        studentData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("name").getValue(String.class), n)) {
                        name.setText(n);
                        department.setText(ds.child("department").getValue(String.class));
                        company.setText(ds.child("company").getValue(String.class));
                        package_name.setText(ds.child("package_name").getValue(String.class));
                        location.setText(ds.child("location").getValue(String.class));
                        student.setText(ds.getKey());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e[] = new EditText[]{name, department, company, package_name, location, student};
                if (check(e)) {
                    Toast.makeText(PlacementDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else
                    update();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentData.child(student.getText().toString()).removeValue();
                Toast.makeText(getBaseContext(), "Placement Deleted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), PlacementListActivity.class));
            }
        });
    }
    private void update() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("placements").child(student.getText().toString());
        myRef.child("name").setValue(name.getText().toString());
        myRef.child("department").setValue(department.getText().toString());
        myRef.child("company").setValue(company.getText().toString());
        myRef.child("package_name").setValue(package_name.getText().toString());
        myRef.child("location").setValue(location.getText().toString());
        Toast.makeText(getBaseContext(), "Placement Updated Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getBaseContext(), PlacementListActivity.class));
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
