package com.uni.wine.notifications;

import com.uni.wine.models.Bottle;
import com.uni.wine.services.BottleService;
import com.uni.wine.services.GrapeService;

import java.util.Map;

public class NotificationManager implements Runnable {

    //Once per minute
    private static final int CHECK_FOR_AVAILABILITY_TIMEOUT = 60000;
    private static final Integer BOTTLES_MINIMUM_QUANTITY = 10;
    private static final Integer VARIETY_MINIMUM_QUANTITY = 10;

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
        while(true) {
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
        Map<String, Integer> varieties = grapeService.getAvailableVarieties();
        for (Map.Entry<String, Integer> entry : varieties.entrySet()) {
            if(entry.getValue() < VARIETY_MINIMUM_QUANTITY) {
                //TODO: NOTIFICATE UI
            }
        }
    }

    private void checkForBottlesAvailability() {
        Map<Bottle, Integer> bottles = bottleService.getEmptyBottles();
        for (Bottle bottle : bottles.keySet()) {
            if(bottles.get(bottle) < BOTTLES_MINIMUM_QUANTITY) {
                //TODO: NOTIFICATE UI
            }
        }
    }
}
