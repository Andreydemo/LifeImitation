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

    public final static int MAP_SIZE = 65;
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
    public void generateLandscape(Map map) {
        System.out.println("MapFactoryImpl map start generateLandscape");
        MapImpl mapImpl = (MapImpl) map;

        mapImpl.setCell(createCell(LandscapeType.LANDSCAPE_MAX_VALUE, 0, 0));
        mapImpl.setCell(createCell(LandscapeType.LANDSCAPE_MAX_VALUE, 0, mapImpl.getSize() - 1));
        mapImpl.setCell(createCell(LandscapeType.LANDSCAPE_MAX_VALUE, mapImpl.getSize() - 1, 0));
        mapImpl.setCell(createCell(LandscapeType.LANDSCAPE_MAX_VALUE, mapImpl.getSize() - 1, mapImpl.getSize() - 1));
        float landscapeShift = 20;
        for (int bigStep = mapImpl.getSize() - 1; bigStep >= 2; bigStep /= 2, landscapeShift /= 2.0f) {
            int smallStep = bigStep / 2;
            //diamond step
            diamondStep(mapImpl, landscapeShift, bigStep, smallStep);
            //square step
            squareStep(mapImpl, landscapeShift, bigStep, smallStep);
        }
        System.out.println("MapFactoryImpl map end generateLandscape");
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

    private int tryGenerateWomans(MapImpl mapImpl, int menCount) {
        for (Cell[] cellRow : mapImpl.getCells()) {
            for (Cell cell : cellRow) {
                CellImpl cellImpl = (CellImpl) cell;
                LandscapeType landscape = LandscapeType.decodeAndGetByValue(cellImpl.getValue());
                HumanType human = HumanType.decodeAndGetByValue(cellImpl.getValue());
                if (!landscape.isRockBlock() && !landscape.isWatterBlock() && human == HumanType.HUMAN_TYPE_EMPTY) {
                    if (XRandom.generateBoolean(1) && menCount > 0) {
                        menCount--;
                        placeWoman(cellImpl);
                    }
                }
            }
        }
        return menCount;
    }

    private void placeWoman(CellImpl cell) {
        cell.setValue(UcfCoder.encodeHumanType(cell.getValue(), HumanType.HUMAN_TYPE_WOMAN.getValue()));
        initHuman(cell);
    }

    private void initHuman(CellImpl cell) {
        cell.setValue(UcfCoder.encodeHumanAge(cell.getValue(), 30));
        cell.setValue(UcfCoder.encodeHumanEnergy(cell.getValue(), 63));
        cell.setValue(UcfCoder.encodeHumanSatiety(cell.getValue(), 63));
    }

    private int tryGenerateMens(MapImpl mapImpl, int menCount) {
        for (Cell[] cellRow : mapImpl.getCells()) {
            for (Cell cell : cellRow) {
                CellImpl cellImpl = (CellImpl) cell;
                LandscapeType landscape = LandscapeType.decodeAndGetByValue(cellImpl.getValue());
                HumanType human = HumanType.decodeAndGetByValue(cellImpl.getValue());
                if (!landscape.isRockBlock() && !landscape.isWatterBlock() && human == HumanType.HUMAN_TYPE_EMPTY) {
                    if (XRandom.generateBoolean(1) && menCount > 0) {
                        menCount--;
                        placeMan(cellImpl);
                    }
                }
            }
        }
        return menCount;
    }

    private void placeMan(CellImpl cell) {
        cell.setValue(UcfCoder.encodeHumanType(cell.getValue(), HumanType.HUMAN_TYPE_MAN.getValue()));
        initHuman(cell);
    }

    private void cleanHumans(MapImpl map) {
        for (Cell[] cellRow : map.getCells()) {
            for (Cell cell : cellRow) {
                CellImpl cellImpl = (CellImpl) cell;
                if (HumanType.decodeAndGetByValue(cellImpl.getValue()) != HumanType.HUMAN_TYPE_EMPTY) {
                    cellImpl.setValue(UcfCoder.encodeHumanType(cellImpl.getValue(), HumanType.HUMAN_TYPE_EMPTY.getValue()));
                }
            }
        }
    }


    private void cleanTrees(MapImpl mapImpl) {
        for (Cell[] cellRow : mapImpl.getCells()) {
            for (Cell cell : cellRow) {
                CellImpl cellImpl = (CellImpl) cell;
                if (PlantType.decodeAndGetByValue(cellImpl.getValue()) != PlantType.PLANT_TYPE_EMPTY) {
                    cellImpl.setValue(UcfCoder.encodePlantType(cellImpl.getValue(), PlantType.PLANT_TYPE_EMPTY.getValue()));
                }
            }
        }
    }

    private int tryGeneratePlant(MapImpl mapImpl, int treesToPlace) {
        for (Cell[] cellRow : mapImpl.getCells()) {
            for (Cell cell : cellRow) {
                CellImpl cellImpl = (CellImpl) cell;
                LandscapeType landscape = LandscapeType.decodeAndGetByValue(cellImpl.getValue());
                if (!landscape.isRockBlock() && !landscape.isWatterBlock()) {
                    if (XRandom.generateBoolean(1) && treesToPlace > 0) {
                        treesToPlace--;
                        placeTree(cellImpl);
                    }
                }
            }
        }
        return treesToPlace;
    }

    private void placeTree(CellImpl cell) {
        PlantType plant = PlantType.getByValue(XRandom.generateInteger(1, 3));
        cell.setValue(UcfCoder.encodePlantType(cell.getValue(), plant.getValue()));
        cell.setValue(UcfCoder.encodePlantFruits(cell.getValue(), 30));
    }

    private void diamondStep(MapImpl mapImpl, float landscapeShift, int bigStep, int smallStep) {
        for (int x = smallStep; x < mapImpl.getSize(); x += bigStep) {
            for (int y = smallStep; y < mapImpl.getSize(); y += bigStep) {
                long topLeftValue = ((CellImpl) mapImpl.getCellAt(x - smallStep, y - smallStep)).getValue();
                long topRightValue = ((CellImpl) mapImpl.getCellAt(x + smallStep, y - smallStep)).getValue();
                long bottomLeftValue = ((CellImpl) mapImpl.getCellAt(x - smallStep, y + smallStep)).getValue();
                long bottomRightValue = ((CellImpl) mapImpl.getCellAt(x + smallStep, y + smallStep)).getValue();
                float average = (topLeftValue + topRightValue + bottomLeftValue + bottomRightValue) / 4;
                int centralValue = (int) (average + random.nextInt(3) * landscapeShift - landscapeShift); // -landscapeShift 0 landscapeShift
                mapImpl.setCell(createCell(getValueInRange(centralValue, 1, LandscapeType.LANDSCAPE_MAX_VALUE), x, y));
            }
        }
    }

    private void squareStep(MapImpl mapImpl, float landscapeShift, int bigStep, int smallStep) {
        for (int x = 0; x < mapImpl.getSize(); x += smallStep) {
            for (int y = (x + smallStep) % bigStep; y < mapImpl.getSize(); y += bigStep) {
                long topValue = ((CellImpl) mapImpl.getCellAt((y - smallStep + mapImpl.getSize() - 1) % (mapImpl.getSize() - 1), x)).getValue();
                long leftValue = (int) ((CellImpl) mapImpl.getCellAt(y, (x - smallStep + mapImpl.getSize() - 1) % (mapImpl.getSize() - 1))).getValue();
                long rightValue = (int) ((CellImpl) mapImpl.getCellAt(y, (x + smallStep) % (mapImpl.getSize() - 1))).getValue();
                long bottomValue = (int) ((CellImpl) mapImpl.getCellAt((y + smallStep) % (mapImpl.getSize() - 1), x)).getValue();
                float avg = (topValue + leftValue + rightValue + bottomValue) / 4;
                int centerValue = getValueInRange((int) (avg + this.random.nextInt(3) * landscapeShift - landscapeShift), 1, LandscapeType.LANDSCAPE_MAX_VALUE);
                mapImpl.setCell(createCell(centerValue, y, x));
            }
        }
    }

    private int getValueInRange(int value, int min, int max) {
        return Math.max(Math.min(value, max), min);
    }
}
