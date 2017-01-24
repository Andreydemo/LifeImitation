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

    @Bean
    MapRender mapRender() {
        Map map = new Map(100, 100, CellType.GROUND);
        MapRender mapRender = new MapRender(100, 100, map, 5, 5);
        return mapRender;

    }
}
