package com.example.myapplication.station;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MsgBody {

    @SerializedName("itemList")
    @Expose
    private ArrayList<StationItem> stationItemList = null;

    public ArrayList<StationItem> getItemList() {
        return stationItemList;
    }

    public void setItemList(ArrayList<StationItem> stationItemList) {
        this.stationItemList = stationItemList;
    }

}
