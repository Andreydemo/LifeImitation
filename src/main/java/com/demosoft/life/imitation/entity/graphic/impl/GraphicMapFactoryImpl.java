package com.demosoft.life.imitation.entity.graphic.impl;

import com.badlogic.gdx.graphics.Texture;
import com.demosoft.life.imitation.entity.*;
import com.demosoft.life.imitation.entity.graphic.GraphicMap;
import com.demosoft.life.imitation.entity.graphic.GraphicMapFactory;
import com.demosoft.life.imitation.entity.impl.CellImpl;
import com.demosoft.life.imitation.entity.impl.MapFactoryImpl;
import com.demosoft.life.imitation.entity.impl.MapImpl;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public class GraphicMapFactoryImpl extends MapFactoryImpl implements GraphicMapFactory {

    @Override
    public GraphicMap createMap(int size) {
        System.out.println("GraphicMapFactoryImpl map start createMap");
        GraphicMapImpl graphicMap = new GraphicMapImpl(this, (MapImpl) super.createMap(size));
        System.out.println("GraphicMapFactoryImpl map end createMap");
        return graphicMap;
    }

    @Override
    public void generateLandscape(Map map) {
        System.out.println("GraphicMapFactoryImpl map start generateLandscape");
        super.generateLandscape(map);
        ((GraphicMap) map).generateMiniMap();
        System.out.println("GraphicMapFactoryImpl map end generateLandscape");
    }


    @Override
    public Cell createCell() {
        return new GraphicCellImpl((CellImpl) super.createCell());
    }

    @Override
    public Human createHuman(Cell cell) {
        return new GraphicHumanImpl(super.createHuman(cell));
    }

    @Override
    public Landscape createLandscape(Cell cell) {
        return new GraphicLandscapeImpl(super.createLandscape(cell));
    }

    @Override
    public Plant createPlant(Cell cell) {
        return new GraphicPlantImpl(super.createPlant(cell));
    }

    @Override
    public void generatePeoples(Map map, int menCount, int womanCount) {
        System.out.println("GraphicMapFactoryImpl map start generatePeoples");
        super.generatePeoples(map, menCount, womanCount);
        System.out.println("GraphicMapFactoryImpl map end generatePeoples");
    }

    @Override
    public void generatePlants(Map map, int count) {
        System.out.println("GraphicMapFactoryImpl map start generatePlants");
        super.generatePlants(map, count);
        System.out.println("GraphicMapFactoryImpl map end generatePlants");
    }

    @Override
    public int getMapSize() {
        return mapSize;
    }

    @Override
    public GraphicMap generateRandomMap(int size) {
        System.out.println("GraphicMapFactoryImpl map start generateRandomMap");
        GraphicMapImpl graphicMap = (GraphicMapImpl) super.generateRandomMap(size);
        System.out.println("GraphicMapFactoryImpl map end generateRandomMap");
        return graphicMap;
    }

}
