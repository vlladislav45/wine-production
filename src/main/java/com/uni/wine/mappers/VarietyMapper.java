package com.uni.wine.mappers;

import com.uni.wine.businesslayer.entities.Variety;

import java.util.Map;

public class VarietyMapper {
    public static Variety map(Map<String, Object> result) {

        Variety variety = new Variety();

        variety.setIdVariety((Integer) result.get("id_variety"));
        variety.setVarietyName((String) result.get("variety_name"));

        return variety;
    }
}
