package com.demosoft.life.spring;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.demosoft.life.LifeImitationApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by andrii_korkoshko on 23.01.17.
 */
@Component
public class ContextContainer {

    @Autowired
    @Qualifier("camera")
    public OrthographicCamera camera;

    @Autowired
    @Qualifier("uiCamera")
    public OrthographicCamera uiCamera;

    @Autowired
    @Qualifier("viewPort")
    public Viewport viewport;

    @Autowired
    @Qualifier("uiViewPort")
    public Viewport uiViewport;

    public LifeImitationApplication game;

    public final BitmapFont font = new BitmapFont(false);
    private final SpriteBatch batch = new SpriteBatch();
    ;

    private boolean flipped = false;

    public float translateY(float y) {
        if (!this.isFlipped()) {
            return Gdx.graphics.getHeight() - y;
        }
        Vector3 v = new Vector3(0, y, 0);
        return camera.unproject(v).y;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void drawPosition() {
        if (Gdx.input.isTouched()) {
            batch.setProjectionMatrix(uiCamera.combined);
            batch.begin();
            font.draw(batch, "screen x: " + Gdx.input.getX() + " y: " + Gdx.input.getY(), Gdx.input.getX(), translateY(Gdx.input.getY()));
            font.draw(batch, "world x: " + translateX(Gdx.input.getX()) + " y: " + unprojectY(Gdx.input.getY()), Gdx.input.getX(), translateY(Gdx.input.getY()) + 20);
            batch.end();
        }

    }

    public float translateX(int x) {
        Vector3 v = new Vector3(x, 0, 0);
        return camera.unproject(v).x;
    }

    public float unprojectY(float y) {
        Vector3 v = new Vector3(0, y, 0);
        return camera.unproject(v).y;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    public Viewport getUiViewport() {
        return uiViewport;
    }

    public LifeImitationApplication getGame() {
        return game;
    }

    public BitmapFont getFont() {
        return font;
    }
}
