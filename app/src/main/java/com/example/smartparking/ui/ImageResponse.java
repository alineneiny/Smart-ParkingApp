package com.example.smartparking.ui;

public class ImageResponse {
    private String block_code;
   private String block_photo;
   private String location;
  private  String is_block_full;
  private String is_accessible;
    private String number_of_slots;
    private String location_name;

    public ImageResponse() {
    }

    public String getBlock_code() {
        return block_code;
    }

    public void setBlock_code(String block_code) {
        this.block_code = block_code;
    }

    public String getBlock_photo() {
        return block_photo;
    }

    public void setBlock_photo(String block_photo) {
        this.block_photo = block_photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIs_block_full() {
        return is_block_full;
    }

    public void setIs_block_full(String is_block_full) {
        this.is_block_full = is_block_full;
    }

    public String getIs_accessible() {
        return is_accessible;
    }

    public void setIs_accessible(String is_accessible) {
        this.is_accessible = is_accessible;
    }

    public String getNumber_of_slots() {
        return number_of_slots;
    }

    public void setNumber_of_slots(String number_of_slots) {
        this.number_of_slots = number_of_slots;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }
}
