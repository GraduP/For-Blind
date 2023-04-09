package com.example.myapplication.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PathItem implements Serializable {
    @SerializedName("routeNm")
    @Expose
    private String routeNm;
    @SerializedName("routeId")
    @Expose
    private String routeId;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("fid")
    @Expose
    private  String fid;
    @SerializedName("fx")
    @Expose
    private String fx;
    @SerializedName("fy")
    @Expose
    private String fy;
    @SerializedName("tname")
    @Expose
    private String tname;
    @SerializedName("tid")
    @Expose
    private  long tid;
    @SerializedName("tx")
    @Expose
    private double tx;
    @SerializedName("ty")
    @Expose
    private double ty;
    @SerializedName("railLinkList")
    @Expose
    private long  railLinkList;
    public String getRouteNm()
    {
        return routeNm;
    }
    public String getRouteId()
    {
        return routeId;
    }
    public String getFname()
    {
        return fname;
    }
    public String getFid()
    {
        return fid;
    }
    public String getFx()
    {
        return fx;
    }
    public String getFy()
    {
        return fy;
    }
    public String getTname()
    {
        return tname;
    }
    public long getTid()
    {
        return tid;
    }
    public double getTx()
    {
        return tx;
    }
    public double getTy()
    {
        return ty;
    }
    public long getRailLinkList()
    {
        return railLinkList;
    }
    public void setTy(double ty) {
        this.ty = ty;
    }

    public void setTx(double tx) {
        this.tx = tx;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public void setFy(String fy) {
        this.fy = fy;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public void setRailLinkList(long railLinkList) {
        this.railLinkList = railLinkList;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setRouteNm(String routeNm) {
        this.routeNm = routeNm;
    }

}
