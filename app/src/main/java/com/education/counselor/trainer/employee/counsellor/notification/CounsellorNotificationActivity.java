package com.education.counselor.trainer.employee.counsellor.notification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CounsellorNotificationActivity extends AppCompatActivity {
    private Button send;
    private EditText message;
    private DatabaseReference db;
    private List<String> studentsId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_notification);
        send = findViewById(R.id.sendNotif);
        message = findViewById(R.id.notifMessage);
        final String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
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
                        db.child(id + "/Notification/" + uid + "/message").setValue(message.getText().toString());
                    message.setText(null);
                }
            }
        });

    }
}
