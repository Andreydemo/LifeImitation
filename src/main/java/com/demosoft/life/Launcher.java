package com.demosoft.life;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.lwjgl.opengl.Display;

/**
 * Created by andrii_korkoshko on 23.01.17.
 */
public class Launcher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "FlatMedievalWorld";
        cfg.useGL30 = true;
        cfg.width = 1280;
        cfg.height = 1024;
        cfg.y = 50;
        cfg.resizable = false;
        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
        LifeImitationApplication game = new LifeImitationApplication();
        new LwjglApplication(game, cfg);
    }

}
