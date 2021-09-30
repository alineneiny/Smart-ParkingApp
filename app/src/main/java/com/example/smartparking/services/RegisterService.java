package com.example.smartparking.services;

import com.example.smartparking.models.RegisterRequest;
import com.example.smartparking.models.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {

    @POST("api/register")
    Call<RegisterResponse> saveRegister(@Body RegisterRequest registerRequest);
}
