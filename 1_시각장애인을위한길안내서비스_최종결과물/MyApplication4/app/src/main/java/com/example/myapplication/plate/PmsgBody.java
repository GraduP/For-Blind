package com.example.myapplication.plate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PmsgBody {
    @SerializedName("itemList")
    @Expose
    private ArrayList<PlateItem> plateItemArrayList = null;

    public ArrayList<PlateItem> getItemList() {
        return plateItemArrayList;
    }

    public void setItemList(ArrayList<PlateItem> plateItemArrayList) {
        this.plateItemArrayList = plateItemArrayList;
    }
}
