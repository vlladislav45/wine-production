package com.uni.wine.dao;

import com.uni.wine.models.Wine;

public interface WineDAO {

    void add(Wine wine);

    void update();

    int getId(int idWine);

    Wine getById(int idWine);

    int count();
}
