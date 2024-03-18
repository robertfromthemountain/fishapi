
package com.example.fishapi;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fish {
    private int id;
    private String name;
    private String url;
    private ImgSrcSet imgSrcSet;
    private Meta meta;

    // Getterek és setterek

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImgSrcSet getImgSrcSet() {
        return imgSrcSet;
    }

    public void setImgSrcSet(ImgSrcSet imgSrcSet) {
        this.imgSrcSet = imgSrcSet;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}



/*@Generated("jsonschema2pojo")
public class Fish {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("img_src_set")
    @Expose
    private ImgSrcSet imgSrcSet;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Fish withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fish withName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Fish withUrl(String url) {
        this.url = url;
        return this;
    }

    public ImgSrcSet getImgSrcSet() {
        return imgSrcSet;
    }

    public void setImgSrcSet(ImgSrcSet imgSrcSet) {
        this.imgSrcSet = imgSrcSet;
    }

    public Fish withImgSrcSet(ImgSrcSet imgSrcSet) {
        this.imgSrcSet = imgSrcSet;
        return this;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Fish withMeta(Meta meta) {
        this.meta = meta;
        return this;
    }
}*/
