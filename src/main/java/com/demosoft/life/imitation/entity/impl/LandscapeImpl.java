package com.demosoft.life.imitation.entity.impl;

import com.demosoft.life.imitation.entity.Landscape;
import com.demosoft.life.imitation.entity.type.LandscapeType;
import com.demosoft.life.logic.math.XMath;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public class LandscapeImpl implements Landscape {
    private CellImpl relatedCell;

    public LandscapeImpl(CellImpl relatedCell) {
        this.relatedCell = relatedCell;
    }

    @Override
    public LandscapeType getType() {
        return LandscapeType.decodeAndGetByValue(relatedCell.getValue());
    }

    @Override
    public void setType(LandscapeType landscapeType) {
        relatedCell.setValue(UcfCoder.encodeLandscapeType(relatedCell.getValue(), XMath.getValueInRange(landscapeType.getValue(), 0, 7)));
    }

    @Override
    public int getHeight() {
        return getType().getValue();
    }

    @Override
    public void setHeight(int height) {
        setType(LandscapeType.decodeAndGetByValue(height));
    }
}
