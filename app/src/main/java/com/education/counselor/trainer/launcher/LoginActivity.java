package com.education.counselor.trainer.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.login.AdminLoginActivity;
import com.education.counselor.trainer.login.StudentLoginActivity;
import com.education.counselor.trainer.login.authority.AuthorityChoiceActivity;
import com.education.counselor.trainer.login.employee.EmployeeChoiceActivity;
public class LoginActivity extends AppCompatActivity {
    Button student, counsellor, authority, admin;
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        student = findViewById(R.id.student);
        counsellor = findViewById(R.id.counsellor);
        authority = findViewById(R.id.authority);
        admin = findViewById(R.id.admin);
        webview = findViewById(R.id.webview);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), StudentLoginActivity.class));
                finishAffinity();
            }
        });
        counsellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), EmployeeChoiceActivity.class));
                finishAffinity();
            }
        });
        authority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AuthorityChoiceActivity.class));
                finishAffinity();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AdminLoginActivity.class));
                finishAffinity();
            }
        });
        webview.setWebViewClient(new MyBrowser());
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl("https://rankethon.in/");
            return true;
        }
    }
}