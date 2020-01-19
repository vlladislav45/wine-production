package com.uni.wine.businesslayer;

import java.util.List;
import java.util.Map;

public interface GrapeService {
    float getSingleUserGrapes(String username, String variety); // get grapes on user by single variety

    double getAllGrapes(String variety); // get all grapes and varieties

    List<Map<String,Object>> getGrapesOnUser(String username); // List all grapes on certain user

    void addGrape(float quantity, String variety, String username); // add grape to the database

    boolean updateQuantity(float quantity, String variety, String username); // update grape/s in database at certain user
}
