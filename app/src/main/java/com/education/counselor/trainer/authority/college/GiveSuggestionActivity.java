package com.education.counselor.trainer.authority.college;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GiveSuggestionActivity extends AppCompatActivity {
    EditText suggestion;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_suggestion);
        suggestion = findViewById(R.id.suggestion);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (suggestion.getText().toString().equals("")) {
                    suggestion.requestFocus();
                    suggestion.setError("This Is A Required Field");
                } else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("suggestions").push();
                    reference.setValue(suggestion.getText().toString());
                    Toast.makeText(getBaseContext(), "Suggestion Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), AuthorityCollegeDashboardActivity.class));
                }
            }
        });
    }
}