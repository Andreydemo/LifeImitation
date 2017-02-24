package com.demosoft.life.scene.main.info;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.demosoft.life.scene.FlippedStage;
import com.demosoft.life.scene.main.BaseStageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class InfoPanelStageHandler extends BaseStageHandler {

    @Autowired
    @Qualifier("flippedUiStage")
    private FlippedStage stage;

    @Autowired
    @Qualifier("uiCamera")
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
        return 1;
    }
}
