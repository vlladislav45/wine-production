package com.uni.wine.mappers;

import com.uni.wine.models.Grape;
import com.uni.wine.models.User;

import java.util.Map;

public class GrapeMapper {
    public static Grape map(Map<String, Object> result) {
        Grape grape = new Grape();

        grape.setIdGrape((Integer) result.get("id_grape"));
        grape.setQuantity((Float) result.get("grape_quantity"));
        //grape.setUser((User) result.get("id_user"));

        return grape;
    }
}
