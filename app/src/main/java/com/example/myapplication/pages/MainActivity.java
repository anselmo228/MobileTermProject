package com.example.myapplication.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button guestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // 레이아웃 파일에서 버튼을 찾음
        loginButton = findViewById(R.id.btn_login);
        guestButton = findViewById(R.id.btn_guest);

        // 로그인 버튼 클릭 시 액션 추가
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = "guest";
                String password = null;
                String responseText = null;

                Toast.makeText(MainActivity.this, id+" 환영합니다", Toast.LENGTH_SHORT).show();
                // MainpageActivity로 이동
                Intent mainPage = new Intent(MainActivity.this, MainpageActivity.class);
                mainPage.putExtra("id", id);
                mainPage.putExtra("password", password);
                mainPage.putExtra("responseText", responseText);
                startActivity(mainPage);
            }
        });

    }
}
