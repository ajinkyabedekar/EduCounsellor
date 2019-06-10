package com.education.counselor.trainer.admin.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.admin.student.all.AllStudentsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
public class StudentsAcademicsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText name, mobile_number, mail, project, grade, score, certificate, award, professor, project_name;
    Button submit;
    DatabaseReference studentData;
    private String n = "", student_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_academics);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        name = findViewById(R.id.name);
        mobile_number = findViewById(R.id.mobile_number);
        mail = findViewById(R.id.mail);
        project = findViewById(R.id.project);
        grade = findViewById(R.id.grade);
        score = findViewById(R.id.score);
        certificate = findViewById(R.id.certificate);
        award = findViewById(R.id.award);
        professor = findViewById(R.id.professor);
        project_name = findViewById(R.id.project_name);
        submit = findViewById(R.id.submit);
        studentData = FirebaseDatabase.getInstance().getReference("student");
        studentData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("name").getValue(String.class), n)) {
                        name.setText(n);
                        mobile_number.setText(ds.child("mobile_number").getValue(String.class));
                        mail.setText(ds.child("mail").getValue(String.class));
                        project.setText(ds.child("project_drive_link").getValue(String.class));
                        grade.setText(ds.child("grade").getValue(String.class));
                        score.setText(ds.child("tutor_score").getValue(String.class));
                        certificate.setText(ds.child("certificate_status").getValue(String.class));
                        award.setText(ds.child("award").getValue(String.class));
                        professor.setText(ds.child("professor").getValue(String.class));
                        project_name.setText(ds.child("project_name").getValue(String.class));
                        student_id = ds.getKey();
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
                EditText e[] = new EditText[]{name, mobile_number, mail, project, grade, score, certificate, award, professor, project_name};
                if (check(e)) {
                    Toast.makeText(StudentsAcademicsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else
                    update();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
    private void update() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("student").child(student_id).child("project_details");
        myRef.child("name").setValue(name.getText().toString());
        myRef.child("mobile_number").setValue(mobile_number.getText().toString());
        myRef.child("mail").setValue(mail.getText().toString());
        myRef.child("project_drive_link").setValue(project.getText().toString());
        myRef.child("grade").setValue(grade.getText().toString());
        myRef.child("tutor_score").setValue(score.getText().toString());
        myRef.child("certificate_status").setValue(certificate.getText().toString());
        myRef.child("award").setValue(award.getText().toString());
        myRef.child("professor").setValue(professor.getText().toString());
        myRef.child("project_name").setValue(project_name.getText().toString());
        Toast.makeText(getBaseContext(), "Student Details Updated Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getBaseContext(), AllStudentsActivity.class));
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