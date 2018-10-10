package com.education.counselor.trainer.counsellor.responsible_centers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.counsellor.responsible_centers.list.ResponsibleCentersListActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ResponsibleCentersDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText name, location, mail, head, password;
    Button submit, delete;
    DatabaseReference centerData;
    private String n = "", key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsible_centers_details);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        mail = findViewById(R.id.mail);
        head = findViewById(R.id.head);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        delete = findViewById(R.id.delete);
        centerData = FirebaseDatabase.getInstance().getReference("centers");
        centerData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("name").getValue(String.class), n)) {
                        key = ds.getKey();
                        name.setText(n);
                        location.setText(ds.child("location").getValue(String.class));
                        mail.setText(ds.child("mail").getValue(String.class));
                        head.setText(ds.child("head").getValue(String.class));
                        password.setText(ds.child("unique_password").getValue(String.class));
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
                EditText e[] = new EditText[]{name, location, mail, head, password};
                if (check(e)) {
                    Toast.makeText(ResponsibleCentersDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else
                    update();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                centerData.child(key).removeValue();
                Toast.makeText(getBaseContext(), "Center Deleted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), ResponsibleCentersListActivity.class));
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
        DatabaseReference myRef = database.getReference("centers").child(key);
        myRef.child("name").setValue(name.getText().toString());
        myRef.child("location").setValue(location.getText().toString());
        myRef.child("mail").setValue(mail.getText().toString());
        myRef.child("head").setValue(head.getText().toString());
        myRef.child("unique_password").setValue(password.getText().toString());
        Toast.makeText(getBaseContext(), "Centers Added Successfully" + key, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getBaseContext(), ResponsibleCentersListActivity.class));
    }

    private boolean check(EditText[] e) {
        for (EditText ed : e) {
            if (TextUtils.isEmpty(ed.getText().toString())) {
                ed.setError("This Is A Required Field");
                return true;
            }
        }
        return false;
    }
}