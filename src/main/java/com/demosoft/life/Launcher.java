package com.demosoft.life;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by andrii_korkoshko on 23.01.17.
 */
public class Launcher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "FlatMedievalWorld";
        //cfg.useGL30 = true;
        cfg.width = 1024;
        cfg.height = 768;


        LifeImitationApplication game = new LifeImitationApplication();
        LwjglApplication lwjglApplication = new LwjglApplication(game, cfg);
    }

}
