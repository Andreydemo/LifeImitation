package com.demosoft.life.spring;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.demosoft.life.scene.FlippedStage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by andrii_korkoshko on 23.01.17.
 */
@Configuration
@ComponentScan("com.demosoft.life")
public class AppConfiguration {


    @Bean
    AssetManager assetManager() {
        return new AssetManager();
    }

    @Bean
    Skin skin() {
        return new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    @Bean
    @Qualifier("camera")
    OrthographicCamera camera() {
        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(true);
        return orthographicCamera;
    }

    @Bean
    @Qualifier("uiCamera")
    OrthographicCamera uiCamera() {
        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(true);
        return orthographicCamera;
    }

    @Bean
    @Qualifier("viewPort")
    Viewport viewPort() {
        return new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera());
    }

    @Bean
    @Qualifier("uiViewPort")
    Viewport uiViewPort() {
        return new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), uiCamera());
    }

    @Bean
    @Qualifier("flippedStage")
    FlippedStage flippedStage() {
        return new FlippedStage(viewPort());
    }

    @Bean
    @Qualifier("flippedUiStage")
    FlippedStage flippedUiStage() {
        return new FlippedStage(uiViewPort());
    }

    @Bean
    Stage stage() {
        return new Stage(viewPort());
    }
}
