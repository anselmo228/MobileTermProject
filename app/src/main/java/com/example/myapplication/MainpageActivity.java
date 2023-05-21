package com.example.myapplication;

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
    Button stu;

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
                String urlString = "https://mobile.gach0n.com/check_session.php?session_id="
                        + URLEncoder.encode(responseText);
                new NetworkTask().execute(urlString);
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
        protected void onPostExecute(String response){
            //responseText 출력
            Log.d("MainpageActivity", "Response Text: "+response);

            if(response != "wrong session"){
                if(response != "session expired"){
                    
                    switch(identify){
                        case "st":
                            Intent rest = new Intent(MainpageActivity.this, StuRestActivity.class);
                            rest.putExtra("id", id);
                            rest.putExtra("password", password);
                            //response 메시지가 아니라 session을 넘겨줬습니다
                            rest.putExtra("responseText", responseText);
                            rest.putExtra("identify", identify);//0:기숙사 1:교육대 2:체육대 3:비전
                            startActivity(rest);
                            break;
                        case "ed":
                            //교육대 식당
                            break;
                        case "pe":
                            //체육대 식당
                            break;
                        case "vi":
                            //비전타워 식당
                            break;
                        default:
                            Toast.makeText(MainpageActivity.this, "에러", Toast.LENGTH_SHORT).show();
                            new LoginActivity();
                            finish();
                            break;
                    }


                } else{
                    //세션 만료
                    Toast.makeText(MainpageActivity.this, "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                    new LoginActivity();
                    finish();
                }
            } else{
                //예외 발생시 처리할 코드
                Toast.makeText(MainpageActivity.this, "에러", Toast.LENGTH_SHORT).show();
                new LoginActivity();
                finish();
            }
        }
    }
}