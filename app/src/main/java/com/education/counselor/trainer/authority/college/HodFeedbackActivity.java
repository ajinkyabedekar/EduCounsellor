package com.education.counselor.trainer.authority.college;

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
public class HodFeedbackActivity extends AppCompatActivity {
    EditText feedback;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_feedback);
        feedback = findViewById(R.id.suggestion);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedback.getText().toString().equals("")) {
                    feedback.requestFocus();
                    feedback.setError("This Is A Required Field");
                } else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("feedback").push();
                    reference.child("suggestion").setValue(feedback.getText().toString());
                    reference.child("status").setValue("PENDING");
                    Toast.makeText(getBaseContext(), "Feedback Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), AuthorityCollegeDashboardActivity.class));
                }
            }
        });
    }
}