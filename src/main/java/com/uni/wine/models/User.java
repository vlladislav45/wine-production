package com.uni.wine.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {

    private int id;
    private String login;
    private String password;
    private LocalDateTime dateOfCreation;
    UserRole accessLevel;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public UserRole getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(UserRole accessLevel) {
        this.accessLevel = accessLevel;
    }
}
