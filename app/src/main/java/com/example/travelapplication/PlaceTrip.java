package com.example.travelapplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlaceTrip implements PlaceInterface {
    private String id;
    private String userId;
    private String name;
    private String adress;
    private String description;
    private Date date;
    private String dateTicks;
    private boolean isVisited;
    private List<String> photos;

    private static final long  TICKS_AT_EPOCH = 621355968000000000L;
    private static final long TICKS_PER_MILLISECOND = 10000;

    public PlaceTrip(String id, String userId, String name, String adress, String description, Date date, boolean isVisited, List<String> photos){
        setId(id);
        setUserId(userId);
        setName(name);
        setAdress(adress);
        setDescription(description);
        setDate(date);
        setDateTicks(date);
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

    public void setDateTicks(Date fromDate){
        if(fromDate != null){
            dateTicks = "" + fromDate.getTime() *TICKS_PER_MILLISECOND + TICKS_AT_EPOCH;
        }
        else{
            dateTicks = null;
        }
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
