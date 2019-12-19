package com.demosoft.life.imitation.entity.v2.impl;

import static com.demosoft.life.imitation.entity.type.LandscapeType.LANDSCAPE_MAX;
import static com.demosoft.life.imitation.entity.type.LandscapeType.LANDSCAPE_MAX_VALUE;
import static com.demosoft.life.imitation.entity.type.LandscapeType.LANDSCAPE_MIN;

import com.demosoft.life.imitation.entity.Cell;
import com.demosoft.life.imitation.entity.Human;
import com.demosoft.life.imitation.entity.Landscape;
import com.demosoft.life.imitation.entity.Map;
import com.demosoft.life.imitation.entity.MapFactory;
import com.demosoft.life.imitation.entity.Plant;
import com.demosoft.life.imitation.entity.type.HumanType;
import com.demosoft.life.imitation.entity.type.LandscapeType;
import com.demosoft.life.imitation.entity.type.PlantType;
import com.demosoft.life.logic.random.XRandom;
import java.util.Optional;
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
            menCount = tryGenerateMens(mapImpl, menCount);
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
            count = tryGeneratePlant(mapImpl, count);

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

        mapImpl.setCell(createCell(LANDSCAPE_MAX, 0, 0));
        mapImpl.setCell(createCell(LANDSCAPE_MAX, 0, mapImpl.getSize() - 1));
        mapImpl.setCell(createCell(LANDSCAPE_MAX, mapImpl.getSize() - 1, 0));
        mapImpl.setCell(createCell(LANDSCAPE_MAX, mapImpl.getSize() - 1, mapImpl.getSize() - 1));
        final float[] landscapeShift = {20};
        final double[] prcent = {0};
        int log = Integer.numberOfLeadingZeros(mapImpl.getSize() - 1);
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


    public CellImpl createCell(int x, int y) {
        CellImpl cell = (CellImpl) this.createCell();
        cell.setX(x);
        cell.setY(y);
        return cell;
    }

    public CellImpl createCell(LandscapeType landscapeType, int x, int y) {
        CellImpl cell = (CellImpl) this.createCell();
        cell.setX(x);
        cell.setY(y);
        cell.setLandscape(new LandscapeImpl(landscapeType));
        return cell;
    }

    @Override
    public Human createHuman() {
        return new HumanImpl();
    }

    @Override
    public Landscape createLandscape() {
        return new LandscapeImpl(LANDSCAPE_MIN);
    }

    @Override
    public Landscape createLandscape(LandscapeType landscapeType) {
        return new LandscapeImpl(landscapeType);
    }

    @Override
    public Plant createPlant() {
        return new PlantImpl();
    }

    @Override
    public Cell createCell() {
        return new CellImpl();
    }

    private int tryGenerateWomans(Map mapImpl, int menCount) {
        for (Cell[] cellRow : mapImpl.getCells()) {
            for (Cell cell : cellRow) {
                LandscapeType landscape = cell.getLandscape().getType();
                Optional<Human> human = cell.getHuman();
                if (!landscape.isRockBlock() && !landscape.isWatterBlock() && !human.isPresent()) {
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
        HumanImpl human = new HumanImpl();
        human.setType(HumanType.HUMAN_TYPE_WOMAN);
        initHuman(human);
        cell.setHuman(human);
    }

    private void initHuman(Human human) {
        human.setAge(30 * 300);
        human.setEnergy(63);
        human.setSatiety(63);
    }

    private int tryGenerateMens(Map mapImpl, int menCount) {
        for (Cell[] cellRow : mapImpl.getCells()) {
            for (Cell cell : cellRow) {
                LandscapeType landscape = cell.getLandscape().getType();
                Optional<Human> human = cell.getHuman();
                if (!landscape.isRockBlock() && !landscape.isWatterBlock() && !human.isPresent()) {
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
        HumanImpl human = new HumanImpl();
        human.setType(HumanType.HUMAN_TYPE_MAN);
        initHuman(human);
        cell.setHuman(human);
    }

    private void cleanHumans(Map map) {
        for (Cell[] cellRow : map.getCells()) {
            for (Cell cell : cellRow) {
                cell.setHuman(null);
            }
        }
    }


    private void cleanTrees(Map mapImpl) {
        for (Cell[] cellRow : mapImpl.getCells()) {
            for (Cell cell : cellRow) {
                cell.setPlant(null);
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
        PlantType plantType = PlantType.getByValue(XRandom.generateInteger(1, 3));
        cell.setPlant(new PlantImpl(plantType, 30, false));
    }

    private void diamondStep(Map map, float landscapeShift, int bigStep, int smallStep) {
        for (int x = smallStep; x < map.getSize(); x += bigStep) {
            for (int y = smallStep; y < map.getSize(); y += bigStep) {
                long topLeftValue = map.getCellAt(x - smallStep, y - smallStep).getLandscape().getHeight();
                long topRightValue = map.getCellAt(x + smallStep, y - smallStep).getLandscape().getHeight();
                long bottomLeftValue = map.getCellAt(x - smallStep, y + smallStep).getLandscape().getHeight();
                long bottomRightValue = map.getCellAt(x + smallStep, y + smallStep).getLandscape().getHeight();
                float average = (topLeftValue + topRightValue + bottomLeftValue + bottomRightValue) / 4;
                int centralValue = (int) (average + random.nextInt(3) * landscapeShift
                        - landscapeShift); // -landscapeShift 0 landscapeShift
                int landscapeType = getValueInRange(centralValue);
                map.setCell(createCell(LandscapeType.getByValue(landscapeType), x, y));
            }
        }
    }

    private void squareStep(Map map, float landscapeShift, int bigStep, int smallStep) {
        for (int x = 0; x < map.getSize(); x += smallStep) {
            for (int y = (x + smallStep) % bigStep; y < map.getSize(); y += bigStep) {
                long topValue = map.getCellAt((y - smallStep + map.getSize() - 1) % (map.getSize() - 1), x).getLandscape()
                        .getHeight();
                long leftValue = map.getCellAt(y, (x - smallStep + map.getSize() - 1) % (map.getSize() - 1)).getLandscape()
                        .getHeight();
                long rightValue = map.getCellAt(y, (x + smallStep) % (map.getSize() - 1)).getLandscape().getHeight();
                long bottomValue = map.getCellAt((y + smallStep) % (map.getSize() - 1), x).getLandscape().getHeight();
                float avg = (topValue + leftValue + rightValue + bottomValue) / 4;
                int centerValue = getValueInRange((int) (avg + this.random.nextInt(3) * landscapeShift - landscapeShift)
                );
                map.setCell(createCell(LandscapeType.getByValue(centerValue), x, y));
            }
        }
    }

    private int getValueInRange(int value) {
        return Math.max(Math.min(value, LandscapeType.LANDSCAPE_MAX_VALUE), 1);
    }
}
