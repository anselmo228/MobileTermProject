package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class StuRestActivity extends AppCompatActivity {
    Intent info;
    String id, password, responseText;
    TextView userIdTextView;

    ImageButton back;
    Button btn_suggest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_rest);
        // MainPage 전달받은 세션 정보
        info = getIntent();
        id = info.getStringExtra("id");
        password = info.getStringExtra("password");
        responseText = info.getStringExtra("responseText");

        // 세션 정보를 상단에 출력
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
        Button btn_suggest = findViewById(R.id.btn_suggest);
        btn_suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StuRestActivity.this, RequestActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("password", password);
                intent.putExtra("responseText", responseText);
                startActivity(intent);
                finish();
            }
        });
        Button mapButton = findViewById(R.id.map);
        mapButton.setOnClickListener(new View.OnClickListener() {

            /* 위치보기 클릭하면 네이버 지도 보여주기*/
            @Override
            public void onClick(View v) {
                String url = "https://map.naver.com/v5/search/가천대학교%20글로벌캠퍼스제3기숙사/place/1857267080?c=18.21,0,0,0,dh&isCorrectAnswer=true";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        Button todayButton = findViewById(R.id.today);
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StuRestActivity.this, TodaysmenuActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("password", password);
                intent.putExtra("responseText", responseText);
                startActivity(intent);
            }
        });
        Button btn_insta = findViewById(R.id.btn_insta);
        btn_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.gachon.ac.kr/kor/7350/subview.do";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });


    }
}
