package com.example.myapplication.dto;

import com.google.gson.annotations.SerializedName;

// Retrofit 요청 DTO

public class Notification {
    @SerializedName("title")
    String title;
    @SerializedName("body")
    String body;

    public Notification() {
    }

    public Notification(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

