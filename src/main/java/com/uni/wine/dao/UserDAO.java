package com.uni.wine.dao;

import com.uni.wine.models.User;
import com.uni.wine.models.UserRole;

public interface UserDAO extends BaseDAO<User> {

    void changeRole(User u, UserRole role);

}
