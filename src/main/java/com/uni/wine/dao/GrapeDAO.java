package com.uni.wine.dao;

import com.uni.wine.businesslayer.entities.Grape;

import java.util.List;
import java.util.Map;

public interface GrapeDAO {
    float getSingleUserGrapes(String username, String variety); // get grapes on user by single variety

    List<Map<String,Object>> getUserGrapes(String username);

    int getUserId(String username, String variety);

    void add(Grape grape);

    Grape getById(int id);

    int getIdGrapeById(int idGrape);

    int getGrapeId(String username, String varietyName);

    double getSumByVariety(String varietyName);

    void removeById(int id);

    void update(float quantity, int idUser, int idVariety);
}
