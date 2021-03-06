package com.demosoft.life.scene.main;

import com.demosoft.life.imitation.entity.graphic.GraphicMap;
import com.demosoft.life.imitation.entity.graphic.GraphicMapFactory;
import com.demosoft.life.imitation.entity.graphic.impl.GraphicMapFactoryImpl;
import com.demosoft.life.imitation.entity.impl.MapFactoryImpl;
import com.demosoft.life.infra.MapLoader;
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
    MapLoader mapLoader() {
        return new MapLoader();
    }



    @Bean
    GraphicMapFactory graphicMapFactory() {
        return new GraphicMapFactoryImpl();
    }

    @Bean
    GraphicMap map() {
        GraphicMap map = graphicMapFactory().generateRandomMap(MapFactoryImpl.mapSize);
        return map;
    }

    @Bean
    MapRender mapRender() {
        System.out.println("MAP side: " + MAP_SIDE);
        MapRender mapRender = new MapRender(550, 50, map(), 12, 12);
        return mapRender;

    }
}
