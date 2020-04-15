package com.example.restaurandapp2;

public class modelClass {
    private int image;
    private String title;
    private String body;

    public modelClass(int image, String title, String body) {
        this.image = image;
        this.title = title;
        this.body = body;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
