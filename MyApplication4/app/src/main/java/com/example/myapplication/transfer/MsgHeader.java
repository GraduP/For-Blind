package com.example.myapplication.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsgHeader {
    @SerializedName("headerMsg")
    @Expose
    private String headerMsg;
    @SerializedName("headerCd")
    @Expose
    private String headerCd;
    @SerializedName("itemCount")
    @Expose
    private Integer itemCount;

    public String getHeaderMsg() {
        return headerMsg;
    }

    public void setHeaderMsg(String headerMsg) {
        this.headerMsg = headerMsg;
    }

    public String getHeaderCd() {
        return headerCd;
    }

    public void setHeaderCd(String headerCd) {
        this.headerCd = headerCd;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
}