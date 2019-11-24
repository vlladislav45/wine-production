package com.uni.wine.dao;

import com.uni.wine.models.Wine;

public interface WineDAO {

    void add(Wine wine);

    void update(String oldWineName, String newWineName);

    int getId(String wineName);

    Wine getById(int idWine);

    int count();
}
