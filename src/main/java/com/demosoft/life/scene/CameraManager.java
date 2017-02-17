package com.demosoft.life.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CameraManager {

    @Autowired
    @Qualifier("camera")
    private OrthographicCamera camera;

    private Vector2 camermove = new Vector2();
    public static final float maxZoom = 0.123f;
    public static final float minZoom = 6f;
    public static final float zoomStep = 1.05f;
    private boolean flipped = false;

    public CameraManager() {
    }

    public Vector2 getCamermove() {
        return camermove;
    }

    public void setCamermove(Vector2 camermove) {
        this.camermove = camermove;
    }

    public void moveRight() {
        camera.translate(6, 0);
    }

    public void flipCamera(boolean flip) {
        Vector3 oldPos = new Vector3(camera.position);
        camera.setToOrtho(flip, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(oldPos);
        flipped = flip;
    }

    public void moveTop() {
        camera.translate(0, 6);
    }

    public void moveDown() {

        camera.translate(0, -6);
    }

    public void moveLeft() {
        camera.translate(-6, 0);
    }

    public void incZoom() {
        float zoom = camera.zoom;
        zoom /= zoomStep;
        if (zoom > maxZoom) {
            camera.zoom = zoom;
        }
    }

    public void decZoom() {
        float zoom = camera.zoom;
        zoom *= zoomStep;
        if (zoom < minZoom) {
            camera.zoom = zoom;
        }
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

}
