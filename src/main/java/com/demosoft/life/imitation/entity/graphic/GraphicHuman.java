package com.demosoft.life.imitation.entity.graphic;

import com.demosoft.life.imitation.entity.Human;

/**
 * Created by Andrii_Korkoshko on 2/15/2017.
 */
public interface GraphicHuman extends Human {

    String getMessage();

    void setMessage(String message);

    int getColor();

    void setColor(int color);

}
