package com.example.smartparking.services;

import com.example.smartparking.models.LocationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationInterface {
    @GET("api/location")
    Call<List<LocationResponse>> getAllLocation();
}

