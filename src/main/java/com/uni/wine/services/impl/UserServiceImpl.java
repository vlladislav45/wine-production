package com.uni.wine.services.impl;

import com.uni.wine.dao.UserDAO;
import com.uni.wine.dao.impl.UserDaoImpl;
import com.uni.wine.dao.impl.UserRoleDaoImpl;
import com.uni.wine.models.User;
import com.uni.wine.services.UserService;

public class UserServiceImpl implements UserService {
    private UserDAO userDao;
    private User loggedUser;

    public UserServiceImpl(UserDAO userDao, UserRoleDaoImpl userRoleDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean checkLogin(String username, String password){

        User user;
        user = userDao.getByParams(username, password);
        if(user != null) {
            this.loggedUser = user;
            return true;
        }else {
            return false;
        }

    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
