package com.example.myapplication.search;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitSearchClient {
    private static RetrofitSearchClient instance =null;
    private static com.example.myapplication.search.SearchAPI SearchAPI;
    private static final String BASE_URL = "http://api.vworld.kr/";

    private RetrofitSearchClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SearchAPI = retrofit.create(SearchAPI.class);
    }

    public static RetrofitSearchClient getInstance(){
        if (instance==null){
            instance = new RetrofitSearchClient();
        }
        return instance;
    }
    public static SearchAPI getSearchAPI() {
        return SearchAPI;
    }
}
