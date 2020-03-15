package com.example.travelapplication;


import java.util.Date;
import java.util.List;

public class Trip {
    private String id;
    private String userId;
    private String name;
    private String description;
    private List<String> photosId;
    private List<String> placesID;
    private List<String> goodsId;
    private List<String> goalsId;
    private Date fromDate;
    private Date toDate;

    public Trip (String id, String userId, String name, String description, List<String> photosId, List<String> placesID, List<String> goodsId, List<String> goalsId, Date fromDate, Date toDate){
        setId(id);
        setUserId(userId);
        setName(name);
        setDescription(description);
        setPhotosId(photosId);
        setPlacesID(placesID);
        setGoodsId(goodsId);
        setGoalsId(goalsId);
        setFromDate(fromDate);
        setToDate(toDate);
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

    public List<String> getPlacesID(){
        return placesID;
    }

    public void setPlacesID(List<String> placesID){
        this.placesID = placesID;
    }

    public List<String> getGoodsId(){
        return goodsId;
    }

    public void setGoodsId(List<String> goodsId){
        this.goodsId = goodsId;
    }

    public List<String> getGoalsId(){
        return goalsId;
    }

    public void setGoalsId(List<String> goalsId){
        this.goalsId = goalsId;
    }

    public Date getFromDate(){
        return fromDate;
    }

    public  void setFromDate(Date fromDate){
        this.fromDate = fromDate;
    }

    public Date getToDate(){
        return toDate;
    }

    public void setToDate(Date toDate){
        this.toDate = toDate;
    }

    public String getPeriod(){
        if(getToDate()!= null && getFromDate()!= null){
            return getFromDate(). + " - " + getToDate().toString();
        }
        return "";
    }
}
