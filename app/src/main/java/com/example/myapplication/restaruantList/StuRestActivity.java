package com.example.myapplication.restaruantList;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.pages.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.pages.RequestActivity;
import com.example.myapplication.pages.TodaysmenuActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

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
                String urlString = "https://mobile.gach0n.com/check_session.php?session_id="
                        + URLEncoder.encode(responseText);
                new NetworkTask().execute(urlString);
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
        protected void onPostExecute(String response) {
            //responseText 출력
            Log.d("MainpageActivity", "Response Text: " + response);

            if (response != "wrong session") {
                if (response != "session expired") {
                    Intent today = new Intent(StuRestActivity.this, TodaysmenuActivity.class);
                    today.putExtra("id", id);
                    today.putExtra("password", password);
                    //response 메시지가 아니라 session을 넘겨줬습니다
                    today.putExtra("responseText", responseText);
                    startActivity(today);
                } else {
                    //세션 만료
                    Toast.makeText(StuRestActivity.this, "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                    new LoginActivity();
                    finish();
                }
            } else {
                //예외 발생시 처리할 코드
                Toast.makeText(StuRestActivity.this, "에러", Toast.LENGTH_SHORT).show();
                new LoginActivity();
                finish();
            }
        }
    }
}