package com.demosoft.life.imitation.entity.graphic.impl;

import com.demosoft.life.imitation.entity.Plant;
import com.demosoft.life.imitation.entity.graphic.GraphicPlant;
import com.demosoft.life.imitation.entity.type.PlantType;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public class GraphicPlantImpl implements GraphicPlant {
    private Plant plant;

    public GraphicPlantImpl(Plant plant) {
        this.plant = plant;
    }

    public int getFruits() {
        return plant.getFruits();
    }

    public void setFruits(int fruits) {
        plant.setFruits(fruits);
    }

    public void setType(PlantType plantType) {
        plant.setType(plantType);
    }

    public PlantType getType() {
        return plant.getType();
    }

    @Override
    public Plant getPlant() {
        return plant;
    }

    @Override
    public int getColor() {
        return getType().getColor();
    }

    @Override
    public void setColor(int color) {
        throw new IllegalArgumentException();
    }

    @Override
    public String getMessage() {
        return getType().getMessage();
    }

}
