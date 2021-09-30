package com.example.smartparking.services;

import com.example.smartparking.models.ReservationRequest;
import com.example.smartparking.models.ReservationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReservationService {

    @POST("api/reservation")
    Call<ReservationResponse> saveReservation(@Body ReservationRequest reservationRequest);
}
