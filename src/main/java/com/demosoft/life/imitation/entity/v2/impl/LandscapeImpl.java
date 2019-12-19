package com.demosoft.life.imitation.entity.v2.impl;

import com.demosoft.life.imitation.entity.Landscape;
import com.demosoft.life.imitation.entity.impl.UcfCoder;
import com.demosoft.life.imitation.entity.type.LandscapeType;
import com.demosoft.life.logic.math.XMath;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandscapeImpl implements Landscape {

    LandscapeType type = LandscapeType.LANDSCAPE_MIN;

    @Override
    public int getHeight() {
        return getType().getValue();
    }

}
