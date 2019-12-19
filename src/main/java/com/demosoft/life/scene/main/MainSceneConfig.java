package com.demosoft.life.scene.main;

import com.demosoft.life.imitation.entity.Map;
import com.demosoft.life.imitation.entity.MapFactory;
import com.demosoft.life.imitation.entity.graphic.GraphicMap;
import com.demosoft.life.imitation.entity.graphic.impl.GraphicMapImpl;
import com.demosoft.life.imitation.entity.v2.impl.MapFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
@Configuration
public class MainSceneConfig {

    public static final int DEGREE = 6;
    public static final int MAP_SIDE = (int) (Math.pow(2, DEGREE) + 1);

    @Bean
    MapFactory mapFactory() {
        return new MapFactoryImpl();
    }

    @Bean
    Map map(MapFactory mapFactory) {
        return mapFactory.generateRandomMap(MapFactoryImpl.mapSize);
    }

    @Bean
    GraphicMap graphicMap(Map map) {
        return new GraphicMapImpl(map);
    }

    @Bean
    MapRender mapRender(GraphicMap graphicMap) {
        System.out.println("MAP side: " + MAP_SIDE);
        MapRender mapRender = new MapRender(550, 50, graphicMap, 12, 12);
        return mapRender;

    }
}
