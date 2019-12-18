package com.demosoft.life.imitation.entity.v2.impl;

import static com.demosoft.life.imitation.entity.type.PlantType.PLANT_TYPE_EMPTY;

import com.demosoft.life.imitation.entity.Plant;
import com.demosoft.life.imitation.entity.type.PlantType;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
@Data
@ToString(exclude = "cell")
public class PlantImpl implements Plant {

    private CellImpl cell;

    PlantType type = PLANT_TYPE_EMPTY;
    int fruits;

    public PlantImpl(CellImpl cell) {
        this.cell = cell;
    }

}
