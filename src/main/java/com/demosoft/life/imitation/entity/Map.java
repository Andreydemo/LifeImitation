package com.demosoft.life.imitation.entity;

import com.demosoft.life.imitation.entity.impl.CellImpl;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public interface Map {

    int getSize();

    Cell[][] getCells();

    Cell getCellAt(int x, int y);

    void setCell(CellImpl cell);
}
