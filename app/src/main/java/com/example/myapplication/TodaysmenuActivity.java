package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
public class TodaysmenuActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private String id;
    private String password;
    private String responseText;

    TextView userIdTextView;

    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todaymenu);

        // 인텐트 데이터 받기
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            password = intent.getStringExtra("password");
            responseText = intent.getStringExtra("responseText");

            // 세션 정보를 사용하는 코드 작성
            // 예: TextView에 사용자 ID 표시
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

        // ViewPager 초기화
        viewPager = findViewById(R.id.viewPager);

        // PagerAdapter 생성 및 연결
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    public static class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 각 페이지에 표시할 Fragment 반환
            return MyFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // 전체 페이지 개수 반환
            return 2; // 예시로 2개의 페이지로 설정
        }
    }
}
