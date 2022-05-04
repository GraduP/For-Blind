package com.example.graduate_project_tmap.bus.station;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MsgBody {

    @SerializedName("itemList")
    @Expose
    private List<StationItem> stationItemList = null;

    public List<StationItem> getItemList() {
        return stationItemList;
    }

    public void setItemList(List<StationItem> stationItemList) {
        this.stationItemList = stationItemList;
    }

}
