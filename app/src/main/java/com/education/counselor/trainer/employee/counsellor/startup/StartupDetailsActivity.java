package com.education.counselor.trainer.employee.counsellor.startup;

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
import com.education.counselor.trainer.employee.counsellor.startup.list.StartupListActivity;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
public class StartupDetailsActivity extends AppCompatActivity {
    EditText name, vision, website, team, funding, turnover, student, id;
    Button submit, delete;
    DatabaseReference studentData;
    private String n = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_details2);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        name = findViewById(R.id.name);
        vision = findViewById(R.id.vision);
        website = findViewById(R.id.website);
        team = findViewById(R.id.team);
        funding = findViewById(R.id.funding);
        turnover = findViewById(R.id.turnover);
        student = findViewById(R.id.student);
        id = findViewById(R.id.id);
        submit = findViewById(R.id.submit);
        delete = findViewById(R.id.delete);
        studentData = FirebaseDatabase.getInstance().getReference("startup");
        id.setEnabled(false);
        studentData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("name").getValue(String.class), n)) {
                        name.setText(n);
                        vision.setText(ds.child("vision").getValue(String.class));
                        website.setText(ds.child("website").getValue(String.class));
                        team.setText(ds.child("team").getValue(String.class));
                        funding.setText(ds.child("funding").getValue(String.class));
                        turnover.setText(ds.child("turnover").getValue(String.class));
                        student.setText(ds.child("student_name").getValue(String.class));
                        id.setText(ds.getKey());
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
                EditText e[] = new EditText[]{name, vision, website, team, funding, turnover, student, id};
                if (check(e)) {
                    Toast.makeText(StartupDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else
                    update();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentData.child(id.getText().toString()).removeValue();
                Toast.makeText(getBaseContext(), "Startup Deleted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), StartupListActivity.class));
            }
        });
    }
    private void update() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("startup").child(id.getText().toString());
        myRef.child("name").setValue(name.getText().toString());
        myRef.child("vision").setValue(vision.getText().toString());
        myRef.child("website").setValue(website.getText().toString());
        myRef.child("funding").setValue(funding.getText().toString());
        myRef.child("turnover").setValue(turnover.getText().toString());
        myRef.child("student_name").setValue(student.getText().toString());
        myRef.child("center").setValue(n);
        Toast.makeText(getBaseContext(), "Startup Updated Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getBaseContext(), StartupListActivity.class));
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

    String access, email;
    boolean flag = false;

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
        db.addValueEventListener(new ValueEventListener() {
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
                        case "counsellor":
                            return;
                        default:
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getBaseContext(), LoginActivity.class));
                            finishAffinity();
                            Toast.makeText(getBaseContext(), "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Please Check Your Network Connection and Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}