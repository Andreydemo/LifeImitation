package com.demosoft.life.imitation.entity;

import com.demosoft.life.imitation.entity.impl.CellImpl;

import java.util.Random;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public class Map {

    private int size;

    private Cell[][] cells;

    private Random random = new Random();

    public Map(int size) {
        this.size = size;
        cells = new Cell[size][size];
    }

    public Map(int size, CellType defaultCellType) {
        this.size = size;
        cells = new Cell[size][size];
        fillMapWithZero();

    }

    private void fillMapWithZero() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                setValueAt(0, x, y);
            }
        }
    }

    public void setValueAt(int value, int x, int y) {
        cells[x][y] = new CellImpl(value, x, y);
    }

    public Cell getValueAt(int x, int y) {
        return cells[x][y];
    }

    /*private void generateLandscape() {
        setValueAt(UcfCoder.LANDSCAPE_MOUNTAIN_LOW, 0, 0);
        setValueAt(UcfCoder.LANDSCAPE_MOUNTAIN_LOW, 0, size - 1);
        setValueAt(UcfCoder.LANDSCAPE_MOUNTAIN_LOW, size - 1, 0);
        setValueAt(UcfCoder.LANDSCAPE_MOUNTAIN_LOW, size - 1, size - 1);
        float landscapeShift = 15;
        for (int bigStep = size - 1; bigStep >= 2; bigStep /= 2, landscapeShift /= 2.0f) {
            int smallStep = bigStep / 2;
            //diamond step
            for (int x = smallStep; x < size; x += bigStep) {
                for (int y = smallStep; y < size; y++) {
                    int topLeftValue = getValueAt(x - smallStep, y - smallStep).getValue();
                    int topRightValue = getValueAt(x + smallStep, y - smallStep).getValue();
                    int bottomLeftValue = getValueAt(x - smallStep, y + smallStep).getValue();
                    int bottomRightValue = getValueAt(x + smallStep, y + smallStep).getValue();
                    float average = (topLeftValue + topRightValue + bottomLeftValue + bottomRightValue) / 4;
                    int centralValue = (int) (average + random.nextInt(3) * landscapeShift - landscapeShift); // -landscapeShift 0 landscapeShift
                    setValueAt(centralValue, x, y);
                }
            }
            //square step
            for (int x = 0; x < size; x += smallStep) {
                for (int y = (x + smallStep) % bigStep; y < size; y += bigStep) {
                    int topValue = getValueAt(x, (y - smallStep + size - 1) % (size - 1)).getValue();
                }
            }
        }
    }*/

    private int getValueAtRange(int value, int min, int max) {
        return Math.max(Math.min(value, max), min);
    }

    public int getSize() {
        return size;
    }

}
