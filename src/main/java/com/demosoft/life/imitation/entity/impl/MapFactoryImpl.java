package com.demosoft.life.imitation.entity.impl;

import com.demosoft.life.imitation.entity.*;
import com.demosoft.life.imitation.entity.type.HumanType;
import com.demosoft.life.imitation.entity.type.LandscapeType;
import com.demosoft.life.imitation.entity.type.PlantType;
import com.demosoft.life.logic.random.XRandom;

import java.util.Random;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public class MapFactoryImpl implements MapFactory {

    public static int mapSize = 65;
    public static int map_size_base = 6;
    private Random random = new Random();

    @Override
    public Map createMap(int size) {
        System.out.println("MapFactoryImpl map start createMap");
        MapImpl map = new MapImpl(this);
        System.out.println("MapFactoryImpl map end createMap");
        return map;
    }

    @Override
    public Map generateRandomMap(int size) {
        System.out.println("MapFactoryImpl map start generateRandomMap");
        Map map = createMap(size);
        generateLandscape(map);
        generatePlants(map, 20);
        generatePeoples(map, 10, 10);
        System.out.println("MapFactoryImpl map finish generateRandomMap");
        return map;
    }


    @Override
    public void generatePeoples(Map map, int menCount, int womanCount) {
        System.out.println("MapFactoryImpl map start generatePeoples");
        MapImpl mapImpl = (MapImpl) map;
        cleanHumans(mapImpl);
        for (int i = 0; i < menCount; i++) {
            if (menCount > 0) {
                menCount = tryGenerateMens(mapImpl, menCount);
            } else {
                break;
            }
        }

        for (int i = 0; i < womanCount; i++) {
            if (womanCount > 0) {
                womanCount = tryGenerateWomans(mapImpl, womanCount);
            } else {
                break;
            }
        }
        System.out.println("MapFactoryImpl map end generatePeoples");
    }

    @Override
    public void generatePlants(Map map, int count) {
        System.out.println("MapFactoryImpl map start generatePlants");
        MapImpl mapImpl = (MapImpl) map;
        cleanTrees(mapImpl);
        for (int i = 0; i < count; i++) {
            if (count > 0) {
                count = tryGeneratePlant(mapImpl, count);

            } else {
                break;
            }
        }
        System.out.println("MapFactoryImpl map end generatePlants");
    }

    @Override
    public void incMapSize() {
        changesMapSize(1);
    }

    @Override
    public void decMapSize() {
        changesMapSize(-1);
    }

    @Override
    public void changesMapSize(int step) {
        map_size_base += step;
        mapSize = (int) (Math.pow(2, map_size_base) + 1);
    }

    @Override
    public int getMapSize() {
        return mapSize;
    }

    @Override
    public void generateLandscape(Map map) {
        System.out.println("MapFactoryImpl map start generateLandscape");
        MapImpl mapImpl = (MapImpl) map;

        mapImpl.setCell(createCell(LandscapeType.LANDSCAPE_MAX_VALUE, 0, 0));
        mapImpl.setCell(createCell(LandscapeType.LANDSCAPE_MAX_VALUE, 0, mapImpl.getSize() - 1));
        mapImpl.setCell(createCell(LandscapeType.LANDSCAPE_MAX_VALUE, mapImpl.getSize() - 1, 0));
        mapImpl.setCell(createCell(LandscapeType.LANDSCAPE_MAX_VALUE, mapImpl.getSize() - 1, mapImpl.getSize() - 1));
        final float[] landscapeShift = {20};
        final double[] prcent = {0};
        int log = Integer.numberOfLeadingZeros( mapImpl.getSize() - 1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int bigStep = mapImpl.getSize() - 1; bigStep >= 2; bigStep /= 2, landscapeShift[0] /= 2.0f) {
                    int smallStep = bigStep / 2;
                    //diamond step
                    double newPercent = getPercent(log, bigStep);
                    if (prcent[0] != newPercent) {
                        System.out.print("Gen: " + newPercent + "% ");
                        prcent[0] = newPercent;
                    }
                    diamondStep(mapImpl, landscapeShift[0], bigStep, smallStep);
                    //square step
                    squareStep(mapImpl, landscapeShift[0], bigStep, smallStep);
                }
                System.out.println("MapFactoryImpl map end generateLandscape");
            }
        }).start();

    }

    private double getPercent(int log, int bigStep) {
        int currentLog = Integer.numberOfLeadingZeros(bigStep);
        double v = Math.abs(((double) currentLog / log) - 1);
        int newRes = (int) (v * 10000);
        return (double) newRes / 100;
    }



    public CellImpl createCell(long value, int x, int y) {
        CellImpl cell = (CellImpl) this.createCell();
        cell.setValue(value);
        cell.setX(x);
        cell.setY(y);
        cell.setHuman(this.createHuman(cell));
        cell.setLandscape(this.createLandscape(cell));
        cell.setPlant(this.createPlant(cell));
        return cell;
    }

    @Override
    public Human createHuman(Cell cell) {
        return new HumanImpl((CellImpl) cell);
    }

    @Override
    public Landscape createLandscape(Cell cell) {
        return new LandscapeImpl((CellImpl) cell);
    }

    @Override
    public Plant createPlant(Cell cell) {
        return new PlantImpl((CellImpl) cell);
    }

    @Override
    public Cell createCell() {
        return new CellImpl();
    }

    private int tryGenerateWomans(Map mapImpl, int menCount) {
        for (Cell[] cellRow : mapImpl.getCells()) {
            for (Cell cell : cellRow) {
                LandscapeType landscape = cell.getLandscape().getType();
                HumanType human = cell.getHuman().getType();
                if (!landscape.isRockBlock() && !landscape.isWatterBlock() && human == HumanType.HUMAN_TYPE_EMPTY) {
                    if (XRandom.generateBoolean(1) && menCount > 0) {
                        menCount--;
                        placeWoman(cell);
                    }
                }
            }
        }
        return menCount;
    }

    private void placeWoman(Cell cell) {
        cell.getHuman().setType(HumanType.HUMAN_TYPE_WOMAN);
        initHuman(cell);
    }

    private void initHuman(Cell cell) {
        cell.getHuman().setAge(30 * 300);
        cell.getHuman().setEnergy(63);
        cell.getHuman().setSatiety(63);
    }

    private int tryGenerateMens(Map mapImpl, int menCount) {
        for (Cell[] cellRow : mapImpl.getCells()) {
            for (Cell cell : cellRow) {
                LandscapeType landscape = cell.getLandscape().getType();
                HumanType human = cell.getHuman().getType();
                if (!landscape.isRockBlock() && !landscape.isWatterBlock() && human == HumanType.HUMAN_TYPE_EMPTY) {
                    if (XRandom.generateBoolean(1) && menCount > 0) {
                        menCount--;
                        placeMan(cell);
                    }
                }
            }
        }
        return menCount;
    }

    private void placeMan(Cell cell) {
        cell.getHuman().setType(HumanType.HUMAN_TYPE_MAN);
        initHuman(cell);
    }

    private void cleanHumans(Map map) {
        for (Cell[] cellRow : map.getCells()) {
            for (Cell cell : cellRow) {
                if (cell.getHuman().getType() != HumanType.HUMAN_TYPE_EMPTY) {
                    cell.getHuman().setType(HumanType.HUMAN_TYPE_EMPTY);
                }
            }
        }
    }


    private void cleanTrees(Map mapImpl) {
        for (Cell[] cellRow : mapImpl.getCells()) {
            for (Cell cell : cellRow) {
                if (cell.getPlant().getType() != PlantType.PLANT_TYPE_EMPTY) {
                    cell.getPlant().setType(PlantType.PLANT_TYPE_EMPTY);
                }
            }
        }
    }

    private int tryGeneratePlant(Map mapImpl, int treesToPlace) {
        for (Cell[] cellRow : mapImpl.getCells()) {
            for (Cell cell : cellRow) {
                LandscapeType landscape = cell.getLandscape().getType();
                if (!landscape.isRockBlock() && !landscape.isWatterBlock()) {
                    if (XRandom.generateBoolean(1) && treesToPlace > 0) {
                        treesToPlace--;
                        placeTree(cell);
                    }
                }
            }
        }
        return treesToPlace;
    }

    private void placeTree(Cell cell) {
        PlantType plant = PlantType.getByValue(XRandom.generateInteger(1, 3));
        cell.getPlant().setType(plant);
        cell.getPlant().setFruits(30);
    }

    private void diamondStep(Map map, float landscapeShift, int bigStep, int smallStep) {
        for (int x = smallStep; x < map.getSize(); x += bigStep) {
            for (int y = smallStep; y < map.getSize(); y += bigStep) {
                long topLeftValue = map.getCellAt(x - smallStep, y - smallStep).getLandscape().getHeight();
                long topRightValue = map.getCellAt(x + smallStep, y - smallStep).getLandscape().getHeight();
                long bottomLeftValue = map.getCellAt(x - smallStep, y + smallStep).getLandscape().getHeight();
                long bottomRightValue = map.getCellAt(x + smallStep, y + smallStep).getLandscape().getHeight();
                float average = (topLeftValue + topRightValue + bottomLeftValue + bottomRightValue) / 4;
                int centralValue = (int) (average + random.nextInt(3) * landscapeShift - landscapeShift); // -landscapeShift 0 landscapeShift
                map.setCell(createCell(getValueInRange(centralValue, 1, LandscapeType.LANDSCAPE_MAX_VALUE), x, y));
            }
        }
    }

    private void squareStep(Map map, float landscapeShift, int bigStep, int smallStep) {
        for (int x = 0; x < map.getSize(); x += smallStep) {
            for (int y = (x + smallStep) % bigStep; y < map.getSize(); y += bigStep) {
                long topValue = map.getCellAt((y - smallStep + map.getSize() - 1) % (map.getSize() - 1), x).getLandscape().getHeight();
                long leftValue = map.getCellAt(y, (x - smallStep + map.getSize() - 1) % (map.getSize() - 1)).getLandscape().getHeight();
                long rightValue = map.getCellAt(y, (x + smallStep) % (map.getSize() - 1)).getLandscape().getHeight();
                long bottomValue = map.getCellAt((y + smallStep) % (map.getSize() - 1), x).getLandscape().getHeight();
                float avg = (topValue + leftValue + rightValue + bottomValue) / 4;
                int centerValue = getValueInRange((int) (avg + this.random.nextInt(3) * landscapeShift - landscapeShift), 1, LandscapeType.LANDSCAPE_MAX_VALUE);
                map.setCell(createCell(centerValue, y, x));
            }
        }
    }

    private int getValueInRange(int value, int min, int max) {
        return Math.max(Math.min(value, max), min);
    }
}
