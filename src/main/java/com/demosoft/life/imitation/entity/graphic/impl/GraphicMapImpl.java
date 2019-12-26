package com.demosoft.life.imitation.entity.graphic.impl;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.demosoft.life.imitation.entity.Map;
import com.demosoft.life.imitation.entity.MapFactory;
import com.demosoft.life.imitation.entity.graphic.GraphicCell;
import com.demosoft.life.imitation.entity.graphic.GraphicMap;
import com.demosoft.life.imitation.entity.v2.impl.MapImpl;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
@Log
public class GraphicMapImpl implements GraphicMap {

    private Map map;

    private int selectedY;
    private int selectedX;

    private Texture miniMap;

    public GraphicMapImpl(Map map) {
        this.map = map;
    }

    @Override
    public GraphicCell getCellAt(int x, int y) {
        return new GraphicCellImpl(map.getCellAt(x, y));
    }

    @Override
    public Map getMap() {
        return map;
    }

    public int getSelectedY() {
        return selectedY;
    }

    public int getSelectedX() {
        return selectedX;
    }

    public void setSelectedX(int selectedX) {
        this.selectedX = selectedX;
    }

    public void setSelectedY(int selectedY) {
        this.selectedY = selectedY;
    }

    public Texture getMiniMap() {
        if (miniMap == null) {
            this.generateMiniMap();
        }
        return miniMap;
    }

    public void setMiniMap(Texture miniMap) {
        this.miniMap = miniMap;
    }

    @Override
    public void generateMiniMap() {
        Pixmap pixmap = new Pixmap(map.getSize(), map.getSize(), Pixmap.Format.RGBA8888);
        for (int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {
                pixmap.drawPixel(x, y, getCellAt(x, y).getGraphicLandscape().getColor());
            }
        }
        log.info("mini map generated");
        miniMap = new Texture(pixmap);
    }
}
