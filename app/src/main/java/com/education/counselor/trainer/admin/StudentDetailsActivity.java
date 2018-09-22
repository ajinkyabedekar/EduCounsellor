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

public class StudentDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText name, mobile_number, mail, total_fee, department, total_fee_submitted, date_of_fee_1, date_of_fee_2, date_of_fee_3, date_of_fee_4, payment_1, payment_2, payment_3, payment_4, reference_id_1, reference_id_2, reference_id_3, reference_id_4, student_id;
    Spinner payment_type, payment_1_mode, payment_2_mode, payment_3_mode, payment_4_mode, student_year;
    Button transfer_payment, submit, delete;
    String status_of_payment = "PENDING";
    DatabaseReference studentData;
    private String n = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        name = findViewById(R.id.name);
        mobile_number = findViewById(R.id.mobile_number);
        mail = findViewById(R.id.mail);
        total_fee = findViewById(R.id.total_fee);
        department = findViewById(R.id.department);
        total_fee_submitted = findViewById(R.id.total_fee_submitted);
        date_of_fee_1 = findViewById(R.id.date_of_fee_1);
        date_of_fee_2 = findViewById(R.id.date_of_fee_2);
        date_of_fee_3 = findViewById(R.id.date_of_fee_3);
        date_of_fee_4 = findViewById(R.id.date_of_fee_4);
        payment_1 = findViewById(R.id.payment_1);
        payment_2 = findViewById(R.id.payment_2);
        payment_3 = findViewById(R.id.payment_3);
        payment_4 = findViewById(R.id.payment_4);
        reference_id_1 = findViewById(R.id.reference_id_1);
        reference_id_2 = findViewById(R.id.reference_id_2);
        reference_id_3 = findViewById(R.id.reference_id_3);
        reference_id_4 = findViewById(R.id.reference_id_4);
        student_id = findViewById(R.id.student_id);
        payment_type = findViewById(R.id.payment_type);
        payment_1_mode = findViewById(R.id.payment_1_mode);
        payment_2_mode = findViewById(R.id.payment_2_mode);
        payment_3_mode = findViewById(R.id.payment_3_mode);
        payment_4_mode = findViewById(R.id.payment_4_mode);
        student_year = findViewById(R.id.student_year);
        transfer_payment = findViewById(R.id.transfer_payment);
        submit = findViewById(R.id.submit);
        delete = findViewById(R.id.delete);
        payment_type.setOnItemSelectedListener(this);
        payment_1_mode.setOnItemSelectedListener(this);
        payment_2_mode.setOnItemSelectedListener(this);
        payment_3_mode.setOnItemSelectedListener(this);
        payment_4_mode.setOnItemSelectedListener(this);
        student_year.setOnItemSelectedListener(this);
        final List<String> payment_type_list = new ArrayList<>();
        final List<String> payment_mode_1_list = new ArrayList<>();
        final List<String> payment_mode_2_list = new ArrayList<>();
        final List<String> payment_mode_3_list = new ArrayList<>();
        final List<String> payment_mode_4_list = new ArrayList<>();
        final List<String> student_year_list = new ArrayList<>();
        payment_type_list.add("Payment Type");
        payment_type_list.add("Installment");
        payment_type_list.add("Full");
        payment_mode_1_list.add("Payment Mode 1");
        payment_mode_1_list.add("NEFT");
        payment_mode_1_list.add("PayTM");
        payment_mode_1_list.add("Online");
        payment_mode_1_list.add("Cash");
        payment_mode_2_list.add("Payment Mode 2");
        payment_mode_2_list.add("NEFT");
        payment_mode_2_list.add("PayTM");
        payment_mode_2_list.add("Online");
        payment_mode_2_list.add("Cash");
        payment_mode_3_list.add("Payment Mode 3");
        payment_mode_3_list.add("NEFT");
        payment_mode_3_list.add("PayTM");
        payment_mode_3_list.add("Online");
        payment_mode_3_list.add("Cash");
        payment_mode_4_list.add("Payment Mode 4");
        payment_mode_4_list.add("NEFT");
        payment_mode_4_list.add("PayTM");
        payment_mode_4_list.add("Online");
        payment_mode_4_list.add("Cash");
        student_year_list.add("Student Year");
        student_year_list.add("First");
        student_year_list.add("Second");
        student_year_list.add("Third");
        student_year_list.add("Fourth");
        ArrayAdapter<String> payment_type_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, payment_type_list);
        ArrayAdapter<String> payment_mode_1_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, payment_mode_1_list);
        ArrayAdapter<String> payment_mode_2_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, payment_mode_2_list);
        ArrayAdapter<String> payment_mode_3_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, payment_mode_3_list);
        ArrayAdapter<String> payment_mode_4_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, payment_mode_4_list);
        ArrayAdapter<String> student_year_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, student_year_list);
        payment_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payment_mode_1_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payment_mode_2_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payment_mode_3_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payment_mode_4_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        student_year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payment_type.setAdapter(payment_type_adapter);
        payment_1_mode.setAdapter(payment_mode_1_adapter);
        payment_2_mode.setAdapter(payment_mode_2_adapter);
        payment_3_mode.setAdapter(payment_mode_3_adapter);
        payment_4_mode.setAdapter(payment_mode_4_adapter);
        student_year.setAdapter(student_year_adapter);
        studentData = FirebaseDatabase.getInstance().getReference("student");
        student_id.setEnabled(false);
        studentData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("name").getValue(String.class), n)) {
                        name.setText(n);
                        mobile_number.setText(ds.child("mobile_number").getValue(String.class));
                        mail.setText(ds.child("mail").getValue(String.class));
                        total_fee.setText(ds.child("total_fee").getValue(String.class));
                        department.setText(ds.child("department").getValue(String.class));
                        total_fee_submitted.setText(ds.child("total_fee_submitted").getValue(String.class));
                        date_of_fee_1.setText(ds.child("date_of_fee_1").getValue(String.class));
                        date_of_fee_2.setText(ds.child("date_of_fee_2").getValue(String.class));
                        date_of_fee_3.setText(ds.child("date_of_fee_3").getValue(String.class));
                        date_of_fee_4.setText(ds.child("date_of_fee_4").getValue(String.class));
                        payment_1.setText(ds.child("payment_1").getValue(String.class));
                        payment_2.setText(ds.child("payment_2").getValue(String.class));
                        payment_3.setText(ds.child("payment_3").getValue(String.class));
                        payment_4.setText(ds.child("payment_4").getValue(String.class));
                        payment_type.setSelection(payment_type_list.indexOf(ds.child("payment_type").getValue(String.class)));
                        payment_1_mode.setSelection(payment_mode_1_list.indexOf(ds.child("payment_1_mode").getValue(String.class)));
                        payment_2_mode.setSelection(payment_mode_2_list.indexOf(ds.child("payment_2_mode").getValue(String.class)));
                        payment_3_mode.setSelection(payment_mode_3_list.indexOf(ds.child("payment_3_mode").getValue(String.class)));
                        payment_4_mode.setSelection(payment_mode_4_list.indexOf(ds.child("payment_4_mode").getValue(String.class)));
                        reference_id_1.setText(ds.child("payment_1_reference_id").getValue(String.class));
                        reference_id_2.setText(ds.child("payment_2_reference_id").getValue(String.class));
                        reference_id_3.setText(ds.child("payment_3_reference_id").getValue(String.class));
                        reference_id_4.setText(ds.child("payment_4_reference_id").getValue(String.class));
                        student_id.setText(ds.getKey());
                        student_year.setSelection(student_year_list.indexOf(ds.child("student_year").getValue(String.class)));
                        status_of_payment = ds.child("payment_status").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (status_of_payment.equals("PENDING")) {
            transfer_payment.setEnabled(true);
            transfer_payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    status_of_payment = "SUCCESS";
                    Toast.makeText(getBaseContext(), "Payment Transferred Successfully", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            transfer_payment.setEnabled(false);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e[] = new EditText[]{name, mobile_number, mail, total_fee, department, total_fee_submitted, date_of_fee_1, date_of_fee_2, date_of_fee_3, date_of_fee_4, payment_1, payment_2, payment_3, payment_4, reference_id_1, reference_id_2, reference_id_3, reference_id_4, student_id};
                if (check(e)) {
                    Toast.makeText(StudentDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else
                    update();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentData.child(student_id.getText().toString()).removeValue();
                Toast.makeText(getBaseContext(), "Student Deleted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), EditStudentActivity.class));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void update() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("student").child(student_id.getText().toString());
        myRef.child("name").setValue(name.getText().toString());
        myRef.child("mobile_number").setValue(mobile_number.getText().toString());
        myRef.child("mail").setValue(mail.getText().toString());
        myRef.child("total_fee").setValue(total_fee.getText().toString());
        myRef.child("department").setValue(department.getText().toString());
        myRef.child("total_fee_submitted").setValue(total_fee_submitted.getText().toString());
        myRef.child("date_of_fee_1").setValue(date_of_fee_1.getText().toString());
        myRef.child("date_of_fee_2").setValue(date_of_fee_2.getText().toString());
        myRef.child("date_of_fee_3").setValue(date_of_fee_3.getText().toString());
        myRef.child("date_of_fee_4").setValue(date_of_fee_4.getText().toString());
        myRef.child("payment_1").setValue(payment_1.getText().toString());
        myRef.child("payment_2").setValue(payment_2.getText().toString());
        myRef.child("payment_3").setValue(payment_3.getText().toString());
        myRef.child("payment_4").setValue(payment_4.getText().toString());
        myRef.child("payment_type").setValue(payment_type.getSelectedItem().toString());
        myRef.child("payment_1_mode").setValue(payment_1_mode.getSelectedItem().toString());
        myRef.child("payment_2_mode").setValue(payment_2_mode.getSelectedItem().toString());
        myRef.child("payment_3_mode").setValue(payment_3_mode.getSelectedItem().toString());
        myRef.child("payment_4_mode").setValue(payment_4_mode.getSelectedItem().toString());
        myRef.child("payment_1_reference_id").setValue(reference_id_1.getText().toString());
        myRef.child("payment_2_reference_id").setValue(reference_id_2.getText().toString());
        myRef.child("payment_3_reference_id").setValue(reference_id_3.getText().toString());
        myRef.child("payment_4_reference_id").setValue(reference_id_4.getText().toString());
        myRef.child("student_year").setValue(student_year.getSelectedItem().toString());
        myRef.child("payment_status").setValue(status_of_payment);
        Toast.makeText(getBaseContext(), "Student Details Updated Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getBaseContext(), EditStudentActivity.class));
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