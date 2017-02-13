package com.demosoft.life.imitation.entity;

import com.demosoft.life.imitation.entity.impl.CellImpl;
import com.demosoft.life.logic.math.XMath;

import java.util.Random;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public class Map {

    public final static int MAP_SIZE = 65;
    public final static int CELL_SIZE = 10;

    private int size;

    private Cell[][] cells;

    private Random random = new Random();

    private int selectedY;
    private int selectedX;

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

    public void setValueAt(long value, int x, int y) {
        cells[x][y] = new CellImpl(value, x, y);
    }

    public void setRawDataAt(long uc, int y, int x) {
        setValueAt(uc, y, x);
    }

    public long getRawDataAt(int y, int x) {
        return getValueAt(y, x).getValue();
    }

    public Cell getValueAt(int x, int y) {
        return cells[x][y];
    }

    public void setLandscapeTypeAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeLandscapeType(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 7)), y, x);
    }

    public int getLandscapeTypeAt(int y, int x) {
        return UcfCoder.decodeLandscapeType(getRawDataAt(y, x));
    }

    public void setHumanTypeAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeHumanType(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 3)), y, x);
    }

    public int getHumanTypeAt(int y, int x) {
        return UcfCoder.decodeHumanType(getRawDataAt(y, x));
    }

    public void setHumanAgeAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeHumanAge(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 32767)), y, x);
    }

    public int getHumanAgeAt(int y, int x) {
        return UcfCoder.decodeHumanAge(getRawDataAt(y, x));
    }

    public void setHumanEnergyAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeHumanEnergy(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 63)), y, x);
    }

    public int getHumanEnergyAt(int y, int x) {
        return UcfCoder.decodeHumanEnergy(getRawDataAt(y, x));
    }

    public void setHumanSatietyAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeHumanSatiety(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 63)), y, x);
    }

    public int getHumanSatietyAt(int y, int x) {
        return UcfCoder.decodeHumanSatiety(getRawDataAt(y, x));
    }

    public void setHumanPregnancyAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeHumanPregnancy(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 511)), y, x);
    }

    public int getHumanPregnancyAt(int y, int x) {
        return UcfCoder.decodeHumanPregnancy(getRawDataAt(y, x));
    }

    public void setPlantTypeAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodePlantType(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 1)), y, x);
    }

    public int getPlantTypeAt(int y, int x) {
        return UcfCoder.decodePlantType(getRawDataAt(y, x));
    }

    public void setPlantFruitsAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodePlantFruits(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 63)), y, x);
    }

    public int getPlantFruitsAt(int y, int x) {
        return UcfCoder.decodePlantFruits(getRawDataAt(y, x));
    }

    public void setActiveFlagHumanAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeActiveFlagHuman(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 1)), y, x);
    }

    public int getActiveFlagHumanAt(int y, int x) {
        return UcfCoder.decodeActiveFlagHuman(getRawDataAt(y, x));
    }

    public void setActiveFlagPlantAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeActiveFlagPlant(getRawDataAt(y, x), XMath.getValueInRange(u, 0, 1)), y, x);
    }

    public int getActiveFlagPlantAt(int y, int x) {
        return UcfCoder.decodeActiveFlagPlant(getRawDataAt(y, x));
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

    public int getSelectedY() {
        return selectedY;
    }

    public int getSelectedX() {
        return selectedX;
    }

    public void setSelectedX(int selectedX) {
        this.selectedX = selectedX;
    }

    public void setSelectedY(int selectedY) {
        this.selectedY = selectedY;
    }
}
