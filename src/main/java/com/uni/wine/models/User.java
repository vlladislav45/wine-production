package com.uni.wine.models;

public class User {

    private int id;
    private String username;
    private String user_pass;
    UserRole user_role;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return username;
    }

    public void setLogin(String username) {
        this.username = username;
    }

    public String getPassword() {
        return user_pass;
    }

    public void setPassword(String user_pass) {
        this.user_pass = user_pass;
    }

    public UserRole getAccessLevel() {
        return user_role;
    }

    public void setAccessLevel(UserRole user_role) {
        this.user_role = user_role;
    }
}
