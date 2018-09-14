package com.education.counselor.trainer.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.education.counselor.trainer.R;

public class PlacementListActivity extends AppCompatActivity {
    Button add_placement, placement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_list);
        add_placement = findViewById(R.id.add_placement);
        placement = findViewById(R.id.placement);
        add_placement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddPlacementActivity.class));
            }
        });
        placement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), PlacementDetailsActivity.class));
            }
        });
    }
}