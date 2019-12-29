package com.demosoft.life.scene.base;

import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Value;

@Value
public class CornerLink {
    Actor actor;
    Corner corner;

    public static enum Corner {
        UP_X_UP_Y, UP_X_DOWN_Y,
        DOWN_X_UP_Y, DOWN_X_DOWN_Y
    }
}

