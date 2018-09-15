package com.education.counselor.trainer.admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.education.counselor.trainer.R;

public class EditCounsellorActivity extends AppCompatActivity {
    TextView name;
    EditText contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counsellor);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
    }
}