package com.demosoft.life.imitation.entity.graphic.impl;

import com.demosoft.life.imitation.entity.Human;
import com.demosoft.life.imitation.entity.graphic.GraphicHuman;
import com.demosoft.life.imitation.entity.type.HumanType;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public class GraphicHumanImpl implements GraphicHuman {
    private Human baseHuman;

    public GraphicHumanImpl(Human baseHuman) {
        this.baseHuman = baseHuman;
    }

    @Override
    public int getAge() {
        return baseHuman.getAge();
    }

    @Override
    public int getEnergy() {
        return baseHuman.getEnergy();
    }

    @Override
    public HumanType getType() {
        return baseHuman.getType();
    }

    @Override
    public int getPregnancy() {
        return baseHuman.getPregnancy();
    }

    @Override
    public int getSatiety() {
        return baseHuman.getSatiety();
    }

    @Override
    public void setAge(int age) {
        baseHuman.setAge(age);
    }

    @Override
    public void setEnergy(int energy) {
        baseHuman.setEnergy(energy);
    }

    @Override
    public void setType(HumanType humanType) {
        baseHuman.setType(humanType);
    }

    @Override
    public void setPregnancy(int pregnancy) {
        baseHuman.setPregnancy(pregnancy);
    }

    @Override
    public void setSatiety(int satiety) {
        baseHuman.setSatiety(satiety);
    }

    @Override
    public String getMessage() {
        return getType().getMessage();
    }

    @Override
    public void setMessage(String message) {
        throw new IllegalArgumentException();
    }

    @Override
    public int getColor() {
        return getType().getColor();
    }

    @Override
    public void setColor(int color) {
        throw new IllegalArgumentException();
    }
}
