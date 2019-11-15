package com.uni.wine.dao;

import com.uni.wine.models.Grape;

public interface GrapeDAO {
    void add(Grape grape);

    Grape getById(int id);

    Grape getSumById(int id);

    int removeById(int id);

    int count();
}
