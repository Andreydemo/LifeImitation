package com.demosoft.life.scene.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.demosoft.life.scene.FlippedStage;

public abstract class BaseStageHandler implements StageHandler {


    public void show() {
        getCamera().setToOrtho(false);
        if (Gdx.input.getInputProcessor() instanceof InputMultiplexer) {
                ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(getStage());
        }
    }

    public void hide() {
        getCamera().setToOrtho(true);
        if (Gdx.input.getInputProcessor() instanceof InputMultiplexer) {
            ((InputMultiplexer) Gdx.input.getInputProcessor()).removeProcessor(getStage());
        }
    }

    public void render() {
        getStage().getBatch().setProjectionMatrix(getCamera().combined);

        getStage().act();
        getStage().draw();
        getCamera().update();
    }

    public void unfocusAll() {
        getStage().unfocusAll();
    }

    public abstract OrthographicCamera getCamera();

    public abstract FlippedStage getStage();

    public Integer getInputOrderNumber() {
        return null;
    }

}
