package com.example.fishapi;
import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface FishApiService {
    @GET("fish_api/fishes")
    @Headers({
            "X-RapidAPI-Key: 396570fd0fmshd5793f8879d4c48p1600d5jsn88bea3956942",
            "X-RapidAPI-Host: fish-species.p.rapidapi.com"
    })
    Call<JsonArray> getFishData();
}
