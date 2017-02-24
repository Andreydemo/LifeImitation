package com.demosoft.life.scene.main.minimap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.demosoft.life.scene.FlippedStage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiniMapConfig {

    @Bean
    @Qualifier("minMapCamera")
    OrthographicCamera minMapCamera() {
        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(true);
        return orthographicCamera;
    }

    @Bean
    @Qualifier("minMapViewPort")
    Viewport minMapViewPort() {
        return new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), minMapCamera());
    }

    @Bean
    @Qualifier("minMapFlippedStage")
    FlippedStage minMapFlippedStage() {
        return new FlippedStage(minMapViewPort(), "minMapFlippedStage");
    }
}
