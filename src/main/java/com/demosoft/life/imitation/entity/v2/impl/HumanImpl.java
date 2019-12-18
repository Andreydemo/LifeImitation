package com.demosoft.life.imitation.entity.v2.impl;

import static com.demosoft.life.imitation.entity.type.HumanType.HUMAN_TYPE_EMPTY;

import com.demosoft.life.imitation.entity.Cell;
import com.demosoft.life.imitation.entity.Human;
import com.demosoft.life.imitation.entity.type.HumanType;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
@Data
@ToString(exclude = "cell")
public class HumanImpl implements Human {

    HumanType type = HUMAN_TYPE_EMPTY;
    int age;
    int energy;
    int satiety;
    int pregnancy;
    Cell cell;

    public HumanImpl(Cell cell) {
        this.cell = cell;
    }

}
