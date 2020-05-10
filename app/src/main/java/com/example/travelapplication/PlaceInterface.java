package com.example.travelapplication;

import java.util.Date;

public interface PlaceInterface {
     String getId();

     void setId(String id);

     String getUserId();

     void setUserId(String userId);

     String getName();

     void setName(String name);

     String getAdress();

     void setAdress(String adress);

     String getDescription();

     void setDescription(String description);


     boolean getIsVisited();

     void setVisited(boolean visited);


     String getDateTicks();
}
