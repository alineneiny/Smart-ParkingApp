package com.example.smartparking.models;

public class User {
    private int id;
    private String username;
    private String email;
    private String first_name;
    private String last_name;
    private String role;

    public User(int id, String username, String full_name, String email, String last_name, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.first_name = full_name;
        this.role = role;
        this.last_name= last_name;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLast_name() {
        return last_name;
    }

}
