package com.education.counselor.trainer.authority.school;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.authority.school.live_chat.chatChoice;
import com.education.counselor.trainer.authority.school.project_list.ProjectListActivity;
import com.education.counselor.trainer.authority.school.rating.RatingListActivity;
import com.education.counselor.trainer.authority.school.startup_list.StartupListActivity;
import com.education.counselor.trainer.authority.school.suggestion_list.SuggestionListActivity;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;
public class AuthoritySchoolDashboardActivity extends AppCompatActivity {
    Button logout, give_suggestions, project_list, startup_list, suggestion_list, chat, rating;
    String access, email;
    int temp = 0;
    boolean flag = false;
    private boolean pressed = false;

    @Override
    public void onBackPressed() {
        if (pressed) {
            finishAffinity();
        }
        this.pressed = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                pressed = false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_school_dashboard);
        logout = findViewById(R.id.logout);
        give_suggestions = findViewById(R.id.give_suggestions);
        project_list = findViewById(R.id.project_list);
        startup_list = findViewById(R.id.startup_list);
        suggestion_list = findViewById(R.id.suggestion_list);
        chat = findViewById(R.id.chat);
        rating = findViewById(R.id.rating);
        FirebaseMessaging.getInstance().subscribeToTopic("startclass");
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finishAffinity();
            }
        });
        give_suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), GiveSuggestionActivity.class));
                finishAffinity();
            }
        });
        project_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ProjectListActivity.class));
                finishAffinity();
            }
        });
        startup_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), StartupListActivity.class));
                finishAffinity();
            }
        });
        suggestion_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SuggestionListActivity.class));
                finishAffinity();
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), chatChoice.class));
                finishAffinity();
            }
        });
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), RatingListActivity.class));
                finishAffinity();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            checkUser(user);
        }
    }

    private void checkUser(final FirebaseUser user) {
        email = user.getEmail();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : ds.getChildren()) {
                        if (Objects.equals(dataSnapshot1.child("mail").getValue(String.class), email)) {
                            access = ds.getKey();
                            flag = true;
                            break;
                        }
                    }
                    if (flag)
                        break;
                }
                if (access != null) {
                    switch (access) {
                        case "school_authority":
                            return;
                        default:
                            temp = 1;
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if (temp == 1) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            finishAffinity();
        }
    }
}