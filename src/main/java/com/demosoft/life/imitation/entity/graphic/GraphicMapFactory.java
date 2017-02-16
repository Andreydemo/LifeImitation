package com.demosoft.life.imitation.entity.graphic;

import com.demosoft.life.imitation.entity.MapFactory;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public interface GraphicMapFactory extends MapFactory {
    @Override
    GraphicMap createMap(int size);

    @Override
    GraphicMap generateRandomMap(int size);
}
