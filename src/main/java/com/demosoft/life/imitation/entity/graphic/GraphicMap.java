package com.demosoft.life.imitation.entity.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.demosoft.life.imitation.entity.Map;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public interface GraphicMap {

    Map getMap();

    int getSelectedY();

    int getSelectedX();

    void setSelectedX(int selectedX);

    void setSelectedY(int selectedY);

    GraphicCell getCellAt(int x, int y);

    Texture getMiniMap();

    void setMiniMap(Texture miniMap);

    void generateMiniMap();
}
