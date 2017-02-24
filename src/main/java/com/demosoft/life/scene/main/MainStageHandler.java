package com.demosoft.life.scene.main;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.demosoft.life.scene.FlippedStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MainStageHandler extends BaseStageHandler {

    @Autowired
    @Qualifier("flippedStage")
    private FlippedStage stage;

    @Autowired
    @Qualifier("camera")
    public OrthographicCamera camera;

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public FlippedStage getStage() {
        return stage;
    }

    @Override
    public Integer getInputOrderNumber() {
        return 0;
    }
}
