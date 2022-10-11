package com.example.myapplication.search;

import com.example.myapplication.transfer.Transfer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
//request=search&query=홍익대학교&type=place&format=json&errorformat=json&key=080DB290-90C8-3D7F-92D1-4394E4B023CC
public interface SearchAPI {
    @GET("req/search")
    Call<Search> getSearch(@Query("request") String search, @Query("query") String text , @Query("type") String type , @Query("format") String format , @Query("errorformat") String errorFormat ,@Query("bbox") String bbox ,@Query("key") String key);

}
