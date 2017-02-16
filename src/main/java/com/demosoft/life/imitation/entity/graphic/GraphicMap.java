package com.demosoft.life.imitation.entity.graphic;

import com.demosoft.life.imitation.entity.Map;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public interface GraphicMap extends Map {

    int getSelectedY();

    int getSelectedX();

    void setSelectedX(int selectedX);

    void setSelectedY(int selectedY);

    GraphicCell getCellAt(int x, int y);
}
