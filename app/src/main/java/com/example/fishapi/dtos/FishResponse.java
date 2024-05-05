package com.example.fishapi.dtos;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FishResponse {
    @SerializedName("data")
    private List<FishItem> fishItems;

    public List<FishItem> getFishItems() {
        return fishItems;
    }
}
