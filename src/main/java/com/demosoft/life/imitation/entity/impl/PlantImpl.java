package com.demosoft.life.imitation.entity.impl;

import com.demosoft.life.imitation.entity.Plant;
import com.demosoft.life.imitation.entity.type.PlantType;
import com.demosoft.life.logic.math.XMath;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public class PlantImpl implements Plant {

    private CellImpl relatedCell;

    public PlantImpl(CellImpl relatedCell) {
        this.relatedCell = relatedCell;
    }

    @Override
    public PlantType getType() {
        return PlantType.decodeAndGetByValue(relatedCell.getValue());
    }

    @Override
    public void setType(PlantType plantType) {
        relatedCell.setValue(UcfCoder.encodePlantType(relatedCell.getValue(), XMath.getValueInRange(plantType.getValue(), 0, 1)));
    }

    @Override
    public int getFruits() {
        return UcfCoder.decodePlantFruits(relatedCell.getValue());
    }

    @Override
    public void setFruits(int fruits) {
        relatedCell.setValue(UcfCoder.encodePlantFruits(relatedCell.getValue(), XMath.getValueInRange(fruits, 0, 63)));
    }
}
