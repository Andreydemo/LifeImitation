package com.demosoft.life.imitation.entity;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public interface Cell {

    /*  long getValue();

      void setValue(long Value);
  */
    int getY();

    void setY(int y);

    int getX();

    void setX(int x);

    void setHuman(Human human);

    Human getHuman();

    void setPlant(Plant plant);

    Plant getPlant();

    void setLandscape(Landscape plant);

    Landscape getLandscape();

    void setActiveFlagHuman(Boolean activeFlagHuman);

    Boolean getActiveFlagHuman();

    void setActiveFlagPlant(Boolean activeFlagPlant);

    Boolean getActiveFlagPlant();

}
