package com.uni.wine.services;

import com.uni.wine.dao.BottleDAO;
import com.uni.wine.dao.BottledWineDAO;
import com.uni.wine.dao.GrapeDAO;
import com.uni.wine.dao.WineDAO;
import com.uni.wine.dao.impl.UserDaoImpl;
import com.uni.wine.dao.impl.UserRoleDaoImpl;
import com.uni.wine.dao.impl.VarietyDaoImpl;
import com.uni.wine.dao.impl.WineTypeDaoImpl;

// All Services
public class ServiceWrapper {


    private boolean isInstance = false;
    private UserService userService;
    private GrapeService grapeService;
    private BottleService bottleService;
    private WineService wineService;

    public void setServices(UserService userService, GrapeService grapeService,
                            BottleService bottleService, WineService wineService) {
        if(isInstance) {
            //Ако вече сме го инстанциирали
            return;
        }

        this.userService = userService;
        this.grapeService = grapeService;
        this.bottleService = bottleService;
        this.wineService = wineService;
        isInstance = true;
    }

    public UserService getUserService() {
        return userService;
    }



    public GrapeService getGrapeService() {
        return grapeService;
    }

    public BottleService getBottleService() {
        return bottleService;
    }

    public WineService getWineService() {
        return wineService;
    }
}
