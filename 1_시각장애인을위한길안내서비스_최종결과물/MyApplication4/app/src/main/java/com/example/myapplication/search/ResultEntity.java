package com.example.myapplication.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultEntity {
    @Expose
    @SerializedName("items")
    private ArrayList<ItemsEntity> items = null;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("crs")
    private String crs;

    public ArrayList<ItemsEntity> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemsEntity> items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCrs() {
        return crs;
    }

    public void setCrs(String crs) {
        this.crs = crs;
    }
}
