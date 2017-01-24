package com.demosoft.life.imitation.entity;

import com.demosoft.life.imitation.entity.impl.CellImpl;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public class Map {

    private int height;
    private int width;

    private Cell[][] cells;

    public Map(int height, int width) {
        this.height = height;
        this.width = width;
        cells = new Cell[width][height];
    }

    public Map(int height, int width, CellType defaultCellType) {
        this.height = height;
        this.width = width;
        cells = new Cell[width][height];
        fillMap(defaultCellType);
    }

    private void fillMap(CellType cellType) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new CellImpl(cellType, x, y);
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
