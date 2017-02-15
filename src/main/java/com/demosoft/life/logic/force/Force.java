package com.demosoft.life.logic.force;

import com.demosoft.life.imitation.entity.Map;
import com.demosoft.life.imitation.entity.UcfCoder;
import com.demosoft.life.imitation.entity.type.Human;
import com.demosoft.life.imitation.entity.type.Landscape;
import com.demosoft.life.imitation.entity.type.Plant;
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
                for (int y = 0; y < map.MAP_SIZE; y++) {
                    for (int x = 0; x < map.MAP_SIZE; x++) {
                        map.setActiveFlagHumanAt(1, y, x);
                        map.setActiveFlagPlantAt(1, y, x);
                    }
                }
                for (int y = 0; y < map.MAP_SIZE; y++) {
                    for (int x = 0; x < map.MAP_SIZE; x++) {
                        act(map.getRawDataAt(x, y), y, x);
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
        if (UcfCoder.decodeActiveFlagHuman(cellData) == 1 && UcfCoder.decodeHumanType(cellData) != Human.HUMAN_TYPE_EMPTY.getValue()) {
            if (tryToDie(cellData, y, x)
                    || tryToGiveBirth(cellData, y, x)
                    || tryToSleep(cellData, y, x)
                    || tryToEat(cellData, y, x)
                    || tryToMakeChild(cellData, y, x)
                    || tryToMove(cellData, y, x, 0, 0)) {/*Do nothing*/}
        }
        if (UcfCoder.decodeActiveFlagPlant(cellData) == 1 && UcfCoder.decodePlantType(cellData) != Plant.PLANT_TYPE_EMPTY.getValue()) {
            tryToMakeFruits(cellData, y, x);
            tryToDropFruit(cellData, y, x);
        }
    }

    // HUMAN - DIE
    private boolean tryToDie(long cellData, int y, int x) {
        int energy = UcfCoder.decodeHumanEnergy(cellData);
        if (energy == 0) {
            clearHuman(y, x);
            Statistic.peopleDied++;
            Statistic.peopleDiedByEnergy++;
            infoPanelContainer.getEventsInfoPanel().update(date, "Human died by [Low Energy].");
            return true;
        }
        int satiety = UcfCoder.decodeHumanSatiety(cellData);
        if (satiety == 0) {
            clearHuman(y, x);
            Statistic.peopleDied++;
            Statistic.peopleDiedBySatiety++;
            infoPanelContainer.getEventsInfoPanel().update(date, "Human died [Low Satiety].");
            return true;
        }
        // f(x): f(0..9000) <= 0, f(24000..32767) >= 100;
        // f(x) = 2*(x/300) - 60;
        // Every year + 2% (since 31 years)
        boolean decision = XRandom.generateBoolean(2 * (UcfCoder.decodeHumanAge(cellData) / 350) - 60);
        if (decision) {
            clearHuman(y, x);
            Statistic.peopleDied++;
            Statistic.peopleDiedByAge++;
            infoPanelContainer.getEventsInfoPanel().update(date, "Human died [Age].");
            return true;
        }
        return false;
    }

    // HUMAN - EAT
    private boolean tryToEat(long cellData, int y, int x) {
        // f(x): f(0..10) >= 100, f(60..64) <= 0;
        // f(x) = 120 - 2*x;
        boolean decision = XRandom.generateBoolean(120 - 2 * UcfCoder.decodeHumanSatiety(cellData));
        if (decision) {
            for (int yShift = -1; yShift < 2; yShift++) {
                for (int xShift = -1; xShift < 2; xShift++) {
                    int yTarget = y + yShift;
                    int xTarget = x + xShift;
                    if (isCellInMapRange(yTarget, xTarget)) {
                        int fruitsTarget = map.getPlantFruitsAt(yTarget, xTarget);
                        if (fruitsTarget != 0) {
                            map.setPlantFruitsAt(--fruitsTarget, yTarget, xTarget);
                            if (map.getHumanAgeAt(y, x) < 10) {
                                map.setHumanSatietyAt(map.getHumanSatietyAt(y, x) + 32, y, x);
                            } else {
                                map.setHumanSatietyAt(map.getHumanSatietyAt(y, x) + 16, y, x);
                            }
                            map.setHumanEnergyAt(map.getHumanEnergyAt(y, x) - 1, y, x);
                            map.setHumanAgeAt(map.getHumanAgeAt(y, x) + 1, y, x);
                            map.setActiveFlagHumanAt(0, y, x);
                            return true;
                        }
                    }
                }
            }
            int minTarget = Map.MAP_SIZE * Map.MAP_SIZE + Map.MAP_SIZE * Map.MAP_SIZE;
            int yTarget = y;
            int xTarget = x;
            for (int yTemp = 0; yTemp < Map.MAP_SIZE; yTemp++) {
                for (int xTemp = 0; xTemp < Map.MAP_SIZE; xTemp++) {
                    if (map.getPlantFruitsAt(yTemp, xTemp) != 0) {
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
                return tryToMove(cellData, y, x, yShift, xShift);
            }
        }
        return false;
    }

    // HUMAN - SLEEP
    private boolean tryToSleep(long cellData, int y, int x) {
        // f(x): f(0..10) >= 100, f(60..64) <= 0;
        // f(x) = 120 - 2*x;
        boolean decision = XRandom.generateBoolean(120 - 2 * UcfCoder.decodeHumanEnergy(cellData));
        if (decision) {
            map.setHumanEnergyAt(63, y, x);
            map.setHumanSatietyAt(map.getHumanSatietyAt(y, x) - 1, y, x);
            map.setHumanAgeAt(map.getHumanAgeAt(y, x) + 1, y, x);
            map.setActiveFlagHumanAt(0, y, x);
            return true;
        }
        return false;
    }

    // HUMAN - MAKE CHILD
    private boolean tryToMakeChild(long cellData, int y, int x) {
        if (map.getHumanTypeAt(y, x) == Human.HUMAN_TYPE_WOMAN.getValue() && map.getHumanPregnancyAt(y, x) == 0) {
            for (int yShift = -1; yShift < 2; yShift++) {
                for (int xShift = -1; xShift < 2; xShift++) {
                    int yTarget = y + yShift;
                    int xTarget = x + xShift;
                    if (isCellInMapRange(yTarget, xTarget)
                            && map.getHumanTypeAt(yTarget, xTarget) == Human.HUMAN_TYPE_MAN.getValue()
                            && map.getActiveFlagHumanAt(yTarget, xTarget) == 1) {
                        boolean decision = XRandom.generateBoolean(30);
                        if (decision) {
                            map.setHumanPregnancyAt(1, y, x);
                            map.setHumanEnergyAt(map.getHumanEnergyAt(y, x) - 4, y, x);
                            map.setHumanSatietyAt(map.getHumanSatietyAt(y, x) - 4, y, x);
                            map.setHumanAgeAt(map.getHumanAgeAt(y, x) + 1, y, x);
                            map.setActiveFlagHumanAt(0, y, x);
                            map.setHumanEnergyAt(map.getHumanEnergyAt(yTarget, xTarget) - 4, yTarget, xTarget);
                            map.setHumanSatietyAt(map.getHumanSatietyAt(yTarget, xTarget) - 4, yTarget, xTarget);
                            map.setHumanAgeAt(map.getHumanAgeAt(yTarget, xTarget) + 1, yTarget, xTarget);
                            map.setActiveFlagHumanAt(0, yTarget, xTarget);
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
    private boolean tryToGiveBirth(long cellData, int y, int x) {
        int pregnacy = map.getHumanPregnancyAt(y, x);
        if (pregnacy != 0 && pregnacy < 300) {
            map.setHumanPregnancyAt(map.getHumanPregnancyAt(y, x) + 1, y, x);
        } else if (pregnacy == 300) {
            map.setHumanPregnancyAt(0, y, x);
            map.setHumanEnergyAt(map.getHumanEnergyAt(y, x) - 4, y, x);
            map.setHumanSatietyAt(map.getHumanSatietyAt(y, x) - 4, y, x);
            map.setHumanAgeAt(map.getHumanAgeAt(y, x) + 1, y, x);
            map.setActiveFlagHumanAt(0, y, x);
            for (int yShift = -1; yShift < 2; yShift++) {
                for (int xShift = -1; xShift < 2; xShift++) {
                    int yTarget = y + yShift;
                    int xTarget = x + xShift;
                    if (isCellInMapRange(yTarget, xTarget) && map.getHumanTypeAt(yTarget, xTarget) == Human.HUMAN_TYPE_EMPTY.getValue()) {
                        map.setHumanTypeAt(XRandom.generateBoolean() ? Human.HUMAN_TYPE_MAN.getValue() : Human.HUMAN_TYPE_WOMAN.getValue(), yTarget, xTarget);
                        map.setHumanAgeAt(301, yTarget, xTarget);
                        map.setHumanEnergyAt(63, yTarget, xTarget);
                        map.setHumanSatietyAt(63, yTarget, xTarget);
                        map.setHumanPregnancyAt(0, yTarget, xTarget);
                        map.setActiveFlagHumanAt(0, yTarget, xTarget);
                        Statistic.childrenWereBorn++;
                        infoPanelContainer.getEventsInfoPanel().update(date, "Child was born.");
                        return true;
                    }
                }
            }
            map.setHumanPregnancyAt(0, y, x);
            Statistic.childrenDied++;
            infoPanelContainer.getEventsInfoPanel().update(date, "Child died.");
            return true;
        }
        return false;
    }

    // HUMAN - MOVE
    private boolean tryToMove(long cellData, int y, int x, int yShift, int xShift) {
        if (yShift == 0 && xShift == 0) {
            do {
                yShift = XRandom.generateInteger(-1, 1);
                xShift = XRandom.generateInteger(-1, 1);
            } while (!isCellInMapRange(x + xShift, y + yShift));
        }
        if (yShift != 0 || xShift != 0) {
            int yTarget = y + yShift;
            int xTarget = x + xShift;
            if (!isCellInMapRange(yTarget, xTarget)) {
                clearHuman(y, x);
                Statistic.peopleDied++;
                Statistic.peopleDiedByLost++;
                infoPanelContainer.getEventsInfoPanel().update(date, "Human died [Lost].");
                return true;
            }
            if (map.getHumanTypeAt(yTarget, xTarget) == 0) {
                moveHuman(y, x, yTarget, xTarget);
                int landscapeTarget = map.getLandscapeTypeAt(yTarget, xTarget);
                if (landscapeTarget == Landscape.LANDSCAPE_TYPE_WATER_LOW.getValue() || landscapeTarget == Landscape.LANDSCAPE_TYPE_WATER_HIGH.getValue()) {
                    map.setHumanEnergyAt(map.getHumanEnergyAt(yTarget, xTarget) - 3, yTarget, xTarget);
                    map.setHumanSatietyAt(map.getHumanSatietyAt(yTarget, xTarget) - 3, yTarget, xTarget);
                } else {
                    map.setHumanEnergyAt(map.getHumanEnergyAt(yTarget, xTarget) - 2, yTarget, xTarget);
                    map.setHumanSatietyAt(map.getHumanSatietyAt(yTarget, xTarget) - 2, yTarget, xTarget);
                }
                map.setHumanAgeAt(map.getHumanAgeAt(yTarget, xTarget) + 1, yTarget, xTarget);
                map.setActiveFlagHumanAt(0, yTarget, xTarget);
                return true;
            }
        }
        map.setHumanEnergyAt(map.getHumanEnergyAt(y, x) - 1, y, x);
        map.setHumanSatietyAt(map.getHumanSatietyAt(y, x) - 1, y, x);
        map.setHumanAgeAt(map.getHumanAgeAt(y, x) + 1, y, x);
        map.setActiveFlagHumanAt(0, y, x);
        return false;
    }

    // PLANT - MAKE FRUITS
    private void tryToMakeFruits(long cellData, int y, int x) {
        if (date % 30 == 0) {
            map.setPlantFruitsAt(map.getPlantFruitsAt(y, x) + XRandom.generateInteger(5, 15), y, x);
        }
    }

    // PLANT - DROP FRUIT
    private void tryToDropFruit(long cellData, int y, int x) {
        if (map.getPlantFruitsAt(y, x) == 0) {
            return;
        }
        // 0.01 * 0.50 = 0.005
        boolean decision = XRandom.generateBoolean(1) && XRandom.generateBoolean(50);
        if (decision) {
            map.setPlantFruitsAt(map.getPlantFruitsAt(y, x) - 1, y, x);
            int yTarget = y + XRandom.generateInteger(-2, 2);
            int xTarget = x + XRandom.generateInteger(-2, 2);
            if (isCellInMapRange(yTarget, xTarget) && (yTarget != y || xTarget != x)) {
                int landscapeTarget = map.getLandscapeTypeAt(yTarget, xTarget);
                int humanTarget = map.getHumanTypeAt(yTarget, xTarget);
                int plantTarget = map.getPlantTypeAt(yTarget, xTarget);
                if (landscapeTarget != Landscape.LANDSCAPE_TYPE_WATER_LOW.getValue()
                        && landscapeTarget != Landscape.LANDSCAPE_TYPE_WATER_HIGH.getValue()
                        && humanTarget == Human.HUMAN_TYPE_EMPTY.getValue()
                        && plantTarget == Plant.PLANT_TYPE_EMPTY.getValue()) {
                    map.setPlantTypeAt(Plant.PLANT_TYPE_APPLE.getValue(), yTarget, xTarget);
                }
            }
        }
    }

    private void moveHuman(int yFrom, int xFrom, int yTo, int xTo) {
        map.setHumanTypeAt(map.getHumanTypeAt(yFrom, xFrom), yTo, xTo);
        map.setHumanAgeAt(map.getHumanAgeAt(yFrom, xFrom), yTo, xTo);
        map.setHumanEnergyAt(map.getHumanEnergyAt(yFrom, xFrom), yTo, xTo);
        map.setHumanSatietyAt(map.getHumanSatietyAt(yFrom, xFrom), yTo, xTo);
        map.setHumanPregnancyAt(map.getHumanPregnancyAt(yFrom, xFrom), yTo, xTo);
        clearHuman(yFrom, xFrom);
    }

    private void clearHuman(int y, int x) {
        map.setHumanTypeAt(0, y, x);
        map.setHumanAgeAt(0, y, x);
        map.setHumanEnergyAt(0, y, x);
        map.setHumanSatietyAt(0, y, x);
        map.setHumanPregnancyAt(0, y, x);
    }

    private boolean isCellInMapRange(int y, int x) {
        return y >= 0 && y < Map.MAP_SIZE && x >= 0 && x < Map.MAP_SIZE;
    }

}