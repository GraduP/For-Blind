package com.example.myapplication.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemsEntity {
    @Expose
    @SerializedName("point")
    private PointEntity point;
    @Expose
    @SerializedName("address")
    private AddressEntity address;
    @Expose
    @SerializedName("category")
    private String category;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("id")
    private String id;

    public PointEntity getPoint() {
        return point;
    }

    public void setPoint(PointEntity point) {
        this.point = point;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
