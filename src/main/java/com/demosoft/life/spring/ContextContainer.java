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
import org.springframework.stereotype.Component;

/**
 * Created by andrii_korkoshko on 23.01.17.
 */
@Component
public class ContextContainer {

    @Autowired
    public OrthographicCamera camera;

    @Autowired
    public Viewport viewport;

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
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            font.draw(batch, "x: " + Gdx.input.getX() + " y: " + Gdx.input.getY(), Gdx.input.getX(), translateY(Gdx.input.getY()));
            font.draw(batch, "unproject x: " + Gdx.input.getX() + " y: " + translateY(Gdx.input.getY()), Gdx.input.getX(),
                    translateY(Gdx.input.getY()) + 20);
            batch.end();
        }

    }
}
