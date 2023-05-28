package com.example.myapplication.pages;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.GradeListAdapter;
import com.example.myapplication.info.ReviewInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity implements GradeListAdapter.OnRecordEventListener {

    private String id;
    private String password;
    private String responseText;
    private String identify;
    private String time;
    private GradeListAdapter adapter;
    private ArrayList<ReviewInfo> items;
    private RatingBar star;
    private Button accept;
    private ImageButton imageButton;
    TextView text;
    private String menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            password = intent.getStringExtra("password");
            responseText = intent.getStringExtra("responseText");
            identify = intent.getStringExtra("identify");
            time = intent.getStringExtra("time");
            menu = intent.getStringExtra("menu");

            imageButton = findViewById(R.id.image_back);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            // 이 부분이 "menu" 문자열을 쉼표를 기준으로 잘라서 ArrayList에 넣는 코드입니다.
            String[] menuItems = menu.split(",");
            items = new ArrayList<>();
            for (String menuItem : menuItems) {
                items.add(new ReviewInfo(menuItem.trim(), this));
            }

            text = findViewById(R.id.text);

            RecyclerView recyclerView = findViewById(R.id.grade);
            adapter = new GradeListAdapter(items, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

            accept = findViewById(R.id.accept);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String urlString = null;
                    try {
                        urlString = "https://mobile.gach0n.com/check_session.php?session_id="
                                + URLEncoder.encode(responseText, "UTF-8");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    new SessionCheckTask().execute(urlString);
                }
            });
        } else {
            Toast.makeText(this, "error: no intent", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    public void onRatingBarChange(ReviewInfo item, float value, int position) {
        if (item.getRatingBar() != null) {
            String name = item.getName();
            float point = value;
            text.setText(name + ": " + point);
        }
    }

    private class SessionCheckTask extends AsyncTask<String, Void, String> {
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
            if ("wrong session".equals(response)) {
                Toast.makeText(ReviewActivity.this, "에러", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReviewActivity.this, LoginActivity.class);
                startActivity(intent);
            } else if ("session expired".equals(response)) {
                Toast.makeText(ReviewActivity.this, "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReviewActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(ReviewActivity.this, "소중한 의견 감사합니다", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
