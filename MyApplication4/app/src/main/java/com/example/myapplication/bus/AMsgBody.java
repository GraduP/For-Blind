package com.example.myapplication.bus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AMsgBody {


    @SerializedName("itemList")
    @Expose
    private ArrayList<ArrivalItem> ArrivalItemList = null;

    public ArrayList<ArrivalItem> getArrivalItemList() {
        return ArrivalItemList;
    }

    public void setArrivalItemList(ArrayList<ArrivalItem> stationItemList) {
        this.ArrivalItemList = stationItemList;
    }

}

