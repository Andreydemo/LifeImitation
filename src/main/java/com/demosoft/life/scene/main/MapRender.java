package com.demosoft.life.scene.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.demosoft.life.imitation.entity.graphic.GraphicMap;
import com.demosoft.life.imitation.entity.impl.MapImpl;
import com.demosoft.life.scene.main.info.InfoPanelContainer;
import com.demosoft.life.spring.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public class MapRender {

    private int cellHeight;
    private int cellWidth;
    private float mapPositionX;
    private float mapPositionY;

    private final GraphicMap map;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public final ClickListener clickListener;

    @Autowired
    private ContextContainer contextContainer;

    @Autowired
    private InfoPanelContainer infoPanelContainer;

    public MapRender(float mapPositionX, float mapPositionY, GraphicMap map, int cellWidth, int cellHeight) {
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
                    infoPanelContainer.getCellInfoPanel().update(selectedX, selectedY);
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
       /* drawHumansAndPlants();*/
        drawBorder();
        drawSelected();
    }

    private void drawBackgroundMap() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {
                shapeRenderer.setColor(new Color(map.getCellAt(x, y).getGraphicLandscape().getColor()));
                shapeRenderer.rect(getWorldX(x * cellWidth), contextContainer.translateY(getWorldY(y * cellHeight)), cellWidth, cellHeight);


            }
        }
        shapeRenderer.end();
    }

    private void drawForegroundMap() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {
                Optional<Integer> foregroundColor = CellRenderer.getForegroundColor(map.getCellAt(x, y));
                if (foregroundColor.isPresent()) {
                    shapeRenderer.setColor(new Color(foregroundColor.get()));
                    shapeRenderer.rect(getWorldX(x * cellWidth + 2), contextContainer.translateY(getWorldY(y * cellHeight)) + 3, cellWidth - 5, cellHeight - 5);
                }

            }
        }
        shapeRenderer.end();
    }

/*
    private void drawHumansAndPlants() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {
                Optional<Integer> foregroundColor = CellRenderer.getForegroundColor(map.getValueAt(x, y).getValue());
                if (foregroundColor.isPresent()) {
                    shapeRenderer.setColor(new Color(foregroundColor.get()));
                    shapeRenderer.rect(getWorldX(x * cellWidth + 2), contextContainer.translateY(getWorldY(y * cellHeight)) + 3, cellWidth - 5, cellHeight - 5);
                }

            }
        }
        shapeRenderer.end();
    }
*/


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
