package com.demosoft.life.scene.main.minimap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.demosoft.life.scene.FlippedStage;
import com.demosoft.life.scene.main.BaseStageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MiniMapStageHandler extends BaseStageHandler {

    @Autowired
    @Qualifier("minMapFlippedStage")
    private FlippedStage stage;

    @Autowired
    @Qualifier("minMapCamera")
    public OrthographicCamera camera;


    @Autowired
    public MiniMapPanel miniMapPanel;


    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public FlippedStage getStage() {
        return stage;
    }

    @Override
    public void render() {
        if (miniMapPanel.isShowed()) {
            getStage().getBatch().setProjectionMatrix(getCamera().combined);
            getStage().act();
            getStage().draw();
            getCamera().update();
        }
    }

    @Override
    public Integer getInputOrderNumber() {
        return -2;
    }
}
