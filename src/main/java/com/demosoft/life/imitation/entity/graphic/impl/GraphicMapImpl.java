package com.demosoft.life.imitation.entity.graphic.impl;

import com.demosoft.life.imitation.entity.Cell;
import com.demosoft.life.imitation.entity.Map;
import com.demosoft.life.imitation.entity.MapFactory;
import com.demosoft.life.imitation.entity.graphic.GraphicCell;
import com.demosoft.life.imitation.entity.graphic.GraphicMap;
import com.demosoft.life.imitation.entity.impl.CellImpl;
import com.demosoft.life.imitation.entity.impl.MapImpl;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public class GraphicMapImpl extends MapImpl implements GraphicMap {
    private MapImpl map;

    private int selectedY;
    private int selectedX;

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
    public void setCell(CellImpl cell) {
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
}
