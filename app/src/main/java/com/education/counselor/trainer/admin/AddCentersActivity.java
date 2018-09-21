package com.education.counselor.trainer.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCentersActivity extends AppCompatActivity {
    EditText name, location, mail, head, password;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_centers);
        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        mail = findViewById(R.id.mail);
        head = findViewById(R.id.head);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    name.requestFocus();
                    name.setError("This Is A Required Field");
                } else if (location.getText().toString().equals("")) {
                    location.requestFocus();
                    location.setError("This Is A Required Field");
                } else if (mail.getText().toString().equals("")) {
                    mail.requestFocus();
                    mail.setError("This Is A Required Field");
                } else if (head.getText().toString().equals("")) {
                    head.requestFocus();
                    head.setError("This Is A Required Field");
                } else if (password.getText().toString().equals("")) {
                    password.requestFocus();
                    password.setError("This Is A Required Field");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("centers").push();
                    myRef.child("name").setValue(name.getText().toString());
                    myRef.child("location").setValue(location.getText().toString());
                    myRef.child("mail").setValue(mail.getText().toString());
                    myRef.child("head").setValue(head.getText().toString());
                    myRef.child("unique_password").setValue(password.getText().toString());
                    Toast.makeText(getBaseContext(), "Centers Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), CentersListActivity.class));
                }
            }
        });
    }
}