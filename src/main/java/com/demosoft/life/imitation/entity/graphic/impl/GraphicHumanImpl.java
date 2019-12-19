package com.demosoft.life.imitation.entity.graphic.impl;

import com.demosoft.life.imitation.entity.Human;
import com.demosoft.life.imitation.entity.graphic.GraphicHuman;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public class GraphicHumanImpl implements GraphicHuman {
    private Human human;

    public GraphicHumanImpl(Human human) {
        this.human = human;
    }


    @Override
    public Human getHuman() {
        return this.human;
    }

    @Override
    public String getMessage() {
        return  this.human.getType().getMessage();
    }

    @Override
    public void setMessage(String message) {
        throw new IllegalArgumentException();
    }

    @Override
    public int getColor() {
        return this.human.getType().getColor();
    }

    @Override
    public void setColor(int color) {
        throw new IllegalArgumentException();
    }
}
