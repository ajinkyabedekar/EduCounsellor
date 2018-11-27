package com.education.counselor.trainer.admin.employee;

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
import com.education.counselor.trainer.admin.employee.list.EmployeesListActivity;
import com.education.counselor.trainer.admin.employee.multiselectionspinner.MultiSelectionSpinner;
import com.education.counselor.trainer.admin.employee.multiselectionspinner.MultiSelectionSpinnerAdapter;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class AddEmployeeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final List<MultiSelectionSpinner> list = new ArrayList<>();
    EditText name, mail, login, password, employee, phone;
    Spinner centers, position;
    Button submit;
    long n;
    private DatabaseReference db;
    private MultiSelectionSpinner s = new MultiSelectionSpinner();
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance().getReference("centers");
        setContentView(R.layout.activity_add_employee);
        name = findViewById(R.id.name);
        position = findViewById(R.id.position);
        phone = findViewById(R.id.phone);
        mail = findViewById(R.id.mail);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        employee = findViewById(R.id.employee);
        centers = findViewById(R.id.centers);
        submit = findViewById(R.id.submit);
        mAuth = FirebaseAuth.getInstance();
        s.setText("Centers");
        list.add(s);
        position.setOnItemSelectedListener(this);
        List<String> position_list = new ArrayList<>();
        position_list.add("Position");
        position_list.add("Counsellor");
        position_list.add("Trainer");
        ArrayAdapter<String> position_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, position_list);
        position_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        position.setAdapter(position_adapter);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    s = new MultiSelectionSpinner();
                    s.setText(snapshot.child("name").getValue(String.class));
                    list.add(s);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddEmployeeActivity.this, databaseError + "", Toast.LENGTH_SHORT).show();
            }
        });
        final MultiSelectionSpinnerAdapter adapter = new MultiSelectionSpinnerAdapter(this, 0, list);
        centers.setAdapter(adapter);
        generate_random();
        employee.setText(String.valueOf(n));
        employee.setEnabled(false);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    name.requestFocus();
                    name.setError("This Is A Required Field");
                } else if (position.getSelectedItem().toString().equals("Position")) {
                    Toast.makeText(AddEmployeeActivity.this, "Please Select A Position", Toast.LENGTH_SHORT).show();
                } else if (phone.getText().toString().equals("")) {
                    phone.requestFocus();
                    phone.setError("This Is A Required Field");
                } else if (mail.getText().toString().equals("")) {
                    mail.requestFocus();
                    mail.setError("This Is A Required Field");
                } else if (login.getText().toString().equals("")) {
                    login.requestFocus();
                    login.setError("This Is A Required Field");
                } else if (password.getText().toString().equals("")) {
                    password.requestFocus();
                    password.setError("This Is A Required Field");
                } else if (employee.getText().toString().equals("")) {
                    employee.requestFocus();
                    employee.setError("This Is A Required Field");
                } else if (adapter.getCenter().isEmpty()) {
                    Toast.makeText(AddEmployeeActivity.this, "Please choose a center", Toast.LENGTH_SHORT).show();
                } else {
                    if (position.getSelectedItem().toString().equals("Counsellor")) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("counsellor").child(employee.getText().toString());
                        myRef.child("employee_name").setValue(name.getText().toString());
                        myRef.child("mobile_number").setValue(phone.getText().toString());
                        myRef.child("mail").setValue(mail.getText().toString());
                        myRef.child("login_id").setValue(login.getText().toString());
                        myRef.child("password").setValue(password.getText().toString());
                        myRef.child("centers").setValue(adapter.getCenter());
                    } else if (position.getSelectedItem().toString().equals("Trainer")) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("trainer").child(employee.getText().toString());
                        myRef.child("employee_name").setValue(name.getText().toString());
                        myRef.child("mobile_number").setValue(phone.getText().toString());
                        myRef.child("mail").setValue(mail.getText().toString());
                        myRef.child("login_id").setValue(login.getText().toString());
                        myRef.child("password").setValue(password.getText().toString());
                        myRef.child("centers").setValue(adapter.getCenter());
                    }
                    mAuth.createUserWithEmailAndPassword(mail.getText().toString(), password.getText().toString());
                    Toast.makeText(getBaseContext(), "Employee Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), EmployeesListActivity.class));
                }
            }
        });
    }
    private void generate_random() {
        n = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        validate_random();
    }
    private void validate_random() {
        if (position.getSelectedItem().toString().equals("Counsellor")) {
            db = FirebaseDatabase.getInstance().getReference("counsellor");
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
        } else if (position.getSelectedItem().toString().equals("Trainer")) {
            db = FirebaseDatabase.getInstance().getReference("trainer");
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
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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
                        case "admin":
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