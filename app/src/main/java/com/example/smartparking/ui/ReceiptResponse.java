package com.example.smartparking.ui;

public class ReceiptResponse {
    private int user;
    private String plate_No;
    private int duration_in_minutes;
    private String parking_block;
    private int amount;
    private String Entry_time;
    private String Exit_time;
    private String booking_date;

    public ReceiptResponse() {
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getPlate_No() {
        return plate_No;
    }

    public void setPlate_No(String plate_No) {
        this.plate_No = plate_No;
    }
    public int getDuration_in_minutes() {
        return duration_in_minutes;
    }

    public void setDuration_in_minutes(int duration) {
        this.duration_in_minutes = duration;
    }
    public String getEntry_time() {
        return Entry_time;
    }

    public void setEntry_time(String entry_time) {
        this.Entry_time = entry_time;
    }
    public String getExit_time() {
        return Exit_time;
    }

    public void setExit_time(String exit_time) {
        this.Exit_time = exit_time;
    }
    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }
    public String getParking_block() {
        return parking_block;
    }

    public void setParking_block(String parking_block) {
        this.parking_block = parking_block;
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
