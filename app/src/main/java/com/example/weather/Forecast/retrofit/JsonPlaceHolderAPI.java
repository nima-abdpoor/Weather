package com.example.weather.Forecast.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderAPI {
    @GET("forecast")
    Call<Forecast> getfrorecast(
            @Query("id") Long cityid,
            @Query("appid") String key
    );
}
