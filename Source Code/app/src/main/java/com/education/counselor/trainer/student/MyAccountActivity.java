/*
*************************************
*      Author:Yogesh Sharma         *
*************************************
-------------------------------------------------------
| Details of the current user logged in in the system  |
--------------------------------------------------------
*/
package com.education.counselor.trainer.student;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.education.counselor.trainer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
public class MyAccountActivity extends AppCompatActivity {
    FirebaseUser user;
    String email;
    DatabaseReference reference;
    TextView id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account3);
        id = findViewById(R.id.id);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            email = user.getEmail();
        reference = FirebaseDatabase.getInstance().getReference("student");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("mail").getValue(String.class), email)) {
                        id.setText(ds.getKey());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
