package com.example.graduate_project_tmap.routes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {

    @SerializedName("type")
    @Expose
    String type;

    @SerializedName("features")
    @Expose
    List<Point> features;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Point> getFeatures() {
        return features;
    }

    public void setFeatures(List<Point> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "Route{" +
                "type='" + type + '\'' +
                ", features=" + features +
                '}';
    }
}
