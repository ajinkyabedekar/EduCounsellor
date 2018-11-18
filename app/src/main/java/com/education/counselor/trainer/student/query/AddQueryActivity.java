/*
*************************************
*      Author:Yogesh Sharma         *
*************************************
*/
package com.education.counselor.trainer.student.query;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.student.StudentDashboardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
public class AddQueryActivity extends AppCompatActivity {
    EditText query;
    Button submit;
    DatabaseReference db;
    FirebaseUser user;
    String email, key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_query);
        query = findViewById(R.id.query);
        submit = findViewById(R.id.submit);
        db = FirebaseDatabase.getInstance().getReference("student");
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("mail").getValue(String.class), email)) {
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
            public void onClick(View v) {
                if (query.getText().toString().equals("")) {
                    query.requestFocus();
                    query.setError("This Is A Required Field");
                } else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("student").child(key).child("query").push();
                    reference.child("query").setValue(query.getText().toString());
                    reference.child("status").setValue("PENDING");
                    Toast.makeText(getBaseContext(), "Query Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), StudentDashboardActivity.class));
                }
            }
        });
    }
}
