package com.demosoft.life.imitation.entity.v2.impl;

import com.demosoft.life.imitation.entity.Landscape;
import com.demosoft.life.imitation.entity.impl.UcfCoder;
import com.demosoft.life.imitation.entity.type.LandscapeType;
import com.demosoft.life.logic.math.XMath;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
@Data
@ToString(exclude = "cell")
public class LandscapeImpl implements Landscape {

    private CellImpl cell;
    LandscapeType type = LandscapeType.LANDSCAPE_MIN;

    public LandscapeImpl(CellImpl cell) {
        this.cell = cell;
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
