package com.uni.wine.dao;

import com.uni.wine.businesslayer.entities.Bottle;
import com.uni.wine.businesslayer.entities.User;
import com.uni.wine.businesslayer.entities.Wine;

import java.util.List;
import java.util.Map;

public interface BottledWineDAO {
    int getUserId(String username);

    int getBottledWineByWineType(String wineType);

    List<Map<String,Object>> getBottledWineOnUser(String username);

    void add(String wineName, String username, int bottleVolume, int bottleQuantity, int idGrape);

    int getQuantityByType(Wine wine);

    int getQuantityByTypeandUser(Wine wine, User user);

    void addByTypeandUser(String wineName, String username,int bottleVolume, int bottleQuantity, int idGrape);

    void updateByTypeAndUser(Wine wine,User user,int quantity);

    int count();
}
