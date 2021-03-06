package com.demosoft.life;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.demosoft.life.scene.loading.LoadingScreen;
import com.demosoft.life.spring.ContextContainer;
import com.demosoft.life.spring.SpringContextTools;

/**
 * Created by andrii_korkoshko on 23.01.17.
 */
public class LifeImitationApplication extends Game {

    private ContextContainer contextContainer;

    @Override
    public void create() {
        SpringContextTools.initContext();
        LoadingScreen loadingScreen = SpringContextTools.getBean(LoadingScreen.class);
        setScreen(loadingScreen);
        contextContainer = SpringContextTools.getBean(ContextContainer.class);
        contextContainer.game = this;
    }

    @Override
    public void resize(int width, int height) {
        if (Gdx.graphics.getWidth() != width || Gdx.graphics.getHeight() != height) {
            System.out.println("game resize");
            //Gdx.graphics.setDisplayMode(width, height, Gdx.graphics.isFullscreen());
            Gdx.graphics.setWindowedMode(width, height);
            this.create();
        }
        contextContainer.viewport.update(width, height);
        contextContainer.camera.update();
    }


}
