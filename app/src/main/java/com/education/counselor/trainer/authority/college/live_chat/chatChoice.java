package com.education.counselor.trainer.authority.college.live_chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.authority.college.live_chat.list.ListActivity;

public class chatChoice extends AppCompatActivity {
    Button admin, councellor, trainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_choice);
        admin = findViewById(R.id.admin);
        councellor = findViewById(R.id.counsellor);
        trainer = findViewById(R.id.trainer);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ListActivity.class);
                i.putExtra("name", "admin");
                startActivity(i);
            }
        });
        councellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ListActivity.class);
                i.putExtra("name", "counsellor");
                startActivity(i);

            }
        });
        trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ListActivity.class);
                i.putExtra("name", "trainer");
                startActivity(i);
            }
        });

    }
}