package com.education.counselor.trainer.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddEmployeeActivity extends AppCompatActivity {
    EditText name, position, mail, login, password, employee;
    Spinner centers;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        name = findViewById(R.id.name);
        position = findViewById(R.id.position);
        mail = findViewById(R.id.mail);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        employee = findViewById(R.id.employee);
        centers = findViewById(R.id.centers);
        submit = findViewById(R.id.submit);
        List<String> list = new ArrayList<String>();
        list.add("List1");
        list.add("List2");
        ArrayAdapter<String> centers_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        centers.setAdapter(centers_adapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    name.requestFocus();
                    name.setError("This Is A Required Field");
                } else if (position.getText().toString().equals("")) {
                    position.requestFocus();
                    position.setError("This Is A Required Field");
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
                } else if (centers.getSelectedItem().toString().equals("")) {
                    centers.requestFocus();
                    Toast.makeText(getBaseContext(), "Please Select A Center", Toast.LENGTH_SHORT).show();
                } else {
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
            }
        });
    }
}