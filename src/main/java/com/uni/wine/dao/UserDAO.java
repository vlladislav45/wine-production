package com.uni.wine.dao;

import com.uni.wine.businesslayer.entities.User;

import java.util.List;
import java.util.Map;

public interface UserDAO {

    int getHosts();

    void changeRole(String username, int id_role);

    List<Map<String,Object>> getAllUsers();

    void add(User user);

    User getById(int id);

    User getByParams(String username, String password);

    int getId(String name);

    void removeByUsername(String username);

    void update(String passUser, User user);

    int count();
}
