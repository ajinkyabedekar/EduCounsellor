package com.education.counselor.trainer.authority.college;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;

public class AuthorityCollegeDashboardActivity extends AppCompatActivity {
    Button give_suggestions, internship_list, placement_list, project_list, startup_list, suggestion_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_college_dashboard);
        give_suggestions = findViewById(R.id.give_suggestions);
        internship_list = findViewById(R.id.internship_list);
        placement_list = findViewById(R.id.placement_list);
        project_list = findViewById(R.id.project_list);
        startup_list = findViewById(R.id.startup_list);
        suggestion_list = findViewById(R.id.suggestion_list);
        give_suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), GiveSuggestionActivity.class));
            }
        });
        internship_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), InternshipListActivity.class));
            }
        });
        placement_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), PlacementListActivity.class));
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