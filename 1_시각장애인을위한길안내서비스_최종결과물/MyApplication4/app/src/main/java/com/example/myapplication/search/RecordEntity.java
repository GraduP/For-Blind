package com.example.myapplication.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecordEntity {
    @Expose
    @SerializedName("current")
    private String current;
    @Expose
    @SerializedName("total")
    private String total;

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
