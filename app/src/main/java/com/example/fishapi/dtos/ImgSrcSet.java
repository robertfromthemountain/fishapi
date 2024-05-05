package com.example.fishapi.dtos;

import com.google.gson.annotations.SerializedName;

// ImgSrcSet osztály
public class ImgSrcSet {
    @SerializedName("1.5x")
    private String imgSrc1_5x;

    @SerializedName("2x")
    private String imgSrc2x;

    public ImgSrcSet(String imgSrc1_5x, String imgSrc2x) {
        this.imgSrc1_5x = imgSrc1_5x;
        this.imgSrc2x = imgSrc2x;
    }

    public String getImgSrc1_5x() {
        return imgSrc1_5x;
    }

    public String getImgSrc2x() {
        return imgSrc2x;
    }
}