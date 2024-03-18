
package com.example.fishapi;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Meta {

    @SerializedName("conservation_status")
    @Expose
    private String conservationStatus;
    @SerializedName("scientific_classification")
    @Expose
    private ScientificClassification scientificClassification;
    @SerializedName("binomial_name")
    @Expose
    private String binomialName;
    @SerializedName("type_species")
    @Expose
    private String typeSpecies;
    @SerializedName("synonyms")
    @Expose
    private String synonyms;
    @SerializedName("subfamilies_&_genera")
    @Expose
    private String subfamiliesGenera;
    @SerializedName("genera")
    @Expose
    private String genera;

    public String getConservationStatus() {
        return conservationStatus;
    }

    public void setConservationStatus(String conservationStatus) {
        this.conservationStatus = conservationStatus;
    }

    public Meta withConservationStatus(String conservationStatus) {
        this.conservationStatus = conservationStatus;
        return this;
    }

    public ScientificClassification getScientificClassification() {
        return scientificClassification;
    }

    public void setScientificClassification(ScientificClassification scientificClassification) {
        this.scientificClassification = scientificClassification;
    }

    public Meta withScientificClassification(ScientificClassification scientificClassification) {
        this.scientificClassification = scientificClassification;
        return this;
    }

    public String getBinomialName() {
        return binomialName;
    }

    public void setBinomialName(String binomialName) {
        this.binomialName = binomialName;
    }

    public Meta withBinomialName(String binomialName) {
        this.binomialName = binomialName;
        return this;
    }

    public String getTypeSpecies() {
        return typeSpecies;
    }

    public void setTypeSpecies(String typeSpecies) {
        this.typeSpecies = typeSpecies;
    }

    public Meta withTypeSpecies(String typeSpecies) {
        this.typeSpecies = typeSpecies;
        return this;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public Meta withSynonyms(String synonyms) {
        this.synonyms = synonyms;
        return this;
    }

    public String getSubfamiliesGenera() {
        return subfamiliesGenera;
    }

    public void setSubfamiliesGenera(String subfamiliesGenera) {
        this.subfamiliesGenera = subfamiliesGenera;
    }

    public Meta withSubfamiliesGenera(String subfamiliesGenera) {
        this.subfamiliesGenera = subfamiliesGenera;
        return this;
    }

    public String getGenera() {
        return genera;
    }

    public void setGenera(String genera) {
        this.genera = genera;
    }

    public Meta withGenera(String genera) {
        this.genera = genera;
        return this;
    }

}
