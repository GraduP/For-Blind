package com.example.myapplication.plate;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitPlateClient {
    private static RetrofitPlateClient instance =null;
    private static PlateAPI PlateAPI;
    private static final String BASE_URL = "http://ws.bus.go.kr/";

    private RetrofitPlateClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PlateAPI = retrofit.create(PlateAPI.class);
    }

    public static RetrofitPlateClient getInstance(){
        if (instance==null){
            instance = new RetrofitPlateClient();
        }
        return instance;
    }
    public static PlateAPI getPlateAPI() {
        return PlateAPI;
    }
}
