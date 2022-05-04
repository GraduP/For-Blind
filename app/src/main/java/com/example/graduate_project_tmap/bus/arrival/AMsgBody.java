package com.example.graduate_project_tmap.bus.arrival;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AMsgBody {


    @SerializedName("itemList")
    @Expose
    private List<ArrivalItem> ArrivalItemList = null;

    public List<ArrivalItem> getArrivalItemList() {
        return ArrivalItemList;
    }

    public void setArrivalItemList(List<ArrivalItem> stationItemList) {
        this.ArrivalItemList = stationItemList;
    }

}

