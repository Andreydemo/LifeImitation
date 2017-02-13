package com.demosoft.life.imitation.entity;

import com.demosoft.life.imitation.entity.impl.CellImpl;
import com.demosoft.life.imitation.entity.type.Landscape;
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

    public Map(Cell[][] cells) {
        this.size = cells[0].length;
        this.cells = cells;

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

    public void setRawDataAt(long uc, int x, int y) {
        setValueAt(uc, x, y);
    }

    public long getRawDataAt(int x, int y) {
        return getValueAt(x, y).getValue();
    }

    public Cell getValueAt(int x, int y) {
        return cells[x][y];
    }

    public void setLandscapeTypeAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeLandscapeType(getRawDataAt(x, y), XMath.getValueInRange(u, 0, 7)), x, y);
    }

    public int getLandscapeTypeAt(int y, int x) {
        return UcfCoder.decodeLandscapeType(getRawDataAt(x, y));
    }

    public void setHumanTypeAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeHumanType(getRawDataAt(x, y), XMath.getValueInRange(u, 0, 3)), x, y);
    }

    public int getHumanTypeAt(int y, int x) {
        return UcfCoder.decodeHumanType(getRawDataAt(x, y));
    }

    public void setHumanAgeAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeHumanAge(getRawDataAt(x, y), XMath.getValueInRange(u, 0, 32767)), x, y);
    }

    public int getHumanAgeAt(int y, int x) {
        return UcfCoder.decodeHumanAge(getRawDataAt(x, y));
    }

    public void setHumanEnergyAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeHumanEnergy(getRawDataAt(x, y), XMath.getValueInRange(u, 0, 63)), x, y);
    }

    public int getHumanEnergyAt(int y, int x) {
        return UcfCoder.decodeHumanEnergy(getRawDataAt(x, y));
    }

    public void setHumanSatietyAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeHumanSatiety(getRawDataAt(x, y), XMath.getValueInRange(u, 0, 63)), x, y);
    }

    public int getHumanSatietyAt(int y, int x) {
        return UcfCoder.decodeHumanSatiety(getRawDataAt(x, y));
    }

    public void setHumanPregnancyAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeHumanPregnancy(getRawDataAt(x, y), XMath.getValueInRange(u, 0, 511)), x, y);
    }

    public int getHumanPregnancyAt(int y, int x) {
        return UcfCoder.decodeHumanPregnancy(getRawDataAt(x, y));
    }

    public void setPlantTypeAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodePlantType(getRawDataAt(x, y), XMath.getValueInRange(u, 0, 1)), x, y);
    }

    public int getPlantTypeAt(int y, int x) {
        return UcfCoder.decodePlantType(getRawDataAt(x, y));
    }

    public void setPlantFruitsAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodePlantFruits(getRawDataAt(x, y), XMath.getValueInRange(u, 0, 63)), x, y);
    }

    public int getPlantFruitsAt(int y, int x) {
        return UcfCoder.decodePlantFruits(getRawDataAt(x, y));
    }

    public void setActiveFlagHumanAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeActiveFlagHuman(getRawDataAt(x, y), XMath.getValueInRange(u, 0, 1)), x, y);
    }

    public int getActiveFlagHumanAt(int y, int x) {
        return UcfCoder.decodeActiveFlagHuman(getRawDataAt(x, y));
    }

    public void setActiveFlagPlantAt(int u, int y, int x) {
        setRawDataAt(UcfCoder.encodeActiveFlagPlant(getRawDataAt(x, y), XMath.getValueInRange(u, 0, 1)), x, y);
    }

    public int getActiveFlagPlantAt(int y, int x) {
        return UcfCoder.decodeActiveFlagPlant(getRawDataAt(x, y));
    }

    public void generateLandscape() {
        try {


            setValueAt(Landscape.LANDSCAPE_TYPE_WATER_LOW.getValue(), 0, 0);
            setValueAt(Landscape.LANDSCAPE_TYPE_WATER_LOW.getValue(), 0, size - 1);
            setValueAt(Landscape.LANDSCAPE_TYPE_WATER_LOW.getValue(), size - 1, 0);
            setValueAt(Landscape.LANDSCAPE_TYPE_WATER_LOW.getValue(), size - 1, size - 1);
            float landscapeShift = 5;
            for (int bigStep = size - 1; bigStep >= 2; bigStep /= 2, landscapeShift /= 2.0f) {
                int smallStep = bigStep / 2;
                //diamond step
                for (int x = smallStep; x < this.size; x += bigStep) {
                    for (int y = smallStep; y < this.size; y += bigStep) {
                        long topLeftValue = getValueAt(x - smallStep, y - smallStep).getValue();
                        long topRightValue = getValueAt(x + smallStep, y - smallStep).getValue();
                        long bottomLeftValue = getValueAt(x - smallStep, y + smallStep).getValue();
                        long bottomRightValue = getValueAt(x + smallStep, y + smallStep).getValue();
                        float average = (topLeftValue + topRightValue + bottomLeftValue + bottomRightValue) / 4;
                        int centralValue =1 + (int) (average + random.nextInt(3) * landscapeShift - landscapeShift); // -landscapeShift 0 landscapeShift
                        setValueAt(centralValue, x, y);
                    }
                }
                //square step
                for (int x = 0; x < this.size; x += smallStep) {
                    for (int y = (x + smallStep) % bigStep; y < this.size; y += bigStep) {
                        long topValue = getValueAt((y - smallStep + this.size - 1) % (this.size - 1), x).getValue();
                        long leftValue = (int) getValueAt(y, (x - smallStep + this.size - 1) % (this.size - 1)).getValue();
                        long rightValue = (int) getValueAt(y, (x + smallStep) % (this.size - 1)).getValue();
                        long bottomValue = (int) getValueAt((y + smallStep) % (this.size - 1), x).getValue();
                        float avg = (topValue + leftValue + rightValue + bottomValue) / 4;
                        int centerValue = 1 + getValueInRange((int) (avg + this.random.nextInt(3) * landscapeShift - landscapeShift), 1, 15);
                        setValueAt(centerValue, y, x);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int getValueInRange(int value, int min, int max) {
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
