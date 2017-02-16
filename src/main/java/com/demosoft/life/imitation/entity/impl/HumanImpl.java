package com.demosoft.life.imitation.entity.impl;

import com.demosoft.life.imitation.entity.Human;
import com.demosoft.life.imitation.entity.type.HumanType;
import com.demosoft.life.logic.math.XMath;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public class HumanImpl implements Human {

    private CellImpl relatedCell;

    public HumanImpl(CellImpl relatedCell) {
        this.relatedCell = relatedCell;
    }


    @Override
    public void setType(HumanType humanType) {
        relatedCell.setValue(UcfCoder.encodeHumanType(relatedCell.getValue(), XMath.getValueInRange(humanType.getValue(), HumanType.HUMAN_TYPE_MIN, HumanType.HUMAN_TYPE_MAX)));
    }

    @Override
    public HumanType getType() {
        return HumanType.decodeAndGetByValue(relatedCell.getValue());
    }

    @Override
    public void setAge(int age) {
        relatedCell.setValue(UcfCoder.encodeHumanAge(relatedCell.getValue(), XMath.getValueInRange(age, 0, 32767)));
    }

    @Override
    public int getAge() {
        return UcfCoder.decodeHumanAge(relatedCell.getValue());
    }

    @Override
    public void setEnergy(int energy) {
         relatedCell.setValue(UcfCoder.encodeHumanEnergy(relatedCell.getValue(), XMath.getValueInRange(energy, 0, 63)));
    }

    @Override
    public int getEnergy() {
        return UcfCoder.decodeHumanEnergy(relatedCell.getValue());
    }

    @Override
    public void setSatiety(int satiety) {
         relatedCell.setValue(UcfCoder.encodeHumanSatiety(relatedCell.getValue(), XMath.getValueInRange(satiety, 0, 63)));
    }

    @Override
    public int getSatiety() {
        return UcfCoder.decodeHumanSatiety(relatedCell.getValue());
    }

    @Override
    public void setPregnancy(int pregnancy) {
         relatedCell.setValue(UcfCoder.encodeHumanPregnancy(relatedCell.getValue(), XMath.getValueInRange(pregnancy, 0, 511)));
    }

    @Override
    public int getPregnancy() {
        return UcfCoder.decodeHumanPregnancy(relatedCell.getValue());
    }
}
