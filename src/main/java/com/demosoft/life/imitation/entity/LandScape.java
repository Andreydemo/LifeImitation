package com.demosoft.life.imitation.entity;

import com.demosoft.life.imitation.entity.type.LandscapeType;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public interface Landscape {

    LandscapeType getType();

    void setType(LandscapeType landscapeType);
}
