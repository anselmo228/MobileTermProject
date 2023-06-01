package com.example.myapplication.info;

import java.util.Objects;

//각 메뉴의 이름과 영양정보의 틀을 제공합니다.
public class MenuInfo {
    private String name;
    private Float cal;
    private Float car;
    private Float fat;
    private Float pro;

    public MenuInfo(){}

    public MenuInfo(String name, float cal, float car, float pro, float fat){
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

    public Float getCal() {
        return cal;
    }

    public void setCal(float cal) {
        this.cal = cal;
    }

    public Float getCar() {
        return car;
    }

    public void setCar(float car) {
        this.car = car;
    }

    public Float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public Float getPro() {
        return pro;
    }

    public void setPro(float pro) {
        this.pro = pro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuInfo menuInfo = (MenuInfo) o;
        return Float.compare(menuInfo.cal, cal) == 0 &&
                Float.compare(menuInfo.car, car) == 0 &&
                Float.compare(menuInfo.fat, fat) == 0 &&
                Float.compare(menuInfo.pro, pro) == 0 &&
                Objects.equals(name, menuInfo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cal, car, fat, pro);
    }
}
