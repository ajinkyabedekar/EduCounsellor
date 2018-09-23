package com.education.counselor.trainer.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class StartupDetailsActivity extends AppCompatActivity {
    DatabaseReference studentData;
    private String n = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_details);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        studentData = FirebaseDatabase.getInstance().getReference("startup");
        studentData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void update() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("placement").child("Student ID");
        Toast.makeText(getBaseContext(), "Startup Updated Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getBaseContext(), StartupListActivity.class));
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