package com.example.myapplication;

//각 메뉴의 이름과 영양정보의 틀을 제공합니다.
public class MenuInfo {
    private String name;
    private String cal;
    private String car;
    private String fat;
    private String pro;

    public MenuInfo(){}
    public MenuInfo(String name, String cal, String car, String pro, String fat){
        this.name = name;
        this.cal = cal;
        this.car = car;
        this.pro = pro;
        this.fat = fat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

}
