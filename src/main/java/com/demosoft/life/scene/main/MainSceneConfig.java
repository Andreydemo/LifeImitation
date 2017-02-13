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
    public static final int MAP_SIDE = (int) (Math.pow(2, DEGREE) + 1);

    @Bean
    Map map(){
        return new Map(MAP_SIDE, CellType.GROUND_LOW);
    }

    @Bean
    MapRender mapRender() {
        System.out.println("MAP side: " + MAP_SIDE);
        MapRender mapRender = new MapRender(550, 50, map(), 12, 12);
        return mapRender;

    }
}
