package com.example.fishapi.dtos;

import com.google.gson.annotations.SerializedName;

public class ScientificClassification {
    @SerializedName("domain")
    private String domain;

    @SerializedName("kingdom")
    private String kingdom;

    @SerializedName("phylum")
    private String phylum;

    @SerializedName("class")
    private String classType;

    @SerializedName("order")
    private String order;

    @SerializedName("superfamily")
    private String superfamily;

    @SerializedName("family")
    private String family;

    public String getDomain() {
        return domain;
    }

    public String getKingdom() {
        return kingdom;
    }

    public String getPhylum() {
        return phylum;
    }

    public String getClassType() {
        return classType;
    }

    public String getOrder() {
        return order;
    }

    public String getSuperfamily() {
        return superfamily;
    }

    public String getFamily() {
        return family;
    }
}

