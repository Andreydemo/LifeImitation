package com.demosoft.life.imitation.entity;

import com.demosoft.life.imitation.entity.type.HumanType;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public interface Human {

    void setType(HumanType humanType);

    HumanType getType();

    void setAge(int age);

    int getAge();

    void setEnergy(int energy);

    int getEnergy();

    void setSatiety(int satiety);

    int getSatiety();

    void setPregnancy(int pregnancy);

    int getPregnancy();
}
