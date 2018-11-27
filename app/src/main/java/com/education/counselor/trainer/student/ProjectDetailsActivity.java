/*
*************************************
*      Author:Yogesh Sharma         *
*************************************
#############################################################
# Shows the details of the project studen did in the collage #
##############################################################
*/
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
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
public class ProjectDetailsActivity extends AppCompatActivity {
    EditText name, link, grade, certificate, award, professor;
    Button submit, delete;
    DatabaseReference reference, ref;
    FirebaseUser user;
    String access;
    int temp = 0;
    boolean flag = false;
    private String n = "", email;

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
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }
        ref = FirebaseDatabase.getInstance().getReference("student");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("mail").getValue(String.class), email)) {
                        n = ds.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        reference = FirebaseDatabase.getInstance().getReference("projects").child(n);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    name.setText(ds.child("name").getValue(String.class));
                    link.setText(ds.child("link").getValue(String.class));
                    grade.setText(ds.child("grade").getValue(String.class));
                    certificate.setText(ds.child("certificate_status").getValue(String.class));
                    award.setText(ds.child("award_status").getValue(String.class));
                    professor.setText(ds.child("professor").getValue(String.class));
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
                startActivity(new Intent(getBaseContext(), StudentDashboardActivity.class));
                finishAffinity();
            }
        });
    }

    private void update() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("projects").child(n);
        reference.child("name").setValue(name.getText().toString());
        reference.child("link").setValue(link.getText().toString());
        reference.child("grade").setValue(grade.getText().toString());
        reference.child("certificate_status").setValue(certificate.getText().toString());
        reference.child("award_status").setValue(award.getText().toString());
        reference.child("professor").setValue(professor.getText().toString());
        Toast.makeText(getBaseContext(), "Project Updated Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getBaseContext(), StudentDashboardActivity.class));
        finishAffinity();
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
        startActivity(new Intent(getBaseContext(), StudentDashboardActivity.class));
        finishAffinity();
    }
}
