package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    private String id;
    private String password;
    private String responseText;
    private String identify;
    private String time;
    private GradeListAdapter adapter;
    private ArrayList<ReviewInfo> items;

    public static class ReviewInfo {
        private String name;

        public ReviewInfo() {
        }

        public ReviewInfo(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

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

            items = new ArrayList<>();
            items.add(new ReviewInfo("식사1"));
            items.add(new ReviewInfo("식사2"));
            items.add(new ReviewInfo("식사3"));
            items.add(new ReviewInfo("식사4"));

            RecyclerView recyclerView = findViewById(R.id.grade);
            adapter = new GradeListAdapter(items);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    public class GradeListAdapter extends RecyclerView.Adapter<GradeListAdapter.GradeViewHolder> {
        private ArrayList<ReviewInfo> items;

        public GradeListAdapter(ArrayList<ReviewInfo> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_list, parent, false);
            return new GradeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
            ReviewInfo item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class GradeViewHolder extends RecyclerView.ViewHolder {
            private TextView nameTextView;
            private RatingBar ratingBar;
            private CheckBox checkBox;

            public GradeViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.name);
                ratingBar = itemView.findViewById(R.id.star);
                checkBox = itemView.findViewById(R.id.check);
            }

            public void bind(ReviewInfo item) {
                nameTextView.setText(item.getName());
                ratingBar.setRating(0);
                checkBox.setChecked(false);
            }
        }
    }
}
