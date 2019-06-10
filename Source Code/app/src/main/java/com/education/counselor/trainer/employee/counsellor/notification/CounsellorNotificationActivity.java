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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class CounsellorNotificationActivity extends AppCompatActivity {
    private EditText message;
    private DatabaseReference db;
    private List<String> studentsId = new ArrayList<>();
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
}