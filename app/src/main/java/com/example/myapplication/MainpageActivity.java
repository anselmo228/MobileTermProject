package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainpageActivity extends AppCompatActivity {
    Intent info;
    String id, password, responseText;
    TextView userIdTextView;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        // LoginActivity에서 전달받은 세션 정보
        info = getIntent();
        id = info.getStringExtra("id");
        password = info.getStringExtra("password");
        responseText = info.getStringExtra("responseText");

        // 세션 정보를 사용하는 코드 작성
        // 예: TextView에 사용자 ID 표시
        userIdTextView = findViewById(R.id.user_id_textview);
        userIdTextView.setText("ID: " + id);

        //뒤로가기 버튼
        back = findViewById(R.id.image_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}