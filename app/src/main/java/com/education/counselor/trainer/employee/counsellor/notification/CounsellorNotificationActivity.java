package com.education.counselor.trainer.employee.counsellor.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.counsellor.CounsellorDashboardActivity;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CounsellorNotificationActivity extends AppCompatActivity {
    private EditText message;
    private DatabaseReference db;
    private List<String> studentsId = new ArrayList<>();
    String access, email;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_notification);
        Button send = findViewById(R.id.sendNotif);
        message = findViewById(R.id.notifMessage);
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        db = FirebaseDatabase.getInstance().getReference("student");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                    studentsId.add(ds.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(message.getText()))
                    Toast.makeText(CounsellorNotificationActivity.this, "Please write a message", Toast.LENGTH_SHORT).show();
                else {
                    for (String id : studentsId)
                        db.child(id + "/Notification/" + name + "/message").setValue(message.getText().toString());
                    Toast.makeText(CounsellorNotificationActivity.this, "Notice Sent Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), CounsellorDashboardActivity.class));
                }
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
        db.addValueEventListener(new ValueEventListener() {
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
                        case "counsellor":
                            return;
                        default:
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getBaseContext(), LoginActivity.class));
                            finishAffinity();
                            Toast.makeText(getBaseContext(), "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Please Check Your Network Connection and Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), CounsellorDashboardActivity.class));
        finishAffinity();
    }
}