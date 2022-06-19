package com.example.graduate_project_tmap;

public class Datalist {
    String type;
    String index;
    String pointCoordinates;
    String lineCoordinates;
    String coordinates;
    String description;
    String turnType;
    String facilityName;
    String distance;
    String roadType;
    Double longitude;
    Double latitude;

    public void setType(String i){type = i;}
    public void setIndex(String i){index = i;}
    public void setPointCoordinates(String i){pointCoordinates = i;}
    public void setDescription(String desc){description = desc;}
    public void setTurnType(String s){turnType = s;}
    public void setFacilityName(String s){facilityName = s;}
    public void setDistance(String s){distance = s;}
    public void setRoadType(String s){roadType = s;}
    public void setLongitude(double x){longitude = x;}
    public void setLatitude(double y){latitude = y;}

    public String getType(){return this.type;}
    public String getIndex(){return this.index;}
    public String getPointCoordinates(){return this.pointCoordinates;}
    public String getDescription(){return this.description;}
    public String getTurnType(){return this.turnType;}
    public String getFacilityName(){return this.facilityName;}
    public String getDistance(){return this.distance;}
    public String getRoadType(){return this.roadType;}
    public double getLongitude(){return this.longitude;}
    public double getLatitude(){return this.latitude;}
}

