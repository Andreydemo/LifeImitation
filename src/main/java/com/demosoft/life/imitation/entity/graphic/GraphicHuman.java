package com.demosoft.life.imitation.entity.graphic;

import com.demosoft.life.imitation.entity.Human;


public interface GraphicHuman {

    Human getHuman();

    String getMessage();

    void setMessage(String message);

    int getColor();

    void setColor(int color);

}
