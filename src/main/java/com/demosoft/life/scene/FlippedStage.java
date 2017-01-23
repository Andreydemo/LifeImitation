package com.demosoft.life.scene;

/**
 * Created by andrii_korkoshko on 23.01.17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Andrii_Korkoshko
 */
public class FlippedStage extends Stage {

    private boolean flipped = false;

    public FlippedStage(Viewport viewport) {
        super(viewport);
    }

    public FlippedStage(Viewport viewport, boolean flipped) {
        super(viewport);
        this.flipped = flipped;
    }

    public FlippedStage() {
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (flipped) {
            System.out.println("FlippedStage touchUp y:" + (Gdx.graphics.getHeight() - screenY));
            return super.touchUp(screenX, Gdx.graphics.getHeight() - screenY, pointer, button);
        } else {
            System.out.println("FlippedStage touchUp y:" + (screenY));
            return super.touchUp(screenX, screenY, pointer, button);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (flipped) {
            return super.touchDown(screenX, Gdx.graphics.getHeight() - screenY, pointer, button);
        } else {
            return super.touchDown(screenX, screenY, pointer, button);
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (flipped) {
            return super.touchDragged(screenX, Gdx.graphics.getHeight() - screenY, pointer);
        } else {
            return super.touchDragged(screenX, screenY, pointer);
        }
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

}

