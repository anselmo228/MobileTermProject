package com.example.myapplication.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.MyFragment;
import com.example.myapplication.R;
import com.example.myapplication.RecFragment;

public class RecommendActivity extends AppCompatActivity implements RecFragment.Listener {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TextView userIdTextView;
    private ImageButton back;
    private String id;
    private int position = 0;

    @Override
    public void onReceived(int num) {
        if(position != num){
            position = num;
            requestRec();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        Intent intent = getIntent();
        if(intent !=null){
            id = intent.getStringExtra("id");
            userIdTextView = findViewById(R.id.user_id_textview);
            userIdTextView.setText("ID: "+id);
        }

        back = findViewById(R.id.image_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {onBackPressed();}
        });

        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        requestRec();
    }
    private void requestRec(){
        String mld;
        if (position == 0) {
            mld = "lunch";
        }else{
            mld = "dinner";
        }
    }
    private class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return RecFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}