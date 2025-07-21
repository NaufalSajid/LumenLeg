package com.example.demo.Data;

public class Exercise {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String intensity;

    public Exercise(String id, String name, String description, String imageUrl, String intensity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.intensity = intensity;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getIntensity() { return intensity; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setIntensity(String intensity) { this.intensity = intensity; }
}