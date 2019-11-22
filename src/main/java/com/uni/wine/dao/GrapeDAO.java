package com.uni.wine.dao;

import com.uni.wine.models.Grape;
import com.uni.wine.models.User;

public interface GrapeDAO {
    void add(Grape grape);

    Grape getById(int id);

    //TODO test
    float getSumById(int id);

    void removeById(int id);

    void update(float quantity, int id);
}
