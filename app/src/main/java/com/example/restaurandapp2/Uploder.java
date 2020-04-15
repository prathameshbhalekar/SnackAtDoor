package com.example.restaurandapp2;

public class Uploder {
    private String name;
    private String url;
    private String description;
    private String type;
    private String type2;

    public Uploder(){

    }
    public void change(String s){
        name=s;
    }
    public Uploder(String name, String url, String description, String type, String type2) {
        this.name = name;
        this.url = url;
        this.description=description;
        this.type=type;
        this.type2=type2;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }
    public String getType2() {
        return type2;
    }

    public void setName(String name) {
        this.name = name;
    }
}
