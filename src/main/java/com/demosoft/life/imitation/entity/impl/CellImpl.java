package com.demosoft.life.imitation.entity.impl;

import com.demosoft.life.imitation.entity.Cell;
import com.demosoft.life.imitation.entity.CellType;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public class CellImpl implements Cell {

    private int x;
    private int y;
    private int value;

    public CellImpl(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

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
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }
}
