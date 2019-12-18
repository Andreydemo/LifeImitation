package com.demosoft.life.imitation.entity.graphic.impl;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.demosoft.life.imitation.entity.Cell;
import com.demosoft.life.imitation.entity.MapFactory;
import com.demosoft.life.imitation.entity.graphic.GraphicCell;
import com.demosoft.life.imitation.entity.graphic.GraphicMap;
import com.demosoft.life.imitation.entity.v2.impl.MapImpl;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public class GraphicMapImpl extends MapImpl implements GraphicMap {
    private MapImpl map;

    private int selectedY;
    private int selectedX;

    private Texture miniMap;

    public GraphicMapImpl(MapFactory mapFactory, MapImpl map) {
        super(mapFactory);
        this.map = map;
    }

    @Override
    public GraphicCell getCellAt(int x, int y) {
        return (GraphicCell) map.getCellAt(x, y);
    }

    @Override
    public Cell[][] getCells() {
        return map.getCells();
    }

    @Override
    public int getSize() {
        return map.getSize();
    }

    @Override
    public void reCreate(int size) {
        map.reCreate(size);
    }

    @Override
    public void setCell(Cell cell) {
        map.setCell(cell);
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
        miniMap = new Texture(pixmap);
    }
}
