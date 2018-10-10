package com.education.counselor.trainer.authority.school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.authority.college.GiveSuggestionActivity;
import com.education.counselor.trainer.authority.college.project_list.ProjectListActivity;
import com.education.counselor.trainer.authority.college.startup_list.StartupListActivity;
import com.education.counselor.trainer.authority.college.suggestion_list.SuggestionListActivity;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AuthoritySchoolDashboardActivity extends AppCompatActivity {
    Button logout, give_suggestions, project_list, startup_list, suggestion_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_school_dashboard);
        logout = findViewById(R.id.logout);
        give_suggestions = findViewById(R.id.give_suggestions);
        project_list = findViewById(R.id.project_list);
        startup_list = findViewById(R.id.startup_list);
        suggestion_list = findViewById(R.id.suggestion_list);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
            }
        });
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