package com.uni.wine.dao;

import com.uni.wine.models.Bottle;
import com.uni.wine.models.User;
import com.uni.wine.models.Wine;
import com.uni.wine.models.WineType;

public interface BottledWineDAO {
    void add(Wine wine, User user, Bottle bottle, int quantity);

    int getQuantityByType(Wine wine);

    int getQuantityByTypeandUser(Wine wine, User user);

    void addByTypeandUser(Wine wine,User user,int quantity);

    void removeByTypeandUser(Wine wine,User user,int quantity);
}
