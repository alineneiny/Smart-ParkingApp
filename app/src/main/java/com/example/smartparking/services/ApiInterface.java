package com.example.smartparking.services;

import com.example.smartparking.ui.ImageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("api/block")
    Call<List<ImageResponse>> getAllImages();
}

