package com.demosoft.life.imitation.entity.impl;

import com.demosoft.life.imitation.entity.*;
import com.demosoft.life.logic.math.XMath;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public class CellImpl implements Cell {

    private int x;
    private int y;
    private long value;
    private Human human;
    private Plant plant;
    private Landscape landscape;

    public CellImpl() {
    }

  /*  public CellImpl(long value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
        human = new HumanImpl(this);
        plant = new PlantImpl(this);
        landscape = new LandscapeImpl(this);
    }*/

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setHuman(Human human) {
       this.human = human;
    }

    @Override
    public Human getHuman() {
        return human;
    }

    @Override
    public void setPlant(Plant plant) {
      this.plant = plant;
    }

    @Override
    public Plant getPlant() {
        return plant;
    }

    @Override
    public void setLandscape(Landscape landscape) {
       this.landscape = landscape;
    }

    @Override
    public Landscape getLandscape() {
        return landscape;
    }

    @Override
    public void setActiveFlagHuman(Boolean activeFlagHuman) {
        setValue(UcfCoder.encodeActiveFlagHuman(value, XMath.getValueInRange(booleanToInt(activeFlagHuman), 0, 1)));
    }

    @Override
    public Boolean getActiveFlagHuman() {
        return intToBoolean(UcfCoder.decodeActiveFlagHuman(value));
    }

    @Override
    public void setActiveFlagPlant(Boolean activeFlagPlant) {
        setValue(UcfCoder.encodeActiveFlagPlant(value, XMath.getValueInRange(booleanToInt(activeFlagPlant), 0, 1)));
    }

    @Override
    public Boolean getActiveFlagPlant() {
        return intToBoolean(UcfCoder.decodeActiveFlagPlant(value));
    }


    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    private int booleanToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    private boolean intToBoolean(int i) {
        return i > 0 ? true : false;
    }
}
