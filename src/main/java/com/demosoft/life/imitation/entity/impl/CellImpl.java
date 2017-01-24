package com.demosoft.life.imitation.entity.impl;

import com.demosoft.life.imitation.entity.Cell;
import com.demosoft.life.imitation.entity.CellType;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public class CellImpl implements Cell {

    private CellType cellType;
    private int x;
    private int y;

    public CellImpl(CellType cellType, int x, int y) {
        this.cellType = cellType;
        this.x = x;
        this.y = y;
    }

    public CellImpl(CellType cellType) {
        this.cellType = cellType;
    }

    @Override
    public CellType getCellType() {
        return cellType;
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
}
