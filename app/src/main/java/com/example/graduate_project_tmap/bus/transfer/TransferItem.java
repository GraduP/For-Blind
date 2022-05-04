package com.example.graduate_project_tmap.bus.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransferItem {
   @SerializedName("time")
   @Expose
        private int time;
   @SerializedName("distance")
   @Expose
        private long distance;
   @SerializedName("pathList")
   @Expose
   private List<PathItem> pathItemList = null;
   public int getTime()
   {
       return time;
   }
   public long getDistance()
   {
       return distance;
   }
    public List<PathItem> getPathItemList()
    {
        return pathItemList;
    }
    public void setTime(int time)
    {
       this.time =time;
    }
    public  void  setDistance(long distance)
    {
        this.distance = distance;
    }
    public void setPathItemList(List<PathItem> pathItemList)
    {
        this.pathItemList = pathItemList;
    }
}
