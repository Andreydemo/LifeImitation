package com.demosoft.life.imitation.entity.v2.impl;

import static com.demosoft.life.imitation.entity.type.PlantType.PLANT_TYPE_EMPTY;

import com.demosoft.life.imitation.entity.Plant;
import com.demosoft.life.imitation.entity.type.PlantType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlantImpl implements Plant {

    PlantType type = PLANT_TYPE_EMPTY;
    int fruits;
    boolean active;
}
