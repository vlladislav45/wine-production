package com.uni.wine.businesslayer.impl;

import com.uni.wine.businesslayer.entities.Grape;
import com.uni.wine.businesslayer.entities.User;
import com.uni.wine.businesslayer.entities.Variety;
import com.uni.wine.dao.BottleDAO;
import com.uni.wine.businesslayer.entities.Bottle;
import com.uni.wine.businesslayer.BottleService;
import com.uni.wine.dao.BottledWineDAO;
import com.uni.wine.dao.GrapeDAO;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BottleServiceImpl implements BottleService {
    private BottleDAO bottleDao;
    private BottledWineDAO bottledWineDao;
    private GrapeDAO grapeDao;

    //Тук се записва по колко от всяка бутилка ще се използва
    private int x7;
    private int x3;
    private int x2;
    private int x1;

    public BottleServiceImpl(BottleDAO bottleDao, BottledWineDAO bottleWineDao, GrapeDAO grapeDao) {
        this.bottleDao = bottleDao;
        this.bottledWineDao = bottleWineDao;
        this.grapeDao = grapeDao;
    }

    @Override
    public void removeBottle(int bottleVolume, int bottleQuantity) {
        Bottle bottle = new Bottle(bottleVolume, bottleQuantity);
        bottleDao.removeBottle(bottle);
    }

    @Override
    public int getBottledWineByWineType(String wineType) {
        return bottledWineDao.getBottledWineByWineType(wineType);
    }

    // get list with bottled wine at certain user
    @Override
    public List<Map<String,Object>> getBottledWineOnUser(String username) { return bottledWineDao.getBottledWineOnUser(username); }

    @Override
    public List<Map<String,Object>> getAvailableBottles() {
        return bottleDao.getAllBottles();
    }

    @Override
    public boolean addBottle(int volume, int quantity) {
        int bottleQuantity = bottleDao.getQuantity(volume);
        if(bottleQuantity == -1) {
            Bottle bottle = new Bottle();
            bottle.setVolume(volume);
            bottle.setQuantity(quantity);
            bottleDao.add(bottle);

            return false; // adding
        }

        return true; // is added
    }

    @Override
    public boolean updateBottle(int volume, int quantity) {

        if(quantity != 0) {
            Bottle bottle = new Bottle();
            bottle.setVolume(volume);
            bottle.setQuantity(quantity);
            bottleDao.updateQuantity(bottle);

            return true;
        }

        return false;
    }

    @Override
    public void addbottledWines(String wineName, String username,String varietyName, int bottleVolume, int bottleQuantity) {
        int idGrape = grapeDao.getGrapeId(username, varietyName);

        bottledWineDao.add(wineName, username, bottleVolume, bottleQuantity, idGrape);
    }

    @Override
    public boolean updateBottledWines(String wineName, String username, String varietyName ,int bottleVolume, int bottleQuantity){

        int idUser = bottledWineDao.getUserId(username);
        if(idUser != 0) {
            int idGrape = grapeDao.getGrapeId(username, varietyName);

            bottledWineDao.addByTypeandUser(wineName, username,bottleVolume, bottleQuantity, idGrape);
        }else {
            return false;
        }

        return true;
    }

    @Override
    public List<Integer> fillTheBottles(float Qwine) {
        //Litres to ml
        Qwine *= 1000;

        //Тук е максималния брой бутилки за получаване на желаното количество(за да няма излишно смятане)
        int max7;
        int max3;
        int max2;
        int max1;
        float currentSum;
        float bestSum = 0;
        //Налични бутилки
        int Q750 = 0; // 750ml
        int Q375 = 0; //375ml
        int Q200 = 0; //200ml
        int Q187 = 0; //187ml


        Q750 = bottleDao.getQuantity(750);
        Q375 = bottleDao.getQuantity(375);
        Q200 = bottleDao.getQuantity(200);
        Q187 = bottleDao.getQuantity(187);

        //Максималното вино което може да се побере в наличните бутилки
        float cap=Q750*750+Q375*375+Q200*200+Q187*187;
        //Ако няма достатъчно бутилки за всичкото вино няма да се смята
        List<Integer> quantities = new ArrayList();
        if(Qwine>cap) {
            quantities.clear();
            return quantities;
        }
        //Ако има достатъчно бутилки
        else {
            //До колко бутилки от даден тип да се изпробва
            max7=(int)Qwine/750;
            if(max7>Q750)
                max7=Q750;
            max3=(int)Qwine/375;
            if(max3>Q375)
                max3=Q375;
            max2=(int)Qwine/200;
            if(max2>Q200)
                max2=Q200;
            max1=(int)Qwine/187;
            if(max1>Q187)
                max1=Q187;
            //Прави всички възможни комбинации и запазва най добрата
            for(int x = 0 ; x <= max7 ; x++) {
                for(int y = 0 ; y <= max3 ; y++) {
                    for(int z = 0 ; z <= max2 ; z++) {
                        for (int w = 0; w <= max1; w++) {
                            currentSum=(750*x)+(375*y)+(200*z)+(187*w);
                            if(currentSum > Qwine && bestSum > Qwine) {
                                if (currentSum < bestSum) {
                                    bestSum = currentSum;
                                    SaveSumQuantities(x,y,z,w);
                                }
                            }
                            if(currentSum < Qwine && bestSum < Qwine){
                                if(currentSum > bestSum) {
                                    bestSum = currentSum;
                                    SaveSumQuantities(x,y,z,w);
                                }
                            }
                            if(currentSum < Qwine && bestSum > Qwine){
                                if(Qwine - currentSum < bestSum - Qwine) {
                                    bestSum = currentSum;
                                    SaveSumQuantities(x,y,z,w);
                                }
                            }
                            if(currentSum > Qwine && bestSum < Qwine){
                                if(currentSum - Qwine < Qwine - bestSum) {
                                    bestSum = currentSum;
                                    SaveSumQuantities(x,y,z,w);
                                }
                            }
                            if(currentSum==Qwine)
                            {
                                bestSum = currentSum;
                                SaveSumQuantities(x,y,z,w);
                            }
                        }
                    }
                }
            }

            quantities.add(x7);
            quantities.add(x3);
            quantities.add(x2);
            quantities.add(x1);

            return quantities;
        }
    }

    public void SaveSumQuantities(int _x,int _y ,int _z,int _w)
    {
        x7=_x;
        x3=_y;
        x2=_z;
        x1=_w;
    }

}
