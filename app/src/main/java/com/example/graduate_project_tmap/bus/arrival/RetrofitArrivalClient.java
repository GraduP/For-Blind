package com.example.graduate_project_tmap.bus.arrival;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitArrivalClient {  private static RetrofitArrivalClient instance =null;
    private static ArrivalAPI ArrivalAPI;
    private static final String BASE_URL = "http://ws.bus.go.kr/";

    private RetrofitArrivalClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ArrivalAPI = retrofit.create(ArrivalAPI.class);
    }

    public static RetrofitArrivalClient getInstance(){
        if (instance==null){
            instance = new RetrofitArrivalClient();
        }
        return instance;
    }
    public static ArrivalAPI getArrivalAPI() {
        return ArrivalAPI;
    }


}
