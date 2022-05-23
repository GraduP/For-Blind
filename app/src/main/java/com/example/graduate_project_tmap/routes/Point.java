package com.example.graduate_project_tmap.routes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;

public class Point {
    @SerializedName("type")
    @Expose
    private String type;


    @SerializedName("properties")
    @Expose
    private Array properties;

    public Array getProperties() {
        return properties;
    }

    public void setProperties(Array properties) {
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Point{" +
                "type='" + type + '\'' +
                ", properties=" + properties +
                '}';
    }
}
