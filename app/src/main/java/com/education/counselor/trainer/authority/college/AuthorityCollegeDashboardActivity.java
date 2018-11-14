package com.education.counselor.trainer.authority.college;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.authority.college.internship_list.InternshipListActivity;
import com.education.counselor.trainer.authority.college.live_chat.chatChoice;
import com.education.counselor.trainer.authority.college.placement_list.PlacementListActivity;
import com.education.counselor.trainer.authority.college.project_list.ProjectListActivity;
import com.education.counselor.trainer.authority.college.startup_list.StartupListActivity;
import com.education.counselor.trainer.authority.college.suggestion_list.SuggestionListActivity;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
public class AuthorityCollegeDashboardActivity extends AppCompatActivity {
    Button logout, give_suggestions, internship_list, placement_list, project_list, startup_list, suggestion_list, chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_college_dashboard);
        logout = findViewById(R.id.logout);
        give_suggestions = findViewById(R.id.give_suggestions);
        internship_list = findViewById(R.id.internship_list);
        startup_list = findViewById(R.id.startup_list);
        placement_list = findViewById(R.id.placement_list);
        project_list = findViewById(R.id.project_list);
        chat = findViewById(R.id.chat);
        FirebaseMessaging.getInstance().subscribeToTopic("startclass");
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
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), chatChoice.class));
            }
        });
    }
}