package com.education.counselor.trainer.admin;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.education.counselor.trainer.R;
public class CentersListActivity extends AppCompatActivity {
    Button add_center, center;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centers_list);
        add_center = findViewById(R.id.add_center);
        center = findViewById(R.id.center);
        add_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddCentersActivity.class));
            }
        });
        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CentersDetailsActivity.class));
            }
        });
    }
}