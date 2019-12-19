package com.demosoft.life.imitation.entity.graphic;

import com.demosoft.life.imitation.entity.Landscape;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public interface GraphicLandscape {

    Landscape getLandscape();

    int getColor();

    void setColor(int color);

    String getMessage();

    void setMessage(String message);
}
