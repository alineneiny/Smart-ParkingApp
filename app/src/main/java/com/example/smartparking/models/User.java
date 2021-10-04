package com.example.smartparking.models;

public class User {
    private int id;
    private String username;
    private String email;
    private String first_name;
    private String last_name;
    private Boolean is_staff;

    public User(int id, String username, String full_name, String email, String last_name, Boolean is_staff) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.first_name = full_name;
        this.last_name= last_name;
        this.is_staff = is_staff;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Boolean getRole() {
        return is_staff;
    }

    public void setRole(Boolean is_staff) {
        this.is_staff = is_staff;
    }

    public String getLast_name() {
        return last_name;
    }

}
