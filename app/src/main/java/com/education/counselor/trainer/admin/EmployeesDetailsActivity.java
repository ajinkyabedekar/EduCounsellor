package com.education.counselor.trainer.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeesDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText name, position, mail, login, password, employee;
    Spinner centers;
    Button submit, delete;
    DatabaseReference studentData;
    private String n = "", key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_details);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        name = findViewById(R.id.name);
        position = findViewById(R.id.position);
        mail = findViewById(R.id.mail);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        employee = findViewById(R.id.employee);
        centers = findViewById(R.id.centers);
        submit = findViewById(R.id.submit);
        delete = findViewById(R.id.delete);
        final List<String> list = new ArrayList<>();
        list.add("List1");
        list.add("List2");
        ArrayAdapter<String> centers_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        centers.setAdapter(centers_adapter);
        studentData = FirebaseDatabase.getInstance().getReference("employee");
        employee.setEnabled(false);
        studentData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("employee_name").getValue(String.class), n)) {
                        name.setText(n);
                        position.setText(ds.child("position").getValue(String.class));
                        mail.setText(ds.child("mail").getValue(String.class));
                        login.setText(ds.child("login_id").getValue(String.class));
                        password.setText(ds.child("password").getValue(String.class));
                        employee.setText(ds.getKey());
                        centers.setSelection(list.indexOf(ds.child("centers").getValue(String.class)));
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
                EditText e[] = new EditText[]{name, position, mail, login, password, employee};
                if (check(e)) {
                    Toast.makeText(EmployeesDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else
                    update();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentData.child(employee.getText().toString()).removeValue();
                Toast.makeText(getBaseContext(), "Employee Deleted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), EmployeesListActivity.class));
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
        DatabaseReference myRef = database.getReference("employee").child(employee.getText().toString());
        myRef.child("employee_name").setValue(name.getText().toString());
        myRef.child("position").setValue(position.getText().toString());
        myRef.child("mail").setValue(mail.getText().toString());
        myRef.child("login_id").setValue(login.getText().toString());
        myRef.child("centers").setValue(centers.getSelectedItem().toString());
        Toast.makeText(getBaseContext(), "Employee Added Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getBaseContext(), EmployeesListActivity.class));
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