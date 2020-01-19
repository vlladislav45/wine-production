package com.uni.wine.dao;

import com.uni.wine.businesslayer.entities.Wine;

import java.util.List;
import java.util.Map;

public interface WineDAO {
    int checkWineName(Wine wine);

    void add(Wine wine);

    int getIdByGrape(String wineName, int idGrape);

    int getId(String wineName);

    List<Map<String,Object>> getById(int idWine);

    int count();
}
