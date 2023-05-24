package com.example.myapplication.info;

import android.content.Context;
import android.widget.RatingBar;

public class ReviewInfo {
    private String name;
    private RatingBar star;
    private Context context;

    public ReviewInfo() {}

    public ReviewInfo(String name, Context context) {
        this.name = name;
        this.context = context;
        this.star = new RatingBar(context);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public void setRating(float point) { this.star.setRating(point);}

    public float getRating() {return star.getRating();}

    public RatingBar getRatingBar(){return star;}
}