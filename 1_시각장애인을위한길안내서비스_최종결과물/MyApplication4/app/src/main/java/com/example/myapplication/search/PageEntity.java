package com.example.myapplication.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageEntity {
    @Expose
    @SerializedName("size")
    private String size;
    @Expose
    @SerializedName("current")
    private String current;
    @Expose
    @SerializedName("total")
    private String total;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
