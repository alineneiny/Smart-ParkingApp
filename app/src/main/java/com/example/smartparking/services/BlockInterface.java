package com.example.smartparking.services;

import com.example.smartparking.ui.BlockResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BlockInterface {
    @GET("api/block")
    Call<List<BlockResponse>> getAllImages();
}

