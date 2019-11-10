package com.uni.wine.mappers;

import com.uni.wine.models.UserRole;

import java.util.Map;

public class UserRoleMapper {

    public static UserRole map(Map<String, Object> map) {
        UserRole userRole = new UserRole();

        userRole.setId((Integer) map.get("id_role"));
        userRole.setRoleName((String) map.get("user_role"));

        return userRole;
    }
}
