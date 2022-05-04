package com.example.graduate_project_tmap.bus.station;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class StationItem {
    @SerializedName("stId")
    @Expose
    private String stId;
    @SerializedName("stNm")
    @Expose
    private String stNm;
    @SerializedName("tmX")
    @Expose
    private String tmX;
    @SerializedName("tmY")
    @Expose
    private String tmY;
    @SerializedName("posX")
    @Expose
    private String posX;
    @SerializedName("posY")
    @Expose
    private String posY;
    @SerializedName("arsId")
    @Expose
    private String arsId;

    public String getStNm() {
        return stNm;
    }

    public void setStNm(String stNm) {
        stNm = stNm;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stID) {
        this.stId = stID;
    }

    public String getArsId() {
        return arsId;
    }

    public void setArsId(String arsId) {
        this.arsId = arsId;
    }

    public String getPosY() {
        return posY;
    }

    public void setPosY(String posY) {
        this.posY = posY;
    }

    public String getPosX() {
        return posX;
    }

    public void setPosX(String posX) {
        this.posX = posX;
    }

    public String getTmY() {
        return tmY;
    }

    public void setTmY(String tmY) {
        this.tmY = tmY;
    }

    public String getTmX() {
        return tmX;
    }

    public void setTmX(String tmX) {
        this.tmX = tmX;
    }
}