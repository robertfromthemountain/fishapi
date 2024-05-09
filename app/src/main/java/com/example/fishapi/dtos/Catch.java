package com.example.fishapi.dtos;

public class Catch {

    private String location;
    private String weight;
    private String size;
    private String species;
    private String description;
    private String imageUri;
    private int catchId;

    public Catch(String location, String weight, String size, String species, String description, String imageUri, int catchId) {
        this.location = location;
        this.weight = weight;
        this.size = size;
        this.species = species;
        this.description = description;
        this.imageUri = imageUri;
        this.catchId = catchId;
    }

    public String getLocation() {
        return location;
    }

    public String getWeight() {
        return weight;
    }

    public String getSize() {
        return size;
    }

    public String getSpecies() {
        return species;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUri() {
        return imageUri;
    }

    public int getCatchId() {
        return catchId;
    }
}
