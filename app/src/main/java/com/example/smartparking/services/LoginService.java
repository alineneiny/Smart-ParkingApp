package com.example.smartparking.services;

import com.example.smartparking.models.LoginRequest;
import com.example.smartparking.models.LoginResponse;
import com.example.smartparking.models.RegisterRequest;
import com.example.smartparking.models.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("api/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
