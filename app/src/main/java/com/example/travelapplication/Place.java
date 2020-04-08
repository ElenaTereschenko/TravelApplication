package com.example.travelapplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Place {
    private String id;
    private String userId;
    private String name;
    private String adress;
    private String description;
    private Date date;
    private boolean isVisited;
    private List<String> photos;

    public Place(String id, String userId, String name, String adress, String description, Date date,boolean isVisited, List<String> photos){
        setId(id);
        setUserId(userId);
        setName(name);
        setAdress(adress);
        setDescription(description);
        setDate(date);
        setVisited(isVisited);
        setPhotos(photos);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getIsVisited(){
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getPeriod(){
        if(date!= null ){

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/M/yyyy");

            return sdf1.format(date);
        }
        return "";
    }
}
