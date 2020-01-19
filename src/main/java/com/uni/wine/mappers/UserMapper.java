package com.uni.wine.mappers;

import com.uni.wine.businesslayer.entities.User;

import java.util.Map;

public class UserMapper {
    public static User map(Map<String, Object> result) {
        User u = new User();
        u.setId((Integer) result.get("id_user"));
        u.setLogin((String) result.get("username"));
        u.setPassword((String) result.get("user_pass"));

        return u;
    }
}
