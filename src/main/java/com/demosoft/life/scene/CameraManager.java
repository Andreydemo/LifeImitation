package com.demosoft.life.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.demosoft.life.scene.main.MapRender;
import com.demosoft.life.scene.main.minimap.MiniMapPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CameraManager {

    @Autowired
    @Qualifier("camera")
    private OrthographicCamera camera;

    @Autowired
    @Qualifier("minMapCamera")
    public OrthographicCamera minMapCamera;

    @Autowired
    private MiniMapPanel miniMapPanel;

    private Vector2 camermove = new Vector2();
    public static final float maxZoom = 0.060f;
    public static final float minZoom = 20f;
    public static final float zoomStep = 1.05f;
    private boolean flipped = false;

    @Autowired
    private MapRender mapRender;

    public CameraManager() {
    }

    public Vector2 getCamermove() {
        return camermove;
    }

    public void setCamermove(Vector2 camermove) {
        this.camermove = camermove;
    }


    public void flipCamera(boolean flip) {
        Vector3 oldPos = new Vector3(camera.position);
        camera.setToOrtho(flip, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(oldPos);
        flipped = flip;
    }

    public void moveTop() {
        camera.translate(0, 6 * getZoomMoveIndex());
        mapRender.calculateFocusedPosition();
    }

    private int getZoomMoveIndex() {
        return ((int) camera.zoom) == 0 ? 1 : ((int) camera.zoom);
    }

    public void moveDown() {
        camera.translate(0, -6 * (getZoomMoveIndex()));
        mapRender.calculateFocusedPosition();
    }

    public void moveRight() {
        camera.translate(6 * (getZoomMoveIndex()), 0);
        mapRender.calculateFocusedPosition();
    }

    public void moveLeft() {
        camera.translate(-6 * (getZoomMoveIndex()), 0);
        mapRender.calculateFocusedPosition();
    }

    public void incZoom() {
        OrthographicCamera camera = miniMapPanel.isShowed() ? minMapCamera : this.camera;
        float zoom = camera.zoom;
        zoom /= zoomStep;
        if (zoom > maxZoom) {
            camera.zoom = zoom;
        }
        calcBufRadiusAccordingToZoom();
    }

    private void calcBufRadiusAccordingToZoom() {
        mapRender.setBufRadius((int) (mapRender.getBaseBufRadius() * camera.zoom));
    }

    public void decZoom() {
        OrthographicCamera camera = miniMapPanel.isShowed() ? minMapCamera : this.camera;
        float zoom = camera.zoom;
        zoom *= zoomStep;
        if (zoom < minZoom) {
            camera.zoom = zoom;
        }
        calcBufRadiusAccordingToZoom();
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

}
