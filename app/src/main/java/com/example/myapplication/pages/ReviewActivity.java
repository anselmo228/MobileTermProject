package com.example.myapplication.pages;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    private TextView text;
    private String menu;
    private HashMap<String, Float> ratingMap;  // 메뉴 이름과 별점을 저장하는 HashMap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ratingMap = new HashMap<>();  // HashMap 초기화

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
                    for (Map.Entry<String, Float> entry : ratingMap.entrySet()) {
                        String name = entry.getKey();
                        Float point = entry.getValue();
                        String urlString = null;
                        Calendar calendar = Calendar.getInstance(Locale.getDefault());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String currentDate = dateFormat.format(calendar.getTime());

                        if (time == "0") {
                            time = "lunch500";
                        } else if (time == "1") {
                            time = "lunch600";
                        } else {
                            time = "dinner";
                        }

                        try {
                            urlString = "https://mobile.gach0n.com/send_star.php?session_id="
                                    + URLEncoder.encode(responseText, "UTF-8")
                                    + "&date=2023-05-23"
                                    + "&mld=" + time
                                    + "&menu=" + URLEncoder.encode(name, "UTF-8")
                                    + "&star=" + point.toString();
                            Log.d("ReviewActivity", "Rating time:" + time);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        new SendRatingTask().execute(urlString);
                    }
                    Toast.makeText(ReviewActivity.this, "소중한 의견 감사합니다", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(this, "error: no intent", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onRatingBarChange(ReviewInfo item, float value, int position) {
        String name = item.getName();
        float point = value;
        text.setText(name + ": " + point);
        ratingMap.put(name, point);  // 별점을 HashMap에 저장
    }

    private class SendRatingTask extends AsyncTask<String, Void, String> {
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
            if (response != null && response.startsWith("success")) {
                finish();
            } else if (response != null && response.startsWith("fail")) {
                Toast.makeText(ReviewActivity.this, "에러", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReviewActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(ReviewActivity.this, "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReviewActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            Log.d("ReviewActivity", "Rating Result:" + response);
        }
    }
}
