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
        cfg.width = 800;
        cfg.height = 640;


        LifeImitationApplication game = new LifeImitationApplication();
        LwjglApplication lwjglApplication = new LwjglApplication(game, cfg);
    }

}
