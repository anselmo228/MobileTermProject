package com.example.myapplication.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


public class tomorrowmenuActivity extends AppCompatActivity {
    private WebView webView;
    private String id;
    private TextView userIdTextView;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tomorrowmenu);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");

            userIdTextView = findViewById(R.id.user_id_textview);
            userIdTextView.setText("ID: " + id);
        }

        back = findViewById(R.id.image_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.gachon.ac.kr/kor/7350/subview.do");
    }
}
