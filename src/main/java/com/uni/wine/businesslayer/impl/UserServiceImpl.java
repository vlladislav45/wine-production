package com.uni.wine.businesslayer.impl;

import com.uni.wine.businesslayer.entities.UserRole;
import com.uni.wine.dao.UserDAO;
import com.uni.wine.dao.impl.UserRoleDaoImpl;
import com.uni.wine.businesslayer.entities.User;
import com.uni.wine.businesslayer.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDAO userDao;
    private UserRoleDaoImpl userRoleDao;
    private User loggedUser;

    public UserServiceImpl(UserDAO userDao, UserRoleDaoImpl userRoleDao) {
        this.userDao = userDao;
        this.userRoleDao = userRoleDao;
    }

    @Override
    public boolean removeUserByName(String username)
    {
        //TODO When is going to be delete certain user, MySQL give us error cuz the table is connected with other tables
        //TODO that why we have to delete other columns too, like wines is connected with id_user, bottled_wine and grapes
        userDao.removeByUsername(username);
        return true;
    }

    @Override
    public List<Map<String,Object>> getUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public boolean addOperator(String username, String password) {
        User user;
        user = userDao.getByParams(username, password);
        if(user != null) {
            return false;
        }else {
            User newOperator = new User();
            newOperator.setLogin(username);
            newOperator.setPassword(password);
            newOperator.setAccessLevel(new UserRole("OPERATOR"));
            userDao.add(newOperator);

            return true;
        }

    }

    @Override
    public boolean addHost(String username, String password) {
        if(userDao.getHosts() >= 1) {
            return false;
        }else {
            User user = new User();
            user.setLogin(username);
            user.setPassword(password);
            user.setAccessLevel(new UserRole("HOST"));
            userDao.add(user);

            return true;
        }

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
