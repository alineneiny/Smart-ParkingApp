package com.example.smartparking.models;

public class User {
    //private int id;
    private int  pk;
    private String username;
    private String email;
    private String full_name;
    private String role;
    private String phone_number;

    public User(int pk, String username, String full_name, String email, String phone_number, String role) {
        this.pk = pk;
        this.username = username;
        this.email = email;
        this.full_name = full_name;
        this.role = role;
        this.phone_number = phone_number;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
