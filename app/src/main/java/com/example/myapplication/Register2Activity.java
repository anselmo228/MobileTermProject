package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Register2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_end);

        // RegisterActivity에서 전달된 Intent 받기
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        // getnick EditText에서 입력된 문자열 저장하기
        EditText nicknameEditText = findViewById(R.id.getnick);
        String nickname = nicknameEditText.getText().toString();


    }
}
