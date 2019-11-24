package com.uni.wine.mappers;

import com.uni.wine.models.Wine;

import java.util.Map;

public class WineMapper {
    public static Wine map(Map<String,Object> result) {
        Wine wine = new Wine();

        wine.setIdWine((Integer) result.get("id_wine"));
        wine.setWineName((String) result.get("wine_name"));

        return wine;
    }
}
