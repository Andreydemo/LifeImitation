package com.demosoft.life.imitation.entity.graphic.impl;

import com.demosoft.life.imitation.entity.Landscape;
import com.demosoft.life.imitation.entity.graphic.GraphicLandscape;
import com.demosoft.life.imitation.entity.type.LandscapeType;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public class GraphicLandscapeImpl implements GraphicLandscape {

    private Landscape landscape;

    public GraphicLandscapeImpl(Landscape landscape) {

        this.landscape = landscape;
    }

    @Override
    public Landscape getLandscape() {
        return landscape;
    }

    @Override
    public int getColor() {
        return landscape.getType().getColor();
    }

    @Override
    public void setColor(int color) {
        throw new IllegalArgumentException();
    }

    @Override
    public String getMessage() {
        return landscape.getType().getMessage();
    }

    @Override
    public void setMessage(String message) {
        throw new IllegalArgumentException();
    }

}
