package com.example.travelapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JSONTravelAPI {
    @POST("/api/auth/login")
    Call<LoginResponse> postLogin(@Body LoginBody loginBody);
    @GET("api/trip/getallids")
    Call<List<String>> getAllTrips(@Query("token") String token);
    @GET("api/trip/read")
    Call<Trip> getTrip(@Query("id") String id, @Query("token") String token);
    @GET("api/place/read")
    Call<PlaceTrip> getPlace(@Query("id") String id, @Query("token") String token);
}
