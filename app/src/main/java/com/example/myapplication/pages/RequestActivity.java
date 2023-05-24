package com.example.myapplication.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class RequestActivity extends AppCompatActivity {

    private EditText idEditText;
    Intent info;
    String id, password, responseText;
    private Button btnGuest;
    TextView userIdTextView;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request);
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

        idEditText = findViewById(R.id.id_editext);
        btnGuest = findViewById(R.id.btn_request);

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id == "guest"){
                    Toast.makeText(RequestActivity.this, "비회원으로는 리뷰를 남길 수 없습니다. 로그인 후 진행해주세요" , Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    finish();
                }
                else {
                    String inputText = idEditText.getText().toString();
                    Toast.makeText(RequestActivity.this, "정상적으로 처리되었습니다. 소중한 의견 감사합니다.", Toast.LENGTH_SHORT).show();

                    onBackPressed();
                    finish();
                }
            }
        });
    }
}
