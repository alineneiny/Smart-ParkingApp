package com.example.smartparking.ui;

import com.google.gson.JsonObject;


public class BlockResponse {
    private String block_code;
   private JsonObject location;
  private  String is_block_full;
  private String is_accessible;
    private String number_of_slots;

    public BlockResponse() {
    }

    public String getBlock_code() {
        return block_code;
    }

    public void setBlock_code(String block_code) {
        this.block_code = block_code;
    }

    public JsonObject getLocation() {
        return location;
    }

    public void setLocation(JsonObject location) {
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

}
