package com.education.counselor.trainer.employee.counsellor.active_course;

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

public class studentProjectDetails extends AppCompatActivity {
    EditText name, link, grade, certificate, award, professor;
    Button submit;
    DatabaseReference reference;
    private String n, p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_project_details);
        Intent i = getIntent();
        if (i.hasExtra("phone")) {
            p = i.getStringExtra("phone");
        }
        name = findViewById(R.id.name);
        link = findViewById(R.id.link);
        grade = findViewById(R.id.grade);
        certificate = findViewById(R.id.certificate);
        award = findViewById(R.id.award);
        professor = findViewById(R.id.professor);
        submit = findViewById(R.id.submit);
        reference = FirebaseDatabase.getInstance().getReference("student");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("mobile_number").getValue(String.class), p)) {
                        name.setText(ds.child("project_details").child("name").getValue(String.class));
                        link.setText(ds.child("project_details").child("project_drive_link").getValue(String.class));
                        grade.setText(ds.child("project_details").child("grade").getValue(String.class));
                        certificate.setText(ds.child("project_details").child("certificate_status").getValue(String.class));
                        award.setText(ds.child("project_details").child("award_status").getValue(String.class));
                        professor.setText(ds.child("project_details").child("professor").getValue(String.class));
                        n = ds.getKey();
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
                EditText e[] = new EditText[]{name, link, grade, certificate, award, professor};
                if (check(e)) {
                    Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                } else
                    update();
            }
        });

    }

    private void update() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("student").child(n);
        reference.child("project_details").child("name").setValue(name.getText().toString());
        reference.child("project_details").child("project_drive_link").setValue(link.getText().toString());
        reference.child("project_details").child("grade").setValue(grade.getText().toString());
        reference.child("project_details").child("certificate").setValue(certificate.getText().toString());
        reference.child("project_details").child("award").setValue(award.getText().toString());
        reference.child("project_details").child("professor").setValue(professor.getText().toString());
        Toast.makeText(getBaseContext(), "Project Updated Successfully", Toast.LENGTH_SHORT).show();
        finish();
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
