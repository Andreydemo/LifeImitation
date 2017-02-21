package com.demosoft.life.logic.force;

import com.demosoft.life.imitation.entity.*;
import com.demosoft.life.imitation.entity.impl.CellImpl;
import com.demosoft.life.imitation.entity.impl.MapFactoryImpl;
import com.demosoft.life.imitation.entity.type.HumanType;
import com.demosoft.life.imitation.entity.type.LandscapeType;
import com.demosoft.life.imitation.entity.type.PlantType;
import com.demosoft.life.logic.random.XRandom;
import com.demosoft.life.logic.statistic.Statistic;
import com.demosoft.life.scene.main.info.InfoPanelContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * Created by Andrii_Korkoshko on 2/10/2017.
 */
@Component
public class Force {

    private static int date = 1;
    private static Timer timer;
    private static int timerDelay = 50;

    @Autowired
    private Map map;

    @Autowired
    private Statistic statistic;
    @Autowired
    private InfoPanelContainer infoPanelContainer;

    public void start() {
        if (timer == null) {
            timer = new Timer(timerDelay, e -> {
                for (int y = 0; y < MapFactoryImpl.mapSize; y++) {
                    for (int x = 0; x < MapFactoryImpl.mapSize; x++) {
                        map.getCellAt(y, x).setActiveFlagHuman(true);
                        map.getCellAt(y, x).setActiveFlagPlant(true);
                    }
                }
                for (int y = 0; y < MapFactoryImpl.mapSize; y++) {
                    for (int x = 0; x < MapFactoryImpl.mapSize; x++) {
                        act(((CellImpl) map.getCellAt(x, y)).getValue(), y, x);
                    }
                }
                statistic.update();
                infoPanelContainer.getMapInfoPanel().update(++date);
            });
        }
        timer.start();
    }

    public void pause() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
        date = 1;
    }

    // ACT
    private void act(long cellData, int y, int x) {
        Cell cell = map.getCellAt(x, y);
        if (cell.getActiveFlagHuman() && cell.getHuman().getType() != HumanType.HUMAN_TYPE_EMPTY) {
            if (tryToDie(cell)
                    || tryToGiveBirth(cell)
                    || tryToSleep(cell)
                    || tryToEat(cell)
                    || tryToMakeChild(cell)
                    || tryToMove(cell, 0, 0)) {/*Do nothing*/}
        }
        if (cell.getActiveFlagPlant() && cell.getPlant().getType() != PlantType.PLANT_TYPE_EMPTY) {
            tryToMakeFruits(y, x);
            tryToDropFruit(y, x);
        }
    }

    // HUMAN - DIE
    private boolean tryToDie(Cell cell) {
        Human human = cell.getHuman();
        int energy = human.getEnergy();
        if (energy == 0) {
            clearHuman(human);
            Statistic.peopleDied++;
            Statistic.peopleDiedByEnergy++;
            infoPanelContainer.getEventsInfoPanel().update(date, "GraphicHuman died by [Low Energy].");
            return true;
        }
        int satiety = human.getSatiety();
        if (satiety == 0) {
            clearHuman(human);
            Statistic.peopleDied++;
            Statistic.peopleDiedBySatiety++;
            infoPanelContainer.getEventsInfoPanel().update(date, "GraphicHuman died [Low Satiety].");
            return true;
        }
        // f(x): f(0..9000) <= 0, f(24000..32767) >= 100;
        // f(x) = 2*(x/300) - 60;
        // Every year + 2% (since 31 years)
        boolean decision = XRandom.generateBoolean(2 * (human.getAge() / 350) - 60);
        if (decision) {
            clearHuman(human);
            Statistic.peopleDied++;
            Statistic.peopleDiedByAge++;
            infoPanelContainer.getEventsInfoPanel().update(date, "GraphicHuman died [Age].");
            return true;
        }
        return false;
    }

    // HUMAN - EAT
    private boolean tryToEat(Cell cell) {
        // f(x): f(0..10) >= 100, f(60..64) <= 0;
        // f(x) = 120 - 2*x;
        boolean decision = XRandom.generateBoolean(120 - 2 * cell.getHuman().getSatiety());
        if (decision) {
            int y = cell.getY();
            int x = cell.getX();
            for (int yShift = -1; yShift < 2; yShift++) {
                for (int xShift = -1; xShift < 2; xShift++) {
                    int yTarget = y + yShift;
                    int xTarget = x + xShift;
                    if (isCellInMapRange(yTarget, xTarget)) {
                        Plant plant = map.getCellAt(xTarget, yTarget).getPlant();
                        Human human = cell.getHuman();
                        int fruitsTarget = plant.getFruits();
                        if (fruitsTarget != 0) {
                            plant.setFruits(--fruitsTarget);
                            if (human.getAge() < 10) {
                                human.setSatiety(human.getSatiety() + 32);
                            } else {
                                human.setSatiety(human.getSatiety() + 16);
                            }
                            human.setEnergy(human.getEnergy() - 1);
                            human.setAge(human.getAge() + 1);
                            cell.setActiveFlagHuman(false);
                            return true;
                        }
                    }
                }
            }
            int minTarget = MapFactoryImpl.mapSize * MapFactoryImpl.mapSize + MapFactoryImpl.mapSize * MapFactoryImpl.mapSize;
            int yTarget = y;
            int xTarget = x;
            for (int yTemp = 0; yTemp < MapFactoryImpl.mapSize; yTemp++) {
                for (int xTemp = 0; xTemp < MapFactoryImpl.mapSize; xTemp++) {
                    if (map.getCellAt(xTemp, yTemp).getPlant().getFruits() != 0) {
                        int yDelta = Math.abs(yTemp - y);
                        int xDelta = Math.abs(xTemp - x);
                        int minTemp = yDelta * yDelta + xDelta * xDelta;
                        if (minTemp < minTarget) {
                            minTarget = minTemp;
                            yTarget = yTemp;
                            xTarget = xTemp;
                        }
                    }
                }
            }
            if (yTarget != y || xTarget != x) {
                int yShift = 0;
                int xShift = 0;
                int yDelta = yTarget - y;
                int xDelta = xTarget - x;
                if (yDelta < 0) {
                    yShift = -1;
                } else if (yDelta > 0) {
                    yShift = 1;
                }
                if (xDelta < 0) {
                    xShift = -1;
                } else if (xDelta > 0) {
                    xShift = 1;
                }
                return tryToMove(cell, yShift, xShift);
            }
        }
        return false;
    }

    // HUMAN - SLEEP
    private boolean tryToSleep(Cell cell) {
        // f(x): f(0..10) >= 100, f(60..64) <= 0;
        // f(x) = 120 - 2*x;
        boolean decision = XRandom.generateBoolean(120 - 2 * cell.getHuman().getEnergy());
        if (decision) {
            Human human = cell.getHuman();
            human.setEnergy(63);
            human.setSatiety(human.getSatiety() - 1);
            human.setAge(human.getAge() + 1);
            cell.setActiveFlagHuman(false);
            return true;
        }
        return false;
    }

    // HUMAN - MAKE CHILD
    private boolean tryToMakeChild(Cell cell) {
        Human human = cell.getHuman();
        if (human.getType() == HumanType.HUMAN_TYPE_WOMAN && human.getPregnancy() == 0) {
            for (int yShift = -1; yShift < 2; yShift++) {
                for (int xShift = -1; xShift < 2; xShift++) {
                    int yTarget = cell.getY() + yShift;
                    int xTarget = cell.getX() + xShift;
                    Cell targetCell = map.getCellAt(xTarget, yTarget);
                    Human targetHuman = targetCell.getHuman();
                    if (isCellInMapRange(yTarget, xTarget)
                            && targetHuman.getType() == HumanType.HUMAN_TYPE_MAN
                            && targetCell.getActiveFlagHuman()) {
                        boolean decision = XRandom.generateBoolean(30);
                        if (decision) {
                            human.setPregnancy(1);
                            human.setEnergy(human.getEnergy() - 4);
                            human.setSatiety(human.getSatiety() - 4);
                            human.setAge(human.getAge() + 1);
                            cell.setActiveFlagHuman(false);
                            targetHuman.setEnergy(targetHuman.getEnergy() - 4);
                            targetHuman.setSatiety(targetHuman.getSatiety() - 4);
                            targetHuman.setAge(targetHuman.getAge() + 1);
                            targetCell.setActiveFlagHuman(false);
                            infoPanelContainer.getEventsInfoPanel().update(date, "Woman got pregnant.");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // HUMAN - GIVE BIRTH
    private boolean tryToGiveBirth(Cell cell) {
        Human human = cell.getHuman();
        int pregnancy = human.getPregnancy();
        if (pregnancy != 0 && pregnancy < 300) {
            human.setPregnancy(human.getPregnancy() + 1);
        } else if (pregnancy == 300) {
            human.setPregnancy(0);
            human.setEnergy(human.getEnergy() - 4);
            human.setSatiety(human.getSatiety() - 4);
            human.setAge(human.getAge() + 1);
            cell.setActiveFlagHuman(false);
            for (int yShift = -1; yShift < 2; yShift++) {
                for (int xShift = -1; xShift < 2; xShift++) {
                    int yTarget = cell.getY() + yShift;
                    int xTarget = cell.getX() + xShift;
                    Cell targetCell = map.getCellAt(xTarget, yTarget);
                    Human targetHuman = targetCell.getHuman();
                    if (isCellInMapRange(yTarget, xTarget) && targetHuman.getType() == HumanType.HUMAN_TYPE_EMPTY) {
                        targetHuman.setType(XRandom.generateBoolean() ? HumanType.HUMAN_TYPE_MAN : HumanType.HUMAN_TYPE_WOMAN);
                        targetHuman.setAge(301);
                        targetHuman.setEnergy(63);
                        targetHuman.setSatiety(63);
                        targetHuman.setPregnancy(0);
                        targetCell.setActiveFlagHuman(false);
                        Statistic.childrenWereBorn++;
                        infoPanelContainer.getEventsInfoPanel().update(date, "Child was born.");
                        return true;
                    }
                }
            }
            human.setPregnancy(0);
            Statistic.childrenDied++;
            infoPanelContainer.getEventsInfoPanel().update(date, "Child died.");
            return true;
        }
        return false;
    }

    // HUMAN - MOVE
    private boolean tryToMove(Cell cell, int yShift, int xShift) {
        int x = cell.getX();
        int y = cell.getY();
        if (yShift == 0 && xShift == 0) {
            int tryCount = 10;
            do {
                yShift = XRandom.generateInteger(-1, 1);
                xShift = XRandom.generateInteger(-1, 1);
                tryCount--;
            } while (tryCount> 0 || !isCellInMapRange(x + xShift, y + yShift) || !canMove(map.getCellAt(x + xShift, y + yShift)));
        }
        if (yShift != 0 || xShift != 0) {
            int yTarget = y + yShift;
            int xTarget = x + xShift;
            if (!isCellInMapRange(yTarget, xTarget)) {
                clearHuman(cell.getHuman());
                Statistic.peopleDied++;
                Statistic.peopleDiedByLost++;
                infoPanelContainer.getEventsInfoPanel().update(date, "GraphicHuman died [Lost].");
                return true;
            }
            Cell targetCell = map.getCellAt(xTarget, yTarget);
            Human targetHuman = targetCell.getHuman();
            Landscape targetLandscape = targetCell.getLandscape();
            if (targetHuman.getType() == HumanType.HUMAN_TYPE_EMPTY) {
                moveHuman(y, x, yTarget, xTarget);
                if (targetLandscape.getType() == LandscapeType.LANDSCAPE_TYPE_WATER_LOW || targetLandscape.getType() == LandscapeType.LANDSCAPE_TYPE_WATER_HIGH) {
                    targetHuman.setEnergy(targetHuman.getEnergy() - 3);
                    targetHuman.setSatiety(targetHuman.getSatiety() - 3);
                } else {
                    targetHuman.setEnergy(targetHuman.getEnergy() - 2);
                    targetHuman.setSatiety(targetHuman.getSatiety() - 2);
                }
                targetHuman.setAge(targetHuman.getAge() + 1);
                targetCell.setActiveFlagHuman(false);
                return true;
            }
        }
        Human human = cell.getHuman();
        human.setEnergy(human.getEnergy() - 1);
        human.setSatiety(human.getSatiety() - 1);
        human.setAge(human.getAge() + 1);
        cell.setActiveFlagHuman(false);
        return false;
    }

    // PLANT - MAKE FRUITS
    private void tryToMakeFruits(int y, int x) {
        if (date % 30 == 0) {
            Plant plant = map.getCellAt(x, y).getPlant();
            plant.setFruits(plant.getFruits() + XRandom.generateInteger(5, 15));
        }
    }

    // PLANT - DROP FRUIT
    private void tryToDropFruit(int y, int x) {
        Plant plant = map.getCellAt(x, y).getPlant();
        if (plant.getFruits() == 0) {
            return;
        }
        // 0.01 * 0.50 = 0.005
        boolean decision = XRandom.generateBoolean(1) && XRandom.generateBoolean(50);
        if (decision) {
            plant.setFruits(plant.getFruits() - 1);
            int yTarget = y + XRandom.generateInteger(-2, 2);
            int xTarget = x + XRandom.generateInteger(-2, 2);
            Cell targetCell = map.getCellAt(xTarget, yTarget);
            if (isCellInMapRange(yTarget, xTarget) && (yTarget != y || xTarget != x)) {
                LandscapeType landscapeTarget = targetCell.getLandscape().getType();
                HumanType humanTarget = targetCell.getHuman().getType();
                PlantType plantTarget = targetCell.getPlant().getType();
                if (landscapeTarget != LandscapeType.LANDSCAPE_TYPE_WATER_LOW
                        && landscapeTarget != LandscapeType.LANDSCAPE_TYPE_WATER_HIGH
                        && humanTarget == HumanType.HUMAN_TYPE_EMPTY
                        && plantTarget == PlantType.PLANT_TYPE_EMPTY  && canMove(targetCell)) {
                    targetCell.getPlant().setType(PlantType.PLANT_TYPE_APPLE);
                }
            }
        }
    }

    private void moveHuman(int yFrom, int xFrom, int yTo, int xTo) {
        Human humanFrom = map.getCellAt(xFrom, yFrom).getHuman();
        Human humanTo = map.getCellAt(xTo, yTo).getHuman();
        humanTo.setType(humanFrom.getType());
        humanTo.setAge(humanFrom.getAge());
        humanTo.setEnergy(humanFrom.getEnergy());
        humanTo.setSatiety(humanFrom.getSatiety());
        humanTo.setPregnancy(humanFrom.getPregnancy());
        clearHuman(humanFrom);
    }

    private void clearHuman(Human human) {
        human.setType(HumanType.HUMAN_TYPE_EMPTY);
        human.setAge(0);
        human.setEnergy(0);
        human.setSatiety(0);
        human.setPregnancy(0);
    }

    private boolean isCellInMapRange(int y, int x) {
        return y >= 0 && y < MapFactoryImpl.mapSize && x >= 0 && x < MapFactoryImpl.mapSize;
    }

    private boolean canMove(Cell cell) {
        return !cell.getLandscape().getType().isRockBlock() && !cell.getLandscape().getType().isWatterBlock();
    }
}