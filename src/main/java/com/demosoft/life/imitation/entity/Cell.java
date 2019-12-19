package com.demosoft.life.imitation.entity;

import java.util.Optional;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public interface Cell {

    int getY();

    void setY(int y);

    int getX();

    void setX(int x);

    void setHuman(Human human);

    Optional<Human> getHuman();

    void setPlant(Plant plant);

    Optional<Plant> getPlant();

    void setLandscape(Landscape plant);

    Landscape getLandscape();

}
