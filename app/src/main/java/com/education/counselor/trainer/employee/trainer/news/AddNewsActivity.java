package com.education.counselor.trainer.employee.trainer.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.trainer.news.list.NewsListActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewsActivity extends AppCompatActivity {
    EditText name, motto, drive, date;
    Button submit;
    private String n = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news2);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        name = findViewById(R.id.name);
        motto = findViewById(R.id.motto);
        drive = findViewById(R.id.drive);
        date = findViewById(R.id.date);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    name.requestFocus();
                    name.setError("This Is A Required Field");
                } else if (motto.getText().toString().equals("")) {
                    motto.requestFocus();
                    motto.setError("This Is A Required Field");
                } else if (drive.getText().toString().equals("")) {
                    drive.requestFocus();
                    drive.setError("This Is A Required Field");
                } else if (date.getText().toString().equals("")) {
                    date.requestFocus();
                    date.setError("This Is A Required Field");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("news").push();
                    myRef.child("name").setValue(name.getText().toString());
                    myRef.child("motto").setValue(motto.getText().toString());
                    myRef.child("google_drive_link").setValue(drive.getText().toString());
                    myRef.child("date").setValue(date.getText().toString());
                    myRef.child("center").setValue(n);
                    Toast.makeText(getBaseContext(), "News Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), NewsListActivity.class));
                }
            }
        });
    }
}