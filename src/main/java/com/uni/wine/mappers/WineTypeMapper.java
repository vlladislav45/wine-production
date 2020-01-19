package com.uni.wine.mappers;

import com.uni.wine.businesslayer.entities.WineType;

import java.util.Map;

public class WineTypeMapper {
    public static WineType map(Map<String,Object> result) {
        WineType wineType = new WineType();

        wineType.setIdWine((Integer) result.get("id_type"));
        wineType.setTypeName((String) result.get("type_name"));

        return wineType;
    }
}
