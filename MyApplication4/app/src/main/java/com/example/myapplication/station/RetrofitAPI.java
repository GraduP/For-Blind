package com.example.myapplication.station;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// http://ws.bus.go.kr/api/rest/pathinfo/getLocationInfo?serviceKey=uJVPZ36cG4TAmsXg9mpWZHtlod%2BuxSREmceXmb8%2BhOU2NDP2G2XcyW4KOua4%2FPMe%2BI1P5%2FMemCn1pNVoNQS8Iw%3D%3D&stSrch=%ED%99%8D%EB%8C%80%EC%9E%85%EA%B5%AC&resultType=json
public interface RetrofitAPI {

    @GET("api/rest/stationinfo/getStationByName")
    Call<Station> getData(@Query("serviceKey") String key, @Query("stSrch") String stSrch , @Query("resultType") String type);
}