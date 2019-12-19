package com.demosoft.life.imitation.entity.graphic.impl;

import com.demosoft.life.imitation.entity.Cell;
import com.demosoft.life.imitation.entity.graphic.GraphicCell;
import com.demosoft.life.imitation.entity.graphic.GraphicHuman;
import com.demosoft.life.imitation.entity.graphic.GraphicLandscape;
import com.demosoft.life.imitation.entity.graphic.GraphicPlant;
import java.util.Optional;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public class GraphicCellImpl implements GraphicCell {

    private Cell cell;


    public GraphicCellImpl(Cell cell) {
        this.cell = cell;
    }

    @Override
    public Cell getCell() {
        return cell;
    }

    @Override
    public Optional<GraphicHuman> getGraphicHuman() {
        return cell.getHuman().map(GraphicHumanImpl::new);
    }

    @Override
    public Optional<GraphicPlant> getGraphicPlant() {
        return cell.getPlant().map(GraphicPlantImpl::new);
    }

    @Override
    public GraphicLandscape getGraphicLandscape() {
        return new GraphicLandscapeImpl(cell.getLandscape());
    }
}
