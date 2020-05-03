package com.example.travelapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONTravelAPI {
    @POST("/api/auth/login")
    Call<LoginResponse> postLogin(@Body LoginBody loginBody);
}
