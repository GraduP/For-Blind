package com.example.graduate_project_tmap.bus.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TMsgBody {
    @SerializedName("itemList")
    @Expose
    private List<TransferItem> transferItemList = null;

    public List<TransferItem> getItemList() {
        return transferItemList;
    }

    public void setItemList(List<TransferItem> transferItemList) {
        this.transferItemList = transferItemList;
    }
}
