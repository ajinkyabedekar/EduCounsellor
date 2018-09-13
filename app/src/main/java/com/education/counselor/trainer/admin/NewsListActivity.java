package com.education.counselor.trainer.admin;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.education.counselor.trainer.R;
public class NewsListActivity extends AppCompatActivity {
    Button add_news, news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        add_news = findViewById(R.id.add_news);
        news = findViewById(R.id.news);
        add_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddNewsActivity.class));
            }
        });
    }
}