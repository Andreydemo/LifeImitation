package com.demosoft.life.imitation.entity;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public interface MapFactory {

    Map createMap(int size);

    Map generateRandomMap(int size);

    void generateLandscape(Map map);

    Cell createCell();

    Human createHuman(Cell cell);

    Landscape createLandscape(Cell cell);

    Plant createPlant(Cell cell);

    void generatePeoples(Map map, int menCount, int womanCount);

    void generatePlants(Map map, int count);

    void incMapSize();

    void decMapSize();

    void changesMapSize(int step);

    int getMapSize();

}
