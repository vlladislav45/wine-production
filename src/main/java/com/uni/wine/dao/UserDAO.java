package com.uni.wine.dao;

import com.uni.wine.models.User;
import com.uni.wine.models.UserRole;

public interface UserDAO {

    void changeRole(String username, int id_role);

    void add(User element);

    User getById(int id);

    void removeById(int id);

    void update(User element);

    int count();
}
