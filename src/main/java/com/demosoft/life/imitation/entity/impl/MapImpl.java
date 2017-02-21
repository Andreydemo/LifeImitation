package com.demosoft.life.imitation.entity.impl;

import com.demosoft.life.imitation.entity.Cell;
import com.demosoft.life.imitation.entity.Map;
import com.demosoft.life.imitation.entity.MapFactory;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public class MapImpl implements Map {

    public final static int CELL_SIZE = 10;
    public static final int TREES_TO_PLACE = 20;

    private int size;

    private Cell[][] cells;


    private MapFactoryImpl mapFactory;


    public MapImpl(MapFactory mapFactory) {
        this.size = MapFactoryImpl.mapSize;
        cells = new Cell[size][size];
        this.mapFactory = (MapFactoryImpl) mapFactory;
        fill();

    }

    private void fill() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = mapFactory.createCell(0, i, j);
            }
        }
    }

    public void setCell(CellImpl cell) {
        cells[cell.getX()][cell.getY()] = cell;
    }

    @Override
    public void reCreate(int size   ) {
        this.size = size;
        cells = new Cell[size][size];
        fill();
    }

    public int getSize() {
        return size;
    }

    @Override
    public Cell[][] getCells() {
        return cells;
    }

    @Override
    public Cell getCellAt(int x, int y) {
        if (notInRange(x, y)) {
            return mapFactory.createCell(0, x, y);
        }
        if (cells[x][y] == null) {
            cells[x][y] = mapFactory.createCell();
        }
        return cells[x][y];
    }

    private boolean notInRange(int x, int y) {
        return x > size - 1 || x < 0 || y > size - 1 || y < 0;
    }

}
