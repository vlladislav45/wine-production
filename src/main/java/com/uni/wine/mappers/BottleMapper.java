package com.uni.wine.mappers;

import com.uni.wine.models.Bottle;

import java.util.Map;

public class BottleMapper {
    public static Bottle map(Map<String, Object> result) {
            Bottle bottle = new Bottle();

            bottle.setVolume((Integer) result.get("bottle_volume"));
            bottle.setQuantity((Integer) result.get("bottle_quantity"));

            return bottle;
        }
    }


