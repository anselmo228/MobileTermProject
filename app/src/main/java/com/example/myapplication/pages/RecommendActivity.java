package com.example.myapplication.pages;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RecommendActivity extends AppCompatActivity {
    private TextView userIdTextView;
    private ImageButton back;
    private String id;
    private String responseText;

    private RatingBar ratingBarLunch500;
    private RatingBar ratingBarLunch600;
    private RatingBar ratingBarDinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        ratingBarLunch500 = findViewById(R.id.rating_lunch500);
        ratingBarLunch600 = findViewById(R.id.rating_lunch600);
        ratingBarDinner = findViewById(R.id.rating_dinner);

        Intent intent = getIntent();
        if(intent !=null){
            id = intent.getStringExtra("id");
            responseText = intent.getStringExtra("responseText");

            userIdTextView = findViewById(R.id.user_id_textview);
            userIdTextView.setText("ID: "+id);
        }

        back = findViewById(R.id.image_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // send a request to the server
        requestData("lunch500", ratingBarLunch500);
        requestData("lunch600", ratingBarLunch600);
        requestData("dinner", ratingBarDinner);
    }

    private void requestData(String mealType, RatingBar ratingBar) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        if (responseText != null) {
            String urlString = "https://mobile.gach0n.com/mld_rate.php?session_id="
                    + responseText + "&date="+currentDate +"&mld=" + mealType;
            new NetworkTask(ratingBar).execute(urlString);
        } else {
            Toast.makeText(RecommendActivity.this, "Response text is null", Toast.LENGTH_SHORT).show();
        }
    }

    private class NetworkTask extends AsyncTask<String, Void, String> {
        private RatingBar ratingBar;

        public NetworkTask(RatingBar ratingBar) {
            this.ratingBar = ratingBar;
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

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null && !"NAN".equalsIgnoreCase(response)) {
                ratingBar.setRating(Float.parseFloat(response));
            } else {
                ratingBar.setRating(0.0f);
            }
        }
    }
}
