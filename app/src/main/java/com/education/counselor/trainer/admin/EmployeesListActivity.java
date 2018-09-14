package com.education.counselor.trainer.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;

public class EmployeesListActivity extends AppCompatActivity {
    Button add_employee, employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_list);
        add_employee = findViewById(R.id.add_employee);
        employee = findViewById(R.id.employee);
        add_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddEmployeeActivity.class));
            }
        });
        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), EmployeesDetailsActivity.class));
            }
        });
    }
}