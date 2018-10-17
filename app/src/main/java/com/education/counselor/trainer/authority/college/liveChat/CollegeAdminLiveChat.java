package com.education.counselor.trainer.authority.college.liveChat;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class CollegeAdminLiveChat extends AppCompatActivity {
    RecyclerView recyclerView;
    Button send;
    DatabaseReference db,ref;
    chatAdapter adapter;
    ProgressBar pg;
    Context mContext;
    EditText text;
    private DatabaseReference messagesRef;
    String email,name, key="";
    private ArrayList<chatMessages> details = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_admin_live_chat);
        send=findViewById(R.id.send);
        text = findViewById(R.id.message);
        pg=findViewById(R.id.progress);
        recyclerView=findViewById(R.id.recycle);
        mContext = this;
        send.setEnabled(false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent i=getIntent();
        if (user != null) {
            email = user.getEmail();
            if(i.hasExtra("key"))
                key=i.getStringExtra("key");
            if(i.hasExtra("name"))
                name=i.getStringExtra("name");

        }
        textListener();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesRef = FirebaseDatabase.getInstance().getReference("college_authority").child(key).child("live_chat");
        pg.setVisibility(View.VISIBLE);
        db = FirebaseDatabase.getInstance().getReference("college_authority");
        pg.setVisibility(View.VISIBLE);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("mail").getValue(String.class), email)) {
                        key = ds.getKey();
                        name = ds.child("name").getValue(String.class);
                        for (DataSnapshot d : ds.child("live_chat").getChildren()) {
                            chatMessages s = new chatMessages();
                            s.setName(d.child("name").getValue(String.class));
                            s.setDate(d.getKey());
                            s.setMessage(d.child("message").getValue(String.class));
                            details.add(s);
                        }
                    }
                }
                if (details.size() == 0) {
                    Toast.makeText(getBaseContext(), "No Internships Found", Toast.LENGTH_SHORT).show();
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
                if (text.getText().toString().equals(""))
                    Toast.makeText(mContext, "Please enter a message", Toast.LENGTH_SHORT).show();
                else {
                    Date date = new Date();
                    db = FirebaseDatabase.getInstance().getReference("college_authority").child(key).child("live_chat").child(date.toString());
                    ref = FirebaseDatabase.getInstance().getReference("admin").child(key).child("live_chat").child(date.toString());
                    db.child("name").setValue(name);
                    db.child("message").setValue(text.getText().toString());
                    ref.child("name").setValue(name);
                    ref.child("message").setValue(text.getText().toString());
                }
            }
        });

        pg.setVisibility(View.GONE);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatMessages chat = new chatMessages();
                chat.setMessage(text.getText().toString());
                chat.setDate(new Date().toString());
                chat.setName(name);
                messagesRef.push().setValue(chat);
            }
        });

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