package com.example.travelapplication;

import io.realm.RealmObject;

public class PlaceRealm extends RealmObject implements PlaceInterface {
    private String id;
    private String userId;
    private String name;
    private String adress;
    private String description;
    private String dateTicks;
    private boolean isVisited;

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


    public void setDateTicks(String dateTicks){
        this.dateTicks = dateTicks;
    }


    public String getDateTicks() {
        return   dateTicks;
    }

    public boolean getIsVisited(){
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

}
