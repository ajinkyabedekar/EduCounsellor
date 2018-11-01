package com.education.counselor.trainer.admin.employee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.admin.employee.list.EmployeesListActivity;
import com.education.counselor.trainer.admin.employee.multiselectionspinner.MultiSelectionSpinner;
import com.education.counselor.trainer.admin.employee.multiselectionspinner.MultiSelectionSpinnerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeesDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final List<MultiSelectionSpinner> centerList = new ArrayList<>();
    EditText name, position, mail, login, password, employee;
    TextView assigned;
    Spinner centers;
    Button submit, delete;
    DatabaseReference emp;
    private String n = "", pos;
    private MultiSelectionSpinner s = new MultiSelectionSpinner();
    private MultiSelectionSpinnerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_details);
        final Intent i = getIntent();
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
        assigned = findViewById(R.id.available);
        s.setText("Centers");
        centerList.add(s);
        position.setEnabled(false);
        emp = FirebaseDatabase.getInstance().getReference("centers");
        emp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    s = new MultiSelectionSpinner();
                    s.setText(snapshot.child("name").getValue(String.class));
                    centerList.add(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        adapter = new MultiSelectionSpinnerAdapter(this, 0, centerList);
        centers.setAdapter(adapter);
        emp = FirebaseDatabase.getInstance().getReference();
        employee.setEnabled(false);
        emp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot sd : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds : sd.getChildren()) {
                        if (Objects.equals(ds.child("employee_name").getValue(String.class), n)) {
                            pos = ds.getKey();
                            name.setText(n);
                            position.setText(pos);
                            mail.setText(ds.child("mail").getValue(String.class));
                            login.setText(ds.child("login_id").getValue(String.class));
                            password.setText(ds.child("password").getValue(String.class));
                            employee.setText(ds.getKey());
                            List retrievedList = (ArrayList) ds.child("centers").getValue();
                            Toast.makeText(EmployeesDetailsActivity.this, retrievedList + "", Toast.LENGTH_SHORT).show();
                            //List<MultiSelectionSpinner> newList=new ArrayList<>();
                        /*for(Object o: Objects.requireNonNull(retrievedList))
                        {
                            MultiSelectionSpinner mul=new MultiSelectionSpinner();
                            mul.setText((String)o);
                            centreList.remove(mul);
                        }
                        //Toast.makeText(EmployeesDetailsActivity.this, centreList.size()+"", Toast.LENGTH_SHORT).show();
                        MultiSelectionSpinner m;
                        m=new MultiSelectionSpinner();
                        m.setText("Center");
                        newList.add(m);

                        for (Object cname: Objects.requireNonNull(retrievedList))
                        {
                            m=new MultiSelectionSpinner();
                            m.setText((String)cname);

                            Iterable<MultiSelectionSpinner> iterable=centreList;
                            for(MultiSelectionSpinner mul:iterable)
                            {
                                if(mul.getText().equals(cname))
                                {
                                    m.setSelected(true);
                                    newList.add(m);
                                }
                                else
                                    newList.add(m);
                            }
                        }
                        adapter = new MultiSelectionSpinnerAdapter(EmployeesDetailsActivity.this, 0, newList, centers);
                        centers.setAdapter(adapter);*/
                            StringBuilder sb = new StringBuilder();
                            sb.append("Assigned Centers:\n\n");
                            if (!(retrievedList == null)) {
                                for (Object o : retrievedList)
                                    sb.append((String) o).append("\n");
                            }
                            assigned.setText(sb.toString());
                        }
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
                emp.child(employee.getText().toString()).removeValue();
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
        DatabaseReference myRef = database.getReference(pos).child(employee.getText().toString());
        myRef.child("employee_name").setValue(name.getText().toString());
        myRef.child("mail").setValue(mail.getText().toString());
        myRef.child("login_id").setValue(login.getText().toString());
        myRef.child("centers").setValue(adapter.getCenter());
        Toast.makeText(getBaseContext(), "Employee Updated Successfully", Toast.LENGTH_SHORT).show();
        finish();
        // startActivity(new Intent(getBaseContext(), EmployeesListActivity.class));
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