package com.education.counselor.trainer.employee.counsellor.responsible_centers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.counsellor.internship.list.InternshipListActivity;
import com.education.counselor.trainer.employee.counsellor.news.list.NewsListActivity;
import com.education.counselor.trainer.employee.counsellor.placement.list.PlacementListActivity;
import com.education.counselor.trainer.employee.counsellor.startup.list.StartupListActivity;

public class ResponsibleCentersDetailsActivity extends AppCompatActivity {
    String n = "";
    Button startup_list, news_list, placement_list, internship_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsible_centers_details);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        startup_list = findViewById(R.id.startup_list);
        news_list = findViewById(R.id.news_list);
        placement_list = findViewById(R.id.placement_list);
        internship_list = findViewById(R.id.internship_list);
        startup_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getBaseContext(), StartupListActivity.class);
                in.putExtra("name", n);
                startActivity(in);
            }
        });
        news_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getBaseContext(), NewsListActivity.class);
                in.putExtra("name", n);
                startActivity(in);
            }
        });
        placement_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getBaseContext(), PlacementListActivity.class);
                in.putExtra("name", n);
                startActivity(in);
            }
        });
        internship_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getBaseContext(), InternshipListActivity.class);
                in.putExtra("name", n);
                startActivity(in);
            }
        });
    }
}