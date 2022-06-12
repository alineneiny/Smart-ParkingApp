package com.example.smartparking.services;

import android.content.Context;

import com.example.smartparking.models.ReceiptRequest;
import com.example.smartparking.models.ReservationRequest;
import com.example.smartparking.models.ReservationResponse;
import com.example.smartparking.ui.ReceiptResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReservationService {
    @POST("api/reservation")
    Call<ReservationResponse> saveReservation(@Body ReservationRequest reservationRequest);

    @POST("api/reservations")
    Call<List<ReceiptResponse>> getAllReceipts(@Body ReceiptRequest receiptRequest);
}
