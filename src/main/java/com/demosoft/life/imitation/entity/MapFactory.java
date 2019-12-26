package com.demosoft.life.imitation.entity;

import com.demosoft.life.imitation.entity.type.LandscapeType;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public interface MapFactory {

    Map createMap(int size);

    Map generateRandomMap(int size);

    CompletableFuture<Void> generateLandscape(Map map);

    Cell createCell();

    Human createHuman();

    Landscape createLandscape();

    Landscape createLandscape(LandscapeType type);

    Plant createPlant();

    void generatePeoples(Map map, int menCount, int womanCount);

    void generatePlants(Map map, int count);

    void incMapSize();

    void decMapSize();

    void changesMapSize(int step);

    int getMapSize();

}
