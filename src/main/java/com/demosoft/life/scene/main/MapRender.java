package com.demosoft.life.scene.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.demosoft.life.imitation.entity.graphic.GraphicCell;
import com.demosoft.life.imitation.entity.graphic.GraphicMap;
import com.demosoft.life.imitation.entity.type.HumanType;
import com.demosoft.life.imitation.entity.type.PlantType;
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

    private int focusedX;
    private int focusedY;

    private final GraphicMap map;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public final ClickListener clickListener;

    @Autowired
    private ContextContainer contextContainer;

    @Autowired
    private InfoPanelContainer infoPanelContainer;

    private int bufRadius = 30;
    private int baseBufRadius = 30;

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
                Optional<Vector2> selectedCellPosition = getCellByPosition(x, y);
                if (selectedCellPosition.isPresent()) {
                    map.setSelectedX((int) selectedCellPosition.get().x);
                    map.setSelectedY((int) selectedCellPosition.get().y);
                    infoPanelContainer.getCellInfoPanel().update((int) selectedCellPosition.get().x, (int) selectedCellPosition.get().y);
                }
            }
        };
    }

    public Optional<Vector2> getCellByPosition(float x, float y) {
        if (x >= mapPositionX && y >= mapPositionY
                && x <= getMapEndPositionX() && y <= getMapEndPositionY()) {
            int selectedX = (int) ((x - mapPositionX) / cellWidth);
            int selectedY = (int) ((y - mapPositionY) / cellHeight);
            Vector2 result = new Vector2(selectedX, selectedY);

            return Optional.of(result);
        }
        return Optional.empty();
    }

    public void calculateFocusedPosition() {
        Optional<Vector2> cellByPosition = getCellByPosition(contextContainer.getCamera().position.x, contextContainer.translateY(contextContainer.getCamera().position.y));
        if (cellByPosition.isPresent()) {
            focusedX = (int) cellByPosition.get().x;
            focusedY = (int) cellByPosition.get().y;
        }

    }

    public void render() {
        contextContainer.camera.update();
        shapeRenderer.setProjectionMatrix(contextContainer.camera.combined);


        drawBackgroundMap();
        drawForegroundMap();
        drawBorder();
        drawSelected();
        drawFocused();
    }

    private void drawBackgroundMap() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {
                if (inRenderingZone(x, y)) {
                    shapeRenderer.setColor(new Color(map.getCellAt(x, y).getGraphicLandscape().getColor()));
                    shapeRenderer.rect(getWorldX(x * cellWidth), contextContainer.translateY(getWorldY(y * cellHeight)), cellWidth, cellHeight);

                }

            }
        }
        shapeRenderer.end();
    }

    private void drawForegroundMap() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {
                if (inRenderingZone(x, y)) {
                    Optional<Integer> foregroundColor = getForegroundColor(map.getCellAt(x, y));
                    if (foregroundColor.isPresent()) {
                        shapeRenderer.setColor(new Color(foregroundColor.get()));
                        shapeRenderer.rect(getWorldX(x * cellWidth + 2), contextContainer.translateY(getWorldY(y * cellHeight)) + 3, cellWidth - 5, cellHeight - 5);
                    }
                }

            }
        }
        shapeRenderer.end();
    }

    private void drawBorder() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);

        for (int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {
                if (inRenderingZone(x, y)) {
                    shapeRenderer.rect(getWorldX(x * cellWidth), contextContainer.translateY(getWorldY(y * cellHeight)), cellWidth, cellHeight);
                }

            }
        }
        shapeRenderer.end();
    }

    public boolean inRenderingZone(int x, int y) {
        return x >= (focusedX - bufRadius) && x <= (focusedX + bufRadius) &&
                y >= (focusedY - bufRadius) && y <= (focusedY + bufRadius);
    }

    private void drawSelected() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);


        shapeRenderer.rect(getWorldX(map.getSelectedX() * cellWidth), contextContainer.translateY(getWorldY(map.getSelectedY() * cellHeight)), cellWidth, cellHeight);


        shapeRenderer.end();
    }

    private void drawFocused() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.LIME);


        shapeRenderer.rect(getWorldX(focusedX * cellWidth), contextContainer.translateY(getWorldY(focusedY * cellHeight)), cellWidth, cellHeight);


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

    public static Optional<Integer> getForegroundColor(GraphicCell cellData) {
        Integer color;
        int plantType = cellData.getPlant().getType().getValue();
        if (cellData.getHuman().getType() != HumanType.HUMAN_TYPE_EMPTY) {
            color = cellData.getHuman().getType().getColor();
        } else if (plantType != PlantType.PLANT_TYPE_EMPTY.getValue()) {

            color = PlantType.getByValue(plantType).getColor();
        } else {
            return Optional.empty();
        }
        return Optional.of(color);
    }

    public int getBufRadius() {
        return bufRadius;
    }

    public void setBufRadius(int bufRadius) {
        this.bufRadius = bufRadius;
    }

    public int getFocusedX() {
        return focusedX;
    }

    public void setFocusedX(int focusedX) {
        this.focusedX = focusedX;
    }

    public int getFocusedY() {
        return focusedY;
    }

    public void setFocusedY(int focusedY) {
        this.focusedY = focusedY;
    }

    public int getBaseBufRadius() {
        return baseBufRadius;
    }

    public void setBaseBufRadius(int baseBufRadius) {
        this.baseBufRadius = baseBufRadius;
    }
}
