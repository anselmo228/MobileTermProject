package com.example.myapplication.pages;

import android.content.Intent;
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

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            items = new ArrayList<>();
            items.add(new ReviewInfo("식사1", this));
            items.add(new ReviewInfo("식사2", this));
            items.add(new ReviewInfo("식사3", this));
            items.add(new ReviewInfo("식사4", this));

            text = findViewById(R.id.text);

            RecyclerView recyclerView = findViewById(R.id.grade);
            adapter = new GradeListAdapter(items, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

            accept = findViewById(R.id.accept);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }else{
            Toast.makeText(this,"error: no intent",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onRatingBarChange(ReviewInfo item, float value, int position) {
        //save your object into database here
        if (item.getRatingBar() != null) { // RatingBar 객체가 null이 아닌 경우에만 처리
            String name = item.getName();
            float point = value; // 평점 가져오기
            text.setText(name + ": " + point);
        }
    }
}
