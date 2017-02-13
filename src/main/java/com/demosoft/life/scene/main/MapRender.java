package com.demosoft.life.scene.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

    public final ClickListener clickListener;

    @Autowired
    private ContextContainer contextContainer;

    public MapRender(float mapPositionX, float mapPositionY, Map map, int cellWidth, int cellHeight) {
        this.mapPositionY = mapPositionY;
        this.mapPositionX = mapPositionX;
        this.map = map;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        clickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                y = contextContainer.translateY(y);
                if (x >= mapPositionX && y >= mapPositionY
                        && x <= getMapEndPositionX() && y <= getMapEndPositionY()) {
                    int selectedX = (int) ((x - mapPositionX) / cellWidth);
                    int selectedY = (int) ((y - mapPositionY) / cellHeight);
                    map.setSelectedX(selectedX);
                    map.setSelectedY(selectedY);
                    System.out.println("Selected x: " + selectedX + " | Selected y: " + selectedY);
                }
            }
        };
    }

    public void render() {
        contextContainer.camera.update();
        shapeRenderer.setProjectionMatrix(contextContainer.camera.combined);


        drawBackgroundMap();
        drawForegroundMap();
        drawBorder();
        drawSelected();
    }

    private void drawBackgroundMap() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {
                shapeRenderer.setColor(new Color(CellRenderer.getBackgroundColor(map.getValueAt(x, y).getValue())));
                shapeRenderer.rect(getWorldX(x * cellWidth), contextContainer.translateY(getWorldY(y * cellHeight)), cellWidth, cellHeight);


            }
        }
        shapeRenderer.end();
    }

    private void drawForegroundMap() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {
                shapeRenderer.setColor(new Color(CellRenderer.getForegroundColor(map.getValueAt(x, y).getValue())));
                shapeRenderer.rect(getWorldX(x * cellWidth + 2), contextContainer.translateY(getWorldY(y * cellHeight)) + 3, cellWidth - 5, cellHeight - 5);


            }
        }
        shapeRenderer.end();
    }

    private void drawBorder() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);

        for (int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {

                shapeRenderer.rect(getWorldX(x * cellWidth), contextContainer.translateY(getWorldY(y * cellHeight)), cellWidth, cellHeight);


            }
        }
        shapeRenderer.end();
    }

    private void drawSelected() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);


        shapeRenderer.rect(getWorldX(map.getSelectedX() * cellWidth), contextContainer.translateY(getWorldY(map.getSelectedY() * cellHeight)), cellWidth, cellHeight);


        shapeRenderer.end();
    }

    private float getWorldX(float x) {
        return mapPositionX + x;
    }

    private float getWorldY(float y) {
        return mapPositionY + y + cellHeight;
    }

    private float getMapEndPositionX() {
        return mapPositionX + cellWidth * map.getSize();
    }

    private float getMapEndPositionY() {
        return mapPositionY + cellHeight * map.getSize();
    }


}
