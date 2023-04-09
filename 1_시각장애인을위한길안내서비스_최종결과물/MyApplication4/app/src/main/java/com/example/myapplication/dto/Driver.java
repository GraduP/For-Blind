package com.example.myapplication.dto;

public class Driver {

    public String username;
    public String age;
    public String gender;
    public String busNum;
    public String fcmToken;

    public Driver() {
    }

    public Driver(String username, String age, String gender, String busNum, String fcmToken) {
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.busNum = busNum;
        this.fcmToken = fcmToken;
    }
}
