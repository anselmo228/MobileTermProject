package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

        emailEditText = findViewById(R.id.id_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

        Button sendButton = findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                String urlString = "https://mobile.gach0n.com/get_session.php?id=" +
                        URLEncoder.encode(email) + "&pw=" + URLEncoder.encode(password);


                new NetworkTask(email, password).execute(urlString);

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

    private class NetworkTask extends AsyncTask<String, Void, String> {

        private String email;
        private String password;

        public NetworkTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

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
                if (!responseText.trim().equalsIgnoreCase("incorrect")) {
                    // 로그인 성공
                    Toast.makeText(LoginActivity.this, "로그인 성공. 환영합니다", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainpageActivity.class);
                    intent.putExtra("id", email);
                    intent.putExtra("password", password);
                    intent.putExtra("responseText", responseText);
                    startActivity(intent);
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
