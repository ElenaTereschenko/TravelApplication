package com.example.travelapplication;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class TripRealm extends RealmObject implements TripInterface{
    @Required
    private String id;
    private String userId;
    @Required
    private String name;
    private String description;
    private String fromDateTicks;
    private String toDateTicks;





    private static final long  TICKS_AT_EPOCH = 621355968000000000L;
    private static final long TICKS_PER_MILLISECOND = 10000;



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



    public void setFromDateTicks(String fromDateTicks){
        this.fromDateTicks = fromDateTicks;
    }








    public void setToDateTicks(String toDateTicks) {
        this.toDateTicks = toDateTicks;
    }

    public String getFromDateTicks() {
        return fromDateTicks;
    }



    public String getToDateTicks() {
        return toDateTicks;
    }

}
