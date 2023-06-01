package com.example.myapplication.pages;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.MyFragment;
import com.example.myapplication.R;
import com.example.myapplication.restaruantList.StuRestActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class TodaysmenuActivity extends AppCompatActivity implements MyFragment.Listener {
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private String id;
    private String password;
    public String responseText;
    private String identify;
    private Button review;
    private int position = -1;
    private TextView userIdTextView;
    private ImageButton back;

    private String menuResult;



    public void onReceived(int num) {
        if (position != num) {  // Only perform a new request if position changed
            position = num;
            requestMenu();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todaymenu);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            password = intent.getStringExtra("password");
            responseText = intent.getStringExtra("responseText");
            identify = intent.getStringExtra("identify");

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

        viewPager = findViewById(R.id.viewPager);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        review = findViewById(R.id.review);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id != null && id.equals("guest")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TodaysmenuActivity.this);
                    builder.setMessage("비회원은 리뷰를 남길 수 없습니다. 로그인 하시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("로그인하기", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent loginIntent = new Intent(TodaysmenuActivity.this, LoginActivity.class);
                                    startActivity(loginIntent);
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else {

                    // Create and start intent for ReviewActivity here
                    Intent reviewIntent = new Intent(TodaysmenuActivity.this, ReviewActivity.class);
                    reviewIntent.putExtra("id", id);
                    reviewIntent.putExtra("password", password);
                    reviewIntent.putExtra("responseText", responseText);
                    reviewIntent.putExtra("identify", identify);
                    reviewIntent.putExtra("time", position);
                    reviewIntent.putExtra("menu", menuResult);
                    startActivity(reviewIntent);
                }
            }
        });

        // Initial menu request
        requestMenu();

    }

    private void requestMenu() {
        String mld;
        if (position == 0) {
            mld = "lunch500";
        } else if (position == 1) {
            mld = "lunch600";
        } else {
            mld = "dinner";
        }

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        new ReviewNetworkTask().execute("https://mobile.gach0n.com/get_meal.php?session_id="
                + URLEncoder.encode(responseText) + "&date="+currentDate+"&mld=" + mld);
    }
    private class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private class ReviewNetworkTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    br.close();
                }
                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equals("Response(False) : fail")) {
                Toast.makeText(TodaysmenuActivity.this, "식단을 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            } else if (result.equals("wrong session") || result.equals("session expired")) {
                Toast.makeText(TodaysmenuActivity.this, "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                new LoginActivity();
                finish();
            } else {
                Log.d("TodaysmenuActivity", "Retrieved menu: " + result);
                menuResult = result;
                // Split result by ,
                String[] menuItems = result.split(",");

                /*여기에 메뉴들 들어가져 있습니다*/
                ArrayList<String> menus = new ArrayList<>();
                for (String menuItem : menuItems) {
                    menus.add(menuItem.trim());
                    new MenuRequestTask().execute("https://mobile.gach0n.com/get_nutrient.php?session_id="
                            + URLEncoder.encode(responseText) + "&menu=" + URLEncoder.encode(menuItem.trim()));

                    Log.d("TodaysmenuActivity", "Menu requested:" + menuItem.trim());
                }
            }
        }

        private ArrayList<String> menuNutrition = new ArrayList<>(); // 여기에 탄단지

        private class MenuRequestTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... urls) {
                String response = "";

                try {
                    URL url = new URL(urls[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line;
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }
                        br.close();
                    }
                    conn.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return response;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if (result.equals("Response(False) : fail")) {
                    Log.d("TodaysmenuActivity", "Menu request failed ");
                    menuNutrition.add("1인분<br>kcal:0<br>fat:0<br>carbohydrate:0<br>protein:0<br>"); // 일단 디폴트 값 넣기
                } else if (result.isEmpty()) {
                    Log.d("TodaysmenuActivity", "Menu request is empty.");
                } else {
                    // Save the response in the ArrayList
                    menuNutrition.add(result);
                    Log.d("TodaysmenuActivity", "Menu request result: " + result);
                }
            }
        }
    }
}