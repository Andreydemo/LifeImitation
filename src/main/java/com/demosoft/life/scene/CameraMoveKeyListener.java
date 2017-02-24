package com.demosoft.life.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.demosoft.life.scene.main.StageManager;
import com.demosoft.life.scene.main.minimap.MiniMapPanel;
import com.demosoft.life.scene.main.info.DebugInfoPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Andrii_Korkoshko on 2/17/2017.
 */
@Component
public class CameraMoveKeyListener implements InputProcessor {


    @Autowired
    @Qualifier("camera")
    private OrthographicCamera camera;

    @Autowired
    private CameraManager cameraManager;

    @Autowired
    private DebugInfoPanel debugInfoPanel;

    @Autowired
    private MiniMapPanel miniMapPanel;

    @Autowired
    private StageManager stageManager;

    public static final int NUM_KEYBOARD_KEYS = 155; // Input.Keys in libGDX has 155 keys.

    public static boolean[] keys;
    public static boolean[] pkeys;

    static {
        keys = new boolean[NUM_KEYBOARD_KEYS];
        pkeys = new boolean[NUM_KEYBOARD_KEYS];
    }

    public static void setKey(int i, boolean b) {
        keys[i] = b;
    }

    public static boolean isDown(int i) {
        return keys[i];
    }

    public static boolean isPressed(int i) {
        return keys[i] && !pkeys[i];
    }

    public static void update() {
        for (int i = 0; i < NUM_KEYBOARD_KEYS; i++)
            pkeys[i] = keys[i];
    }

    public void processKeyPressed() {
        debugInfoPanel.update();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cameraManager.moveTop();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cameraManager.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cameraManager.moveDown();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            cameraManager.moveRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            stageManager.unfocusAll();
        }


    }

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.PLUS:
                cameraManager.incZoom();
                break;
            case Input.Keys.MINUS:
                cameraManager.decZoom();
                break;
            case Input.Keys.M:
                miniMapPanel.toggle();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        //  setKey(keycode, false);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (amount < 0) {
            cameraManager.incZoom();
        } else {
            cameraManager.decZoom();
        }
        return false;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

}

