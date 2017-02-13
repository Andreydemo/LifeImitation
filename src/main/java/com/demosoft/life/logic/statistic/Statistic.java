package com.demosoft.life.logic.statistic;

import com.demosoft.life.imitation.entity.Map;
import com.demosoft.life.imitation.entity.UcfCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Andrii_Korkoshko on 2/10/2017.
 */
@Component
public class Statistic {

    // LANDSCAPE
    public static final int CELLS = 4225;
    public static final int CELLS_WATER = 1713;
    public static final int CELLS_LAND = 2512;
    // HUMAN
    public static int people = 0;
    public static int peopleMen = 0;
    public static int peopleWomen = 0;
    public static int peopleWomenPregnant = 0;
    public static int peopleAge = 0;
    public static int childrenWereBorn = 0;
    public static int childrenDied = 0;
    public static int peopleDied = 0;
    public static int peopleDiedByAge = 0;
    public static int peopleDiedByEnergy = 0;
    public static int peopleDiedBySatiety = 0;
    public static int peopleDiedByLost = 0;
    // PLANT
    public static int plants = 0;
    public static int plantsFruits = 0;

    // DENSITY
    public static float getPeopleLandDensity() {
        return (float) people / CELLS_LAND;
    }

    public static float getPlantsLandDensity() {
        return (float) plants / CELLS_LAND;
    }

    // MEAN
    public static float getPeopleAgeMean() {
        return people != 0 ? (float) peopleAge / people : 0;
    }

    public static float getPlantsFruitsMean() {
        return plants != 0 ? (float) plantsFruits / plants : 0;
    }

    // RATIO
    public static float getPlantsFruitsPeopleRatio() {
        return people != 0 ? (float) plantsFruits / people : plantsFruits;
    }

    @Autowired
    private Map map;

    public void update() {
        int peopleTemp = 0;
        int peopleMenTemp = 0;
        int peopleWomenTemp = 0;
        int peopleWomenPregnantTemp = 0;
        int peopleAgeTemp = 0;
        int plantsTemp = 0;
        int plantsFruitsTemp = 0;
        for (int y = 0; y < Map.MAP_SIZE; y++) {
            for (int x = 0; x < Map.MAP_SIZE; x++) {
                if (map.getHumanTypeAt(y, x) != UcfCoder.HUMAN_TYPE_EMPTY) {
                    peopleTemp++;
                    peopleAgeTemp += map.getHumanAgeAt(y, x);
                    if (map.getHumanTypeAt(y, x) == UcfCoder.HUMAN_TYPE_MAN) {
                        peopleMenTemp++;
                    } else if (map.getHumanTypeAt(y, x) == UcfCoder.HUMAN_TYPE_WOMAN) {
                        peopleWomenTemp++;
                        if (map.getHumanPregnancyAt(y, x) != 0) {
                            peopleWomenPregnantTemp++;
                        }
                    }
                }
                if (map.getPlantTypeAt(y, x) != UcfCoder.PLANT_TYPE_EMPTY) {
                    plantsTemp++;
                    plantsFruitsTemp += map.getPlantFruitsAt(y, x);
                }
            }
        }
        people = peopleTemp;
        peopleMen = peopleMenTemp;
        peopleWomen = peopleWomenTemp;
        peopleWomenPregnant = peopleWomenPregnantTemp;
        peopleAge = peopleAgeTemp;
        plants = plantsTemp;
        plantsFruits = plantsFruitsTemp;
    }

    public void reset() {
        people = 0;
        peopleMen = 0;
        peopleWomen = 0;
        peopleWomenPregnant = 0;
        peopleAge = 0;
        childrenWereBorn = 0;
        childrenDied = 0;
        peopleDied = 0;
        peopleDiedByAge = 0;
        peopleDiedByEnergy = 0;
        peopleDiedBySatiety = 0;
        peopleDiedByLost = 0;
        plants = 0;
        plantsFruits = 0;
    }
}
