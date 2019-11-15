package com.uni.wine.mappers;

import com.uni.wine.models.BottledWine;

import java.util.Map;

public class BottledWineMapper {
    public static BottledWine map(Map<String,Object> result){

        BottledWine bw = new BottledWine();

        //bw.setBottleVolume((Integer) result.get("bottle_type"));
        bw.setQuantityBottled((Integer) result.get("quantity_bottled"));

        return bw;
    }
}
