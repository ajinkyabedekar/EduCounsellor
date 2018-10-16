package com.education.counselor.trainer.employee.trainer.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.admin.student.edit.EditStudentActivity;
import com.education.counselor.trainer.employee.trainer.news.list.NewsListActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class NewsDetailsActivity extends AppCompatActivity {
    EditText name, motto, drive, date;
    Button submit, delete;
    DatabaseReference studentData;
    private String n = "", key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details2);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        name = findViewById(R.id.name);
        motto = findViewById(R.id.motto);
        drive = findViewById(R.id.drive);
        date = findViewById(R.id.date);
        submit = findViewById(R.id.submit);
        delete = findViewById(R.id.delete);
        studentData = FirebaseDatabase.getInstance().getReference("news");
        studentData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("name").getValue(String.class), n)) {
                        name.setText(n);
                        motto.setText(ds.child("motto").getValue(String.class));
                        drive.setText(ds.child("google_drive_link").getValue(String.class));
                        date.setText(ds.child("date").getValue(String.class));
                        key = ds.getKey();
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
                EditText e[] = new EditText[]{name, motto, drive, date};
                if (check(e)) {
                    Toast.makeText(NewsDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else
                    update();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentData.child(key).removeValue();
                Toast.makeText(getBaseContext(), "Placement Deleted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), EditStudentActivity.class));
            }
        });
    }

    private void update() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("news").push();
        myRef.child("name").setValue(name.getText().toString());
        myRef.child("motto").setValue(motto.getText().toString());
        myRef.child("google_drive_link").setValue(drive.getText().toString());
        myRef.child("date").setValue(date.getText().toString());
        Toast.makeText(getBaseContext(), "News Updated Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getBaseContext(), NewsListActivity.class));
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