package com.example.digitalartgallery;

public class Artwork {
    private int id;
    private int userId;
    private String imagePath;
    private String title;

    // Constructor
    public Artwork(int id, int userId, String imagePath, String title) {
        this.id = id;
        this.userId = userId;
        this.imagePath = imagePath;
        this.title = title;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTitle() {
        return title;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
