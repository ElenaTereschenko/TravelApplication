package com.example.travelapplication;


import java.util.List;

public class Trip {
    private String id;
    private String userId;
    private String name;
    private String description;
    private List<String> photosId;

    public Trip (String id, String userId, String name, String description, List<String> photosId){
        setId(id);
        setUserId(userId);
        setName(name);
        setDescription(description);
        setPhotosId(photosId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public List<String> getPhotosId() {
        return photosId;
    }

    public void setPhotosId(List<String> photosId) {
        this.photosId = photosId;
    }
}
