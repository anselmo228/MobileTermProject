package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    ImageButton imageButton;
    Button sendButton,
            visitButton;
    String email,
            password,
            urlString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageButton = findViewById(R.id.image_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        emailEditText = findViewById(R.id.email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

        sendButton = findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();

                urlString = "https://mobile.gach0n.com/get_session.php?id=" +
                        URLEncoder.encode(email) + "&pw=" + URLEncoder.encode(password);
                new NetworkTask().execute(urlString);
            }
        });

        visitButton = findViewById(R.id.visit);
        visitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 누르면 가천대학교 홈페이지로 연결
                String url = "https://sso.gachon.ac.kr/svc/tk/Auth.do?ac=Y&ifa=N&id=portal";
                Intent gachonURL = new Intent(Intent.ACTION_VIEW);
                gachonURL.setData(Uri.parse(url));
                startActivity(gachonURL);
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
        protected void onPostExecute(String responseText) {
            // responseText 출력
            Log.d("LoginActivity", "Response Text: " + responseText);

            if (responseText != null) {
                if (!responseText.isEmpty()) {
                    // 로그인 성공 responseText가 너무 길게 나와서 토스트 메시지에서 일단 지워뒀습니다.
                    Toast.makeText(LoginActivity.this, " 환영합니다", Toast.LENGTH_SHORT).show();
                    // MainpageActivity로 이동
                    Intent mainPage = new Intent(LoginActivity.this, MainpageActivity.class);
                    mainPage.putExtra("id", email);
                    mainPage.putExtra("password", password);
                    mainPage.putExtra("responseText", responseText);
                    startActivity(mainPage);

                    // LoginActivity 종료
                    finish();
                } else {
                    // 로그인 실패
                    Toast.makeText(LoginActivity.this, "아이디/비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                }
            } else {
                // 예외 발생 시 처리할 코드 작성
                Toast.makeText(LoginActivity.this, "아이디/비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
