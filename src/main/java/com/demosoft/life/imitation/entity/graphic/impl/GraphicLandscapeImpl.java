package com.demosoft.life.imitation.entity.graphic.impl;

import com.demosoft.life.imitation.entity.Landscape;
import com.demosoft.life.imitation.entity.graphic.GraphicLandscape;
import com.demosoft.life.imitation.entity.type.LandscapeType;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public class GraphicLandscapeImpl implements GraphicLandscape {

    private Landscape baseLandscape;

    public GraphicLandscapeImpl(Landscape baseLandscape) {

        this.baseLandscape = baseLandscape;
    }

    @Override
    public LandscapeType getType() {
        return baseLandscape.getType();
    }

    @Override
    public void setType(LandscapeType landscapeType) {
        baseLandscape.setType(landscapeType);
    }

    @Override
    public int getColor() {
        return getType().getColor();
    }

    @Override
    public void setColor(int color) {
        throw new IllegalArgumentException();
    }

    @Override
    public String getMessage() {
        return getType().getMessage();
    }

    @Override
    public void setMessage(String message) {
        throw new IllegalArgumentException();
    }
}
