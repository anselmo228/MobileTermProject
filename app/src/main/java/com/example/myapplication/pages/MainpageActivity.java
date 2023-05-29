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

import com.example.myapplication.R;
import com.example.myapplication.restaruantList.StuRestActivity;
import com.example.myapplication.restaruantList.artActivity;
import com.example.myapplication.restaruantList.eduActivity;
import com.example.myapplication.restaruantList.visionActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainpageActivity extends AppCompatActivity {
    Intent info;
    String id, password, responseText, identify;
    TextView userIdTextView;
    ImageButton back;
    Button stu, vision, art, edu, recommend;

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

        //학생기숙사 식당 버튼
        stu = findViewById(R.id.stu);
        stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identify = "st";
                startNetworkTask();
            }
        });

        //비전타워 식당 버튼
        vision = findViewById(R.id.vision);
        vision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identify = "vi";
                startNetworkTask();
            }
        });

        //예술인 레스토랑 버튼
        art = findViewById(R.id.art);
        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identify = "ar";
                startNetworkTask();
            }
        });

        //교육대 식당 버튼
        edu = findViewById(R.id.edu);
        edu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identify = "ed";
                startNetworkTask();
            }
        });

        recommend = findViewById(R.id.recommend);
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identify = "rec";
                startNetworkTask();
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
            //session이 정상일 때 각 식당으로 넘어갑니다.
            if (response != "wrong session") {
                if (response != "session expired") {
                    nextActivity();
                } else {
                    //세션 만료
                    Toast.makeText(MainpageActivity.this, "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                    new LoginActivity();
                    finish();
                }
            } else {
                //예외 발생시 처리할 코드
                Toast.makeText(MainpageActivity.this, "에러", Toast.LENGTH_SHORT).show();
                new LoginActivity();
                finish();
            }
        }

    }

    //기존에서 다음 activity로 넘어가는 부분을 따로 함수로 만들었습니다.
    private void nextActivity() {
        Intent intent = new Intent();
        switch (identify) {
            case "st":
                //학생생활관 식당
                intent = new Intent(MainpageActivity.this, StuRestActivity.class);
                break;
            case "ed":
                //교육대 식당
                intent = new Intent(MainpageActivity.this, eduActivity.class);
                break;
            case "ar":
                //예술대 식당
                intent = new Intent(MainpageActivity.this, artActivity.class);
                break;
            case "vi":
                //비전타워 식당
                intent = new Intent(MainpageActivity.this, visionActivity.class);
                break;
            case "rec":
                //메뉴 추천 시스템
                intent = new Intent(MainpageActivity.this, RecommendActivity.class);
                break;
            default:
                //비정상적인 입력시 로그인페이지 반환
                Toast.makeText(MainpageActivity.this, "에러", Toast.LENGTH_SHORT).show();
                new LoginActivity();
                finish();
                break;
        }
        intent.putExtra("id", id);
        intent.putExtra("password", password);
        //response 메시지가 아니라 session을 넘겨줬습니다
        intent.putExtra("responseText", responseText);
        intent.putExtra("identify", identify);//st:기숙사 ed:교육대 pe:체육대 vi:비전
        startActivity(intent);
    }
    //NetworkTask를 시작합니다.
    private void startNetworkTask(){
        if (id != null && id.equals("guest")) {
            nextActivity();
        } else {

            if(responseText != null) {
                String urlString = "https://mobile.gach0n.com/check_session.php?session_id="
                        + URLEncoder.encode(responseText);
                new NetworkTask().execute(urlString);
            } else {
                Toast.makeText(MainpageActivity.this, "Responsetext가 null입니다", Toast.LENGTH_SHORT).show();
            }
        }
    }
}