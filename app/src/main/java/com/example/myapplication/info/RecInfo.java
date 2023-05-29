package com.example.myapplication.info;

import android.content.Context;
import android.widget.RatingBar;

import java.util.ArrayList;

public class RecInfo {
    private String name;
    private RatingBar star;
    private Context context;
    private ArrayList<MenuInfo> menuList;
    private float point;

    public RecInfo(){}
    //식당 이름, 점수, context, 메뉴 리스트
    public RecInfo(String name, float point, Context context, ArrayList<MenuInfo> menuList){
        this.name = name;
        this.context = context;
        this.star = new RatingBar(context);
        this.menuList = menuList;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public RatingBar getStar() {
        return star;
    }

    public void setStar(RatingBar star) {this.star = star;}

    public void setMenuList(ArrayList<MenuInfo> menuList) {
        this.menuList = menuList;
    }
    public ArrayList<MenuInfo> getMenuList() {
        return menuList;
    }

    //전체 메뉴의 영양소 합을 반환합니다
    public float getCal() {
        float cal = 0;
        for(int i=0;i<menuList.size();i++){
            if(menuList.get(i).getCal() == null) continue;
            cal+=menuList.get(i).getCal();
        }
        return cal;
    }

    public float getCar() {
        float car = 0;
        for(int i=0;i<menuList.size();i++){
            if(menuList.get(i).getCar() == null) continue;
            car+=menuList.get(i).getCar();
        }
        return car;
    }


    public float getFat() {
        float fat = 0;
        for(int i=0;i<menuList.size();i++){
            if(menuList.get(i).getFat() == null) continue;
            fat+=menuList.get(i).getFat();
        }
        return fat;
    }


    public float getPro() {
        float pro = 0;
        for(int i=0;i<menuList.size();i++){
            if(menuList.get(i).getPro() == null) continue;
            pro+=menuList.get(i).getPro();
        }
        return pro;
    }
}
