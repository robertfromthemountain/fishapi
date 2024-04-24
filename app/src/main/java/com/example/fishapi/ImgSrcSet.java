
package com.example.fishapi;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ImgSrcSet {

    @SerializedName("1.5x")
    @Expose
    private String _15x;
    @SerializedName("2x")
    @Expose
    private String _2x;

    public String get15x() {
        return _15x;
    }

    public void set15x(String _15x) {
        this._15x = _15x;
    }

    public ImgSrcSet with15x(String _15x) {
        this._15x = _15x;
        return this;
    }

    public String get2x() {
        return _2x;
    }

    public void set2x(String _2x) {
        this._2x = _2x;
    }

    public ImgSrcSet with2x(String _2x) {
        this._2x = _2x;
        return this;
    }

}
