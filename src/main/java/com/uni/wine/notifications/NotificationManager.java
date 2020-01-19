package com.uni.wine.notifications;

import com.uni.wine.businesslayer.entities.Bottle;
import com.uni.wine.businesslayer.BottleService;
import com.uni.wine.businesslayer.GrapeService;
import com.uni.wine.controllers.HostController;
import com.uni.wine.controllers.OperatorController;

import java.util.List;
import java.util.Map;

public class NotificationManager implements Runnable {

    //Once per minute
    private static final int CHECK_FOR_AVAILABILITY_TIMEOUT = 180000;
    private static final Integer BOTTLES_MINIMUM_QUANTITY = 20;
    private static final Integer VARIETY_MINIMUM_QUANTITY = 500; // KG

    private BottleService bottleService;
    private GrapeService grapeService;

    public NotificationManager(BottleService bottleService, GrapeService grapeService) {
        this.bottleService = bottleService;
        this.grapeService = grapeService;

        //TODO Maybe move to UI
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                checkForBottlesAvailability();
                checkForVarietyAvailability();
                Thread.sleep(CHECK_FOR_AVAILABILITY_TIMEOUT);
            } catch (InterruptedException e) {
                //This will end when the program stops
            }
        }
    }

    private void checkForVarietyAvailability() {
        double blackGrapes = grapeService.getAllGrapes("Black");
        double whiteGrapes = grapeService.getAllGrapes("White");

            if (blackGrapes < VARIETY_MINIMUM_QUANTITY) {
                HostController hostController = new HostController();
                hostController.criticalMinimumOfBlackGrapes(blackGrapes, VARIETY_MINIMUM_QUANTITY);
            }
            if(whiteGrapes < VARIETY_MINIMUM_QUANTITY) {
                HostController hostController = new HostController();
                hostController.criticalMinimumOfWhiteGrapes(whiteGrapes, VARIETY_MINIMUM_QUANTITY);
            }
    }

    private void checkForBottlesAvailability() {
        List<Map<String, Object>> emptyBottles = bottleService.getAvailableBottles();

        int bottleQuantity750 = 0;
        int bottleQuantity375 = 0;
        int bottleQuantity200 = 0;
        int bottleQuantity187 = 0;
        int counter = 0;
        for (Map<String, Object> emptyBottle : emptyBottles) {
            for (Map.Entry<String, Object> entry : emptyBottle.entrySet()) {
                String key = entry.getKey();
                if (key.equals("bottle_quantity")) {
                    counter++;
                    int value = (Integer) entry.getValue();

                    if (counter == 1) {
                        bottleQuantity187 = value;
                        if (bottleQuantity187 < BOTTLES_MINIMUM_QUANTITY) {
                            HostController hostController = new HostController();
                            hostController.alertBottleQuantity187(bottleQuantity187, BOTTLES_MINIMUM_QUANTITY);
                        }
                    } else if (counter == 2) {
                        bottleQuantity200 = value;
                        if (bottleQuantity200 < BOTTLES_MINIMUM_QUANTITY) {
                            HostController hostController = new HostController();
                            hostController.alertBottleQuantity200(bottleQuantity200, BOTTLES_MINIMUM_QUANTITY);
                        }
                    } else if (counter == 3) {
                        bottleQuantity375 = value;
                        if (bottleQuantity375 < BOTTLES_MINIMUM_QUANTITY) {
                            HostController hostController = new HostController();
                            hostController.alertBottleQuantity375(bottleQuantity375, BOTTLES_MINIMUM_QUANTITY);
                        }
                    } else if (counter == 4) {
                        bottleQuantity750 = value;
                        if (bottleQuantity750 < BOTTLES_MINIMUM_QUANTITY) {
                            HostController hostController = new HostController();
                            hostController.alertBottleQuantity750(bottleQuantity750, BOTTLES_MINIMUM_QUANTITY);

                            counter = 0;
                        }
                    }
                }
            }
        }

    }


}
