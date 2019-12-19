package com.demosoft.life.imitation.entity.graphic;


import com.demosoft.life.imitation.entity.Cell;
import java.util.Optional;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public interface GraphicCell {

    Cell getCell();

    Optional<GraphicHuman> getGraphicHuman();

    Optional<GraphicPlant> getGraphicPlant();

    GraphicLandscape getGraphicLandscape();
}
