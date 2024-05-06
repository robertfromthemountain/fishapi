package com.example.fishapi.clients;

import android.util.Log;

import com.example.fishapi.dtos.FishItem;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    private FishApiClient fishApiClient;



    public RetrofitClient() {

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://fish-species.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        fishApiClient = retrofit.create(FishApiClient.class);
    }

    public interface FishDataCallback {
        void onSuccess(List<FishItem> fetchedFishList);
        void onFailure(Throwable throwable);
    }

    public void fetchFishData(FishDataCallback callback) {
        Call<List<FishItem>> call = fishApiClient.getFishData();
        call.enqueue(new Callback<List<FishItem>>() {
            @Override
            public void onResponse(Call<List<FishItem>> call, Response<List<FishItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FishItem> fishList = response.body();
                    callback.onSuccess(fishList);
                } else {
                    callback.onFailure(new Exception("Sikertelen válasz"));
                }
            }

            @Override
            public void onFailure(Call<List<FishItem>> call, Throwable t) {
                callback.onFailure(t);
            }
        });

    }

}
