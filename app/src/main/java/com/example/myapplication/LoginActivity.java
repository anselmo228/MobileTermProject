package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton imageButton = findViewById(R.id.image_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        emailEditText = findViewById(R.id.email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

        Button sendButton = findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                String urlString = "https://mobile.gach0n.com/login.php?id=" +
                        URLEncoder.encode(email) + "&pw=" + URLEncoder.encode(password);

                new NetworkTask().execute(urlString);
            }
        });

        Button visitButton = findViewById(R.id.visit);
        visitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 누르면 가천대학교 홈페이지로 연결
                String url = "https://sso.gachon.ac.kr/svc/tk/Auth.do?ac=Y&ifa=N&id=portal";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private class NetworkTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(false);

                int responseCode = connection.getResponseCode();
                return responseCode;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer responseCode) {
            if (responseCode != null) {
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 로그인 성공
                    Toast.makeText(LoginActivity.this, "정상적으로 로그인 되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    // 로그인 실패
                    Toast.makeText(LoginActivity.this, "아이디/비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                }
            } else {
                // 예외 발생 시 처리할 코드 작성
                Toast.makeText(LoginActivity.this, "network error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
