package com.example.smartparking.models;

import java.sql.Time;

public class ReservationRequest {

    private int user;
    private String plate_No;
    private long duration_in_minutes;
    private int parking_block;
    private int amount;
    private String Entry_time;
    private String Exit_time;
    private String booking_date;

    public int getUser_id() {
        return user;
    }
    public int getAmount() {
        return amount;
    }
    public int getParking_block() {
        return parking_block;
    }

    public String getPlate_No() {
        return plate_No;
    }

    public String getEntry_time() {
        return Entry_time;
    }

    public String getExit_time() {
        return Exit_time;
    }

    public long getDuration_in_minutes() {
        return duration_in_minutes;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setUser_id(int user) {
        this.user = user;
    }

    public void setPlate_No(String Plate_No) {
        plate_No = Plate_No;
    }

    public void setDuration_in_minutes(int duration_in_minutes) {
        this.duration_in_minutes = duration_in_minutes;
    }

    public void setParking_block(int parking_block) {
        this.parking_block = parking_block;
    }

    public void setEntry_time(String entry_time) {
        Entry_time = entry_time;
    }

    public void setExit_time(String exit_time) {
        Exit_time = exit_time;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }
}