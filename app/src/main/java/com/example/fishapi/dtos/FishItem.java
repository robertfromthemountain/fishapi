package com.example.fishapi.dtos;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class FishItem {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    @SerializedName("img_src_set")
    private JsonElement imgSrcSetElement;

    @SerializedName("meta")
    private Meta meta;



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public JsonElement getImgSrcSetElement() {
        return imgSrcSetElement;
    }

    public Meta getMeta() {
        return meta;
    }




    public static class Meta {

        @SerializedName("conservation_status")
        private String conservationStatus;

        public String getConservationStatus() {
            return conservationStatus;
        }

        @SerializedName("scientific_classification")
        private ScientificClassification scientificClassification;

        public ScientificClassification getScientificClassification() {
            return scientificClassification;
        }
    }
}
