package com.education.counselor.trainer.employee.trainer.live_chat.liveChatAdmin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class adminLiveChat extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton send;
    DatabaseReference db, ref;
    chatAdapter adapter;
    ProgressBar pg;
    Context mContext;
    EditText text;
    String email, name, senderKey, receiverKey;
    ScrollView mScrollView;
    private ArrayList<chatMessages> details = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_trainer_live_chat);
        send = findViewById(R.id.send);
        text = findViewById(R.id.message);
        pg = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycle);
        mScrollView = findViewById(R.id.sc);
        mContext = this;
        send.setEnabled(false);
        mScrollView.fullScroll(View.FOCUS_DOWN);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent i = getIntent();
        if (user != null) {
            email = user.getEmail();
            if (i.hasExtra("key"))
                receiverKey = i.getStringExtra("key");
            if (i.hasExtra("senderKey"))
                senderKey = i.getStringExtra("senderKey");
            if (i.hasExtra("user"))
                name = i.getStringExtra("user");
        }

        textListener();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pg.setVisibility(View.VISIBLE);
        db = FirebaseDatabase.getInstance().getReference("admin/" + receiverKey + "/live_chat/trainer/" + senderKey);
        pg.setVisibility(View.VISIBLE);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                details.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    chatMessages s = new chatMessages();
                    s.setName(ds.child("name").getValue(String.class));
                    s.setDate(ds.getKey());
                    s.setMessage(ds.child("message").getValue(String.class));
                    details.add(s);
                }
                adapter = new chatAdapter(mContext, details);
                pg.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressLint("SimpleDateFormat") String date = (new SimpleDateFormat("dd MMMM yyyy hh:mm:ss a").format(new Date()));
                db.child(date);
                db.child(date + "/name").setValue(name);
                db.child(date + "/message").setValue(text.getText().toString());
                text.setText(null);
            }
        });
        pg.setVisibility(View.GONE);


    }

    private void textListener() {
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    send.setEnabled(true);
                } else {
                    send.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}