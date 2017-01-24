package com.demosoft.life.scene.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.demosoft.life.imitation.entity.Map;
import com.demosoft.life.spring.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public class MapRender {

    private int cellHeight;
    private int cellWidth;
    private float mapPositionX;
    private float mapPositionY;

    private final Map map;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    @Autowired
    private ContextContainer contextContainer;

    public MapRender(float mapPositionY, float mapPositionX, Map map, int cellWidth, int cellHeight) {
        this.mapPositionY = mapPositionY;
        this.mapPositionX = mapPositionX;
        this.map = map;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    public void render() {
        contextContainer.camera.update();
        shapeRenderer.setProjectionMatrix(contextContainer.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                shapeRenderer.rect(getWorldX(x * cellWidth), contextContainer.translateY(getWorldY(y * cellHeight)), cellWidth, cellHeight);
            }
        }

        shapeRenderer.end();
    }

    private float getWorldX(float x) {
        return mapPositionX + x;
    }

    private float getWorldY(float y) {
        return mapPositionY + y;
    }


}
