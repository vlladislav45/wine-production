package com.uni.wine.businesslayer;

import com.uni.wine.businesslayer.entities.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    boolean removeUserByName(String username);

    List<Map<String,Object>> getUsers();

    boolean addOperator(String username, String password);

    boolean addHost(String username, String password);

    boolean checkLogin(String username, String password);

    User getLoggedUser();
}
