package com.demosoft.life.imitation.entity;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public interface Cell {

    CellType getCellType();

    int getY();

    void setY(int y);

    int getX();

    void setX(int x);

}
