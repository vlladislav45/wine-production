package com.uni.wine.services;

import com.uni.wine.models.User;

public interface UserService {

    boolean checkLogin(String username, String password);

    User getLoggedUser();
}
