package com.demosoft.life.scene.main;

import com.demosoft.life.imitation.entity.CellType;
import com.demosoft.life.imitation.entity.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
@Configuration
public class MainSceneConfig {

    public static final int DEGREE = 6;

    @Bean
    MapRender mapRender() {
        int mapSide = (int) (Math.pow(2, DEGREE) + 1);
        System.out.println("MAP side: " + mapSide);
        Map map = new Map(mapSide, CellType.GROUND_LOW);
        MapRender mapRender = new MapRender(50, 50, map, 12, 12);
        return mapRender;

    }
}
