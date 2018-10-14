package com.education.counselor.trainer.employee.trainer.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.counsellor.CounsellorDashboardActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RankethonMeetActivity extends AppCompatActivity {
    EditText summary;
    Button submit;
    String n = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankethon_meet);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        summary = findViewById(R.id.summary);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (summary.getText().toString().equals("")) {
                    summary.requestFocus();
                    summary.setError("This Is A Required Field");
                } else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("rankethon_meet_summary").push();
                    reference.child("summary").setValue(summary.getText().toString());
                    reference.child("center").setValue(n);
                    Toast.makeText(getBaseContext(), "Summary Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), CounsellorDashboardActivity.class));
                }
            }
        });
    }
}