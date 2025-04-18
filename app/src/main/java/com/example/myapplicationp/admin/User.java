package com.example.myapplicationp.admin;

public class User {
    private String name, email, phone;

    public User() {
        // Required for Firebase
    }

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}
