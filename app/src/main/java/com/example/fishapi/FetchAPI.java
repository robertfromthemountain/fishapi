package com.example.fishapi;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchAPI {

    public interface FishDataCallback {
        void onFishDataReceived(JsonArray fishArray);
        void onFailure(String errorMessage);
    }

    public static void fetchFishData(Context context, String fileName, FishDataCallback callback) {
        FishApiService service = FishApiClient.getClient().create(FishApiService.class);

        Call<JsonArray> call = service.getFishData();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    JsonArray fishArray = response.body();

                    String jsonFishString = fishArray.toString();

                    try {
                        File file = new File(context.getExternalFilesDir(null), fileName);
                        FileWriter writer = new FileWriter(file);
                        writer.append(jsonFishString);
                        writer.flush();
                        writer.close();
                        Log.d("Fájlba írás: ", "Fájlba írás sikeres");

                        callback.onFishDataReceived(fishArray);

                    } catch (IOException e) {
                        Log.d("Fájlba írás: ", "Fájlba írás sikertelen");
                        e.printStackTrace();
                        callback.onFailure("Fájlba írás sikertelen");
                    }
                } else {
                    callback.onFailure("Hiba a lekérés során: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Hálózati hiba: ", t.getMessage());
                callback.onFailure("Hálózati hiba: " + t.getMessage());
            }
        });
    }
}
