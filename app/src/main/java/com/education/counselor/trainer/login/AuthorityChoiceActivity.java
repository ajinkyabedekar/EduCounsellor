package com.education.counselor.trainer.login;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.education.counselor.trainer.R;
public class AuthorityChoiceActivity extends AppCompatActivity {
    Button school, college;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_choice);
        school = findViewById(R.id.school);
        college = findViewById(R.id.college);
        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AuthoritySchoolLoginActivity.class));
            }
        });
        college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AuthorityCollegeLoginActivity.class));
            }
        });
    }
}