package com.example.myapplication.station;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitClient {
    private static RetrofitClient instance =null;
    private static RetrofitAPI retrofitAPI;
    private static final String BASE_URL = "http://ws.bus.go.kr/";

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);
    }

    public static RetrofitClient getInstance(){
        if (instance==null){
            instance = new RetrofitClient();
        }
        return instance;
    }
    public static RetrofitAPI getRetrofitAPI() {
        return retrofitAPI;
    }
}
    /*private static Retrofit retrofit;
    private static Gson gson;

    public static Retrofit getRetrofit() {
        gson=new GsonBuilder()
                .create();

        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl("http://ws.bus.go.kr/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}*/