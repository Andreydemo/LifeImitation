package com.demosoft.life.imitation.entity;

import com.demosoft.life.imitation.entity.type.PlantType;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public interface Plant extends Activatable{

    PlantType getType();

    void setType(PlantType plantType);

    int getFruits();

    void setFruits(int fruits);
}
