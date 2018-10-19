package com.education.counselor.trainer.student;

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
import com.education.counselor.trainer.admin.startup.list.StartupListActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProjectDetailsActivity extends AppCompatActivity {
    EditText name, link, grade, certificate, award, professor;
    Button submit, delete;
    DatabaseReference reference;
    private String n = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        name = findViewById(R.id.name);
        link = findViewById(R.id.link);
        grade = findViewById(R.id.grade);
        certificate = findViewById(R.id.certificate);
        award = findViewById(R.id.award);
        professor = findViewById(R.id.professor);
        submit = findViewById(R.id.submit);
        delete = findViewById(R.id.delete);
        reference = FirebaseDatabase.getInstance().getReference("projects");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("name").getValue(String.class), n)) {
                        name.setText(ds.child("name").getValue(String.class));
                        link.setText(ds.child("link").getValue(String.class));
                        grade.setText(ds.child("grade").getValue(String.class));
                        certificate.setText(ds.child("certificate_status").getValue(String.class));
                        award.setText(ds.child("award_status").getValue(String.class));
                        professor.setText(ds.child("professor").getValue(String.class));
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
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(n).removeValue();
                Toast.makeText(getBaseContext(), "Project Deleted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), StartupListActivity.class));
            }
        });
    }

    private void update() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("project").child(n);
        reference.child("name").setValue(name.getText().toString());
        reference.child("link").setValue(link.getText().toString());
        reference.child("grade").setValue(grade.getText().toString());
        reference.child("certificate").setValue(certificate.getText().toString());
        reference.child("award").setValue(award.getText().toString());
        reference.child("professor").setValue(professor.getText().toString());
        Toast.makeText(getBaseContext(), "Project Updated Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getBaseContext(), StudentDashboardActivity.class));
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