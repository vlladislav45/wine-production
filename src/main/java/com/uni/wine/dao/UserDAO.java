package com.uni.wine.dao;

import com.uni.wine.models.User;

public interface UserDAO {

    void changeRole(String username, int id_role);

    void add(User user);

    User getById(int id);

    User getByParams(String username, String password);

    int getId(String name);

    void removeById(int id);

    void update(String passUser, User user);

    int count();
}
