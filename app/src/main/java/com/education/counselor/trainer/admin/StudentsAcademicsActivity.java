package com.education.counselor.trainer.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentsAcademicsActivity extends AppCompatActivity {
    EditText name, mobile_number, mail, project, grade, score, certificate, award, professor, project_name;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_academics);
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    name.requestFocus();
                    name.setError("This Is A Required Field");
                } else if (mobile_number.getText().toString().equals("")) {
                    mobile_number.requestFocus();
                    mobile_number.setError("This Is A Required Field");
                } else if (mail.getText().toString().equals("")) {
                    mail.requestFocus();
                    mail.setError("This Is A Required Field");
                } else if (project.getText().toString().equals("")) {
                    project.requestFocus();
                    project.setError("This Is A Required Field");
                } else if (grade.getText().toString().equals("")) {
                    grade.requestFocus();
                    grade.setError("This Is A Required Field");
                } else if (score.getText().toString().equals("")) {
                    score.requestFocus();
                    score.setError("This Is A Required Field");
                } else if (certificate.getText().toString().equals("")) {
                    certificate.requestFocus();
                    certificate.setError("This Is A Required Field");
                } else if (award.getText().toString().equals("")) {
                    award.requestFocus();
                    award.setError("This Is A Required Field");
                } else if (professor.getText().toString().equals("")) {
                    professor.requestFocus();
                    professor.setError("This Is A Required Field");
                } else if (project_name.getText().toString().equals("")) {
                    project_name.requestFocus();
                    project_name.setError("This Is A Required Field");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("student").push();
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
                    Toast.makeText(getBaseContext(), "Student Academic Details Updated Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), AdminDashboardActivity.class));
                }
            }
        });
    }
}