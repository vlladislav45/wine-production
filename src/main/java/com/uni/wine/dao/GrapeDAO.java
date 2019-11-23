package com.uni.wine.dao;

import com.uni.wine.models.Grape;

public interface GrapeDAO {
    void add(Grape grape);

    Grape getById(int id);

    int getGrapeId(int idGrape);

    double getSumById(String varietyName);

    void getGrapeByUsername(String username);

    void removeById(int id);

    void update(float quantity, int id);
}
