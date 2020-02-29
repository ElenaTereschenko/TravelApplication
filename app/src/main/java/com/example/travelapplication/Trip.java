package com.example.travelapplication;


public class Trip {
    private String id;
    private String userId;
    private String name;
    private String textField;
    private String[] photos;

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName(){
        return name;
    }

    public String getTextField(){
        return textField;
    }

    public String[] getPhotos() {
        return photos;
    }
}
