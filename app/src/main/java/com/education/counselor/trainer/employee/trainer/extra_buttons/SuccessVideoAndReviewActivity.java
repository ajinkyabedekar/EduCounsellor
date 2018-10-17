package com.education.counselor.trainer.employee.trainer.extra_buttons;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.trainer.TrainerDashboardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SuccessVideoAndReviewActivity extends AppCompatActivity {
    EditText media, review;
    Button submit;
    DatabaseReference reference;
    FirebaseUser user;
    String email, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_video_and_review);
        media = findViewById(R.id.media);
        review = findViewById(R.id.review);
        submit = findViewById(R.id.submit);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }
        reference = FirebaseDatabase.getInstance().getReference("trainer");
        reference.addValueEventListener(new ValueEventListener() {
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
                if (review.getText().toString().equals("")) {
                    review.requestFocus();
                    review.setError("This Is A Required Field");
                } else if (review.getText().toString().length() < 200) {
                    review.requestFocus();
                    review.setError("Review Should Be At Least Of 200 Words");
                } else if (media.getText().toString().equals("")) {
                    media.requestFocus();
                    media.setError("This Is A Required Field");
                } else {
                    reference = reference.child(key).child("success_video").push();
                    reference.child("success_video_link").setValue(media.getText().toString());
                    reference.child("review").setValue(review.getText().toString());
                    Toast.makeText(getBaseContext(), "Success Video Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), TrainerDashboardActivity.class));
                }
            }
        });
    }
}