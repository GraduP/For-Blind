package com.example.myapplication;

import com.example.myapplication.transfer.TransferItem;

import java.util.ArrayList;

public class RecyclerViewItem {
    private String time;
    private String station;
    private ArrayList<TransferItem> transferItems = null;

    public ArrayList<TransferItem> getTransferItems() {
        return transferItems;
    }

    public void setTransferItems(ArrayList<TransferItem> transferItems) {
        this.transferItems = transferItems;
    }

    public String getStation() {
        return station;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
