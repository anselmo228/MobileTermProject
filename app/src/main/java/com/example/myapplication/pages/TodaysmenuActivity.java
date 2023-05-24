package com.example.myapplication.pages;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.MyFragment;
import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class TodaysmenuActivity extends AppCompatActivity implements MyFragment.Listener {
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private String id;
    private String password;
    private String responseText;
    private String identify;
    private Button review;
    private int position;
    private TextView userIdTextView;
    private ImageButton back;

    //position 0일때 점심, 1일때 저녁
    public void onReceived(int num){
        position = num;
    }

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
            identify = intent.getStringExtra("identify");

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

        review = findViewById(R.id.review);
        review.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String urlString = "https://mobile.gach0n.com/check_session.php?session_id="
                        + URLEncoder.encode(responseText);
                new NetworkTask().execute(urlString);
            }
        });
    }

    public static class PagerAdapter extends FragmentStatePagerAdapter {
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

    private class NetworkTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(false);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    return responseBuilder.toString();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response){
            //responseText 출력
            Log.d("TodaysmenuActivity", "Response Text: "+response);

            if(response != "wrong session"){
                if(response != "session expired"){
                    Intent review = new Intent(TodaysmenuActivity.this, ReviewActivity.class);
                    review.putExtra("id", id);
                    review.putExtra("password", password);
                    //response 메시지가 아니라 session을 넘겨줬습니다
                    review.putExtra("responseText", responseText);
                    //identify는 어느 식당인지, time은 점심과 저녁을 구분하기 위해 넣었습니다.
                    review.putExtra("identify", identify);
                    review.putExtra("time",position);
                    startActivity(review);
                } else{
                    //세션 만료
                    Toast.makeText(TodaysmenuActivity.this, "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                    new LoginActivity();
                    finish();
                }
            } else{
                //예외 발생시 처리할 코드
                Toast.makeText(TodaysmenuActivity.this, "에러", Toast.LENGTH_SHORT).show();
                new LoginActivity();
                finish();
            }
        }
    }
}
