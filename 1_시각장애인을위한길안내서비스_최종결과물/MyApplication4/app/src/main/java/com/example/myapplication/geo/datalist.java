package com.example.myapplication.geo;

public class datalist {
    String type;    //Point or Line
    int index;   //Point+Line index
    String description; //Point+Line - description
    String turnType;    //Point - turntype (직진, 우회전, 좌회전, 출발지, 목적지, 육교, 계단, 경사로 등)
    String facilityType;    //Line - facilityType (구간시설물 / 일반보행자도로, 횡단보도, 계단 등)
    String distance;    //Line - distance (구간 거리)
    String roadType;    //Line - roadType (도로타입정보 / 차도 인도 분리 O, 차도 인도 분리 X, 차량 통행 불가 등)
    Double longitude;   //Point의 longitude
    Double latitude;    //Point의 latitude

    public void setType(String i){type = i;}
    public void setIndex(int i){index = i;}
    public void setDescription(String s){description = s;}
    public void setTurnType(String s){turnType = s;}
    public void setFacilityType(String s){facilityType = s;}
    public void setDistance(String s){distance = s;}
    public void setRoadType(String s){roadType = s;}
    public void setLongitude(double x){longitude = x;}
    public void setLatitude(double y){latitude = y;}

    public String getType(){return this.type;}
    public int getIndex(){return this.index;}
    public String getDescription(){return this.description;}
    public String getTurnType(){return this.turnType;}
    public String getFacilityType(){return this.facilityType;}
    public String getDistance(){return this.distance;}
    public String getRoadType(){return this.roadType;}
    public double getLongitude(){return this.longitude;}
    public double getLatitude(){return this.latitude;}
}

