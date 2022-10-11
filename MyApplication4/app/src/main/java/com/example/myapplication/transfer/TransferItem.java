package com.example.myapplication.transfer;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class TransferItem implements Comparable<TransferItem> , Serializable {
    @SerializedName("time")
    @Expose
    private int time;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("pathList")
    @Expose
    private ArrayList<PathItem> pathItemList = null;

    public int getTime() {
        return time;
    }

    public String getDistance() {
        return distance;
    }

    public ArrayList<PathItem> getPathItemList() {
        return pathItemList;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setPathItemList(ArrayList<PathItem> pathItemList) {
        this.pathItemList = pathItemList;
    }

    @Override
    public int compareTo(TransferItem transferItem) { // 환승 수 기준 환승 수가 같을 때는 버스 소요시간 기준
        if(transferItem.getPathItemList().size() < this.getPathItemList().size())
            return 1;
        else if(transferItem.getPathItemList().size() == this.getPathItemList().size()) {
            if(transferItem.getTime() < this.getTime())
                return 1;
            else
                return -1;
        }
        return -1;
    }


}

