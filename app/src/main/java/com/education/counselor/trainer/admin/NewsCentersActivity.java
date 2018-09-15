package com.education.counselor.trainer.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;

public class NewsCentersActivity extends AppCompatActivity {
    Button center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_centers);
        center = findViewById(R.id.center);
        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), NewsListActivity.class));
            }
        });
    }
}