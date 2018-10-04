package com.education.counselor.trainer.authority.school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.authority.college.GiveSuggestionActivity;
import com.education.counselor.trainer.authority.college.ProjectListActivity;
import com.education.counselor.trainer.authority.college.StartupListActivity;
import com.education.counselor.trainer.authority.college.SuggestionListActivity;

public class AuthoritySchoolDashboardActivity extends AppCompatActivity {
    Button give_suggestions, project_list, startup_list, suggestion_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_school_dashboard);
        give_suggestions = findViewById(R.id.give_suggestions);
        project_list = findViewById(R.id.project_list);
        startup_list = findViewById(R.id.startup_list);
        suggestion_list = findViewById(R.id.suggestion_list);
        give_suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), GiveSuggestionActivity.class));
            }
        });
        project_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ProjectListActivity.class));
            }
        });
        startup_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), StartupListActivity.class));
            }
        });
        suggestion_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SuggestionListActivity.class));
            }
        });
    }
}