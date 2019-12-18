package com.demosoft.life.imitation.entity.v2.impl;

import com.demosoft.life.imitation.entity.Cell;
import com.demosoft.life.imitation.entity.Human;
import com.demosoft.life.imitation.entity.Landscape;
import com.demosoft.life.imitation.entity.Plant;
import com.demosoft.life.imitation.entity.impl.UcfCoder;
import com.demosoft.life.logic.math.XMath;
import lombok.Data;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
@Data
public class CellImpl implements Cell {

    private int x;
    private int y;
    private Human human = new HumanImpl(this);
    private Plant plant = new PlantImpl(this);
    private Landscape landscape = new LandscapeImpl(this);
    private Boolean activeFlagHuman;
    private Boolean activeFlagPlant;

    public CellImpl() {
    }


}
