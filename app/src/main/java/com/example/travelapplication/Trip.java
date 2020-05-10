package com.example.travelapplication;


import android.os.Parcel;
import android.os.Parcelable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Trip implements TripInterface,Parcelable {
    private String id;
    private String userId;
    private String name;
    private String description;
    private List<String> photosId;
    private List<String> placesID;
    private List<String> goodsId;
    private List<String> goalsId;
    private List<String> purchasesId;
    private Date fromDate;
    private Date toDate;
    private String fromDateTicks;
    private String toDateTicks;

    private static final long  TICKS_AT_EPOCH = 621355968000000000L;
    private static final long TICKS_PER_MILLISECOND = 10000;

    public Trip(){
        setId(null);
        setUserId(null);
        setName(null);
        setDescription(null);
        setPhotosId(null);
        setPlacesID(null);
        setGoodsId(null);
        setGoalsId(null);
        setPurchasesId(null);
        setFromDate(null);
        setToDate(null);
        setFromDateTicks(null);
        setToDateTicks(null);
    }

    public Trip(Trip trip){
        setId(trip.getId());
        setUserId(trip.getUserId());
        setName(trip.getName());
        setDescription(trip.getDescription());
        setPhotosId(trip.getPhotosId());
        setPlacesID(trip.getPlacesID());
        setGoodsId(trip.getGoodsId());
        setGoalsId(trip.getGoalsId());
        setPurchasesId(trip.getPurchasesId());
        setFromDate(trip.getFromDate());
        setToDate(trip.getToDate());
        setFromDateTicks(trip.getFromDate());
        setToDateTicks(trip.getToDate());
    }

    public Trip (String id, String userId, String name, String description, List<String> photosId, List<String> placesID, List<String> goodsId, List<String> goalsId, List<String> purchasesId, Date fromDate, Date toDate){
        setId(id);
        setUserId(userId);
        setName(name);
        setDescription(description);
        setPhotosId(photosId);
        setPlacesID(placesID);
        setGoodsId(goodsId);
        setGoalsId(goalsId);
        setPurchasesId(purchasesId);
        setFromDate(fromDate);
        setToDate(toDate);
        setFromDateTicks(fromDate);
        setToDateTicks(toDate);
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

    public List<String> getPurchasesId(){
        return purchasesId;
    }

    public void setPurchasesId(List<String> purchasesId){
        this.purchasesId = purchasesId;
    }

    public Date getFromDate(){
        return fromDate;
    }

    public void setFromDateTicks(Date fromDate){
        if(fromDate != null){
            fromDateTicks = "" + fromDate.getTime() *TICKS_PER_MILLISECOND + TICKS_AT_EPOCH;
        }
        else{
            fromDateTicks = null;
        }
    }

    @Override
    public String getFromDateTicks() {
        return   fromDateTicks;
    }




    public  void setFromDate(Date fromDate){
        this.fromDate = fromDate;
    }

    public Date getToDate(){
        return toDate;
    }

    public void setToDateTicks(Date toDate) {
        if(toDateTicks != null){
            this.toDateTicks = ""+ toDate.getTime() *TICKS_PER_MILLISECOND + TICKS_AT_EPOCH ;
        }
        else{
            toDateTicks = null;
        }
    }

    @Override
    public String getToDateTicks() {
        return  toDateTicks;
    }



    public void setToDate(Date toDate){
        this.toDate = toDate;
    }

    public String getPeriod(){
        if(getToDate()!= null && getFromDate()!= null){

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/M/yyyy");

            return new String(sdf1.format(fromDate) + " - " + sdf1.format(toDate));
        }
        return "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeStringList(photosId);
        dest.writeStringList(placesID);
        dest.writeStringList(goodsId);
        dest.writeStringList(goalsId);
        dest.writeStringList(purchasesId);
        dest.writeSerializable(fromDate);
        dest.writeSerializable(toDate);

    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel source) {
            String id = source.readString();
            String userid = source.readString();
            String name = source.readString();
            String description = source.readString();
            List<String> photosId = new ArrayList<>();
            source.readStringList(photosId);
            List<String> placesId = new ArrayList<>();
            source.readStringList(placesId);
            List<String> goodsId = new ArrayList<>();
            source.readStringList(goodsId);
            List<String> goalsId = new ArrayList<>();
            source.readStringList(goalsId);
            List<String> purchasesId = new ArrayList<>();
            source.readStringList(purchasesId);
            Date fromDate = (Date) source.readSerializable();
            Date toDate = (Date) source.readSerializable();

            return new Trip(id, userid, name, description, photosId,placesId,goodsId,goalsId, purchasesId,fromDate,toDate);

        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };
}
