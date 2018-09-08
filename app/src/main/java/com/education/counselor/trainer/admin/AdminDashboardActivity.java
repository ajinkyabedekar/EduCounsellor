package com.education.counselor.trainer.admin;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.education.counselor.trainer.R;
public class AdminDashboardActivity extends AppCompatActivity {
    Button add_student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        add_student = findViewById(R.id.add_student);
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddStudentActivity.class));
            }
        });
    }
}