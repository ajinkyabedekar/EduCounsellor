package com.education.counselor.trainer.employee.counsellor.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
public class AddStartupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText name, vision, website, team, funding, turnover, student, id;
    Button submit;
    DatabaseReference db;
    long number;
    private String n = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_startup2);
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
        generate_random();
        id.setText(String.valueOf(number));
        id.setEnabled(false);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    name.requestFocus();
                    name.setError("This Is A Required Field");
                } else if (vision.getText().toString().equals("")) {
                    vision.requestFocus();
                    vision.setError("This Is A Required Field");
                } else if (website.getText().toString().equals("")) {
                    website.requestFocus();
                    website.setError("This Is A Required Field");
                } else if (team.getText().toString().equals("")) {
                    team.requestFocus();
                    team.setError("This Is A Required Field");
                } else if (funding.getText().toString().equals("")) {
                    funding.requestFocus();
                    funding.setError("This Is A Required Field");
                } else if (turnover.getText().toString().equals("")) {
                    turnover.requestFocus();
                    turnover.setError("This Is A Required Field");
                } else if (student.getText().toString().equals("")) {
                    student.requestFocus();
                    student.setError("This Is A Required Field");
                } else if (id.getText().toString().equals("")) {
                    id.requestFocus();
                    id.setError("This Is A Required Field");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("startup").child(id.getText().toString());
                    myRef.child("name").setValue(name.getText().toString());
                    myRef.child("vision").setValue(vision.getText().toString());
                    myRef.child("website").setValue(website.getText().toString());
                    myRef.child("funding").setValue(funding.getText().toString());
                    myRef.child("turnover").setValue(turnover.getText().toString());
                    myRef.child("student_name").setValue(student.getText().toString());
                    myRef.child("center").setValue(n);
                    Toast.makeText(getBaseContext(), "Startup Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), StartupListActivity.class));
                }
            }
        });
    }
    private void generate_random() {
        number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        validate_random();
    }
    private void validate_random() {
        db = FirebaseDatabase.getInstance().getReference("startup");
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