package com.example.myapplication.fcm;

import com.example.myapplication.dto.Notification;
import com.google.gson.annotations.SerializedName;


//Retrofit 요청 DTO

public class FcmRequest {

    @SerializedName("to")
    String to;

    @SerializedName("notification")
    Notification notification;

    public FcmRequest() {
    }

    public FcmRequest(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}


