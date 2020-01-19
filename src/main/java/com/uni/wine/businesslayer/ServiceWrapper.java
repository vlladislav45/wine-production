package com.uni.wine.businesslayer;

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
