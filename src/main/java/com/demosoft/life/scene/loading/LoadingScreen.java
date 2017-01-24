package com.demosoft.life.scene.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.demosoft.life.assets.AssetsLoader;
import com.demosoft.life.scene.ScreenNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Andrii_Korkoshko on 1/17/2017.
 */
@Component
public class LoadingScreen extends ScreenAdapter {


    public static final String LOGO_RESOURCE_NAME = "logo";
    public static final String LOADING_LOADING_FRAME = "loading/loading-frame";
    public static final String LOADING_LOADING_BAR_HIDDEN = "loading/loading-bar-hidden";
    public static final String LOADING_SCREEN_BG = "loading/screen-bg";
    public static final String LOADING_LOADING_FRAME_BG = "loading/loading-frame-bg";
    public static final String LOADING_LOADING_BAR_ANIM = "loading/loading-bar-anim";
    public static final float FRAME_DURATION = 0.05f;
    @Autowired
    private Stage stage;

    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;

    private float startX, endX;
    private float percent;

    private Actor loadingBar;
    private float waitTime = 1;

    @Autowired
    private AssetManager assetManager;

    @Autowired
    private Viewport viewport;

    @Autowired
    private ScreenNavigator screenNavigator;

    @Autowired
    private AssetsLoader assetsLoader;

    public LoadingScreen() {
    }

    @Override
    public void show() {

        logo = assetsLoader.getImage(LOGO_RESOURCE_NAME, true);

        loadingFrame = assetsLoader.getImage(LOADING_LOADING_FRAME);
        loadingBarHidden = assetsLoader.getImage(LOADING_LOADING_BAR_HIDDEN);
        screenBg = assetsLoader.getImage(LOADING_SCREEN_BG);
        loadingBg = assetsLoader.getImage(LOADING_LOADING_FRAME_BG);

        Animation anim = assetsLoader.getAnimation(LOADING_LOADING_BAR_ANIM, FRAME_DURATION);
        anim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        loadingBar = new LoadingBar(anim);

        stage.addActor(screenBg);
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);
        stage.addActor(loadingBarHidden);
        stage.addActor(loadingFrame);
        stage.addActor(logo);
    }

    @Override
    public void resize(int width, int height) {
        // Scale the viewport to fit the screen
        Vector2 scaledView = Scaling.fit.apply(800, 480, width, height);
        stage.getViewport().update((int) scaledView.x, (int) scaledView.y, true);

        // Make the background fill the screen
        screenBg.setSize(width, height);

        // Place the logo in the middle of the screen and 100 px up
        logo.setX((width - logo.getWidth()) / 2);
        logo.setY((height - logo.getHeight()) / 2 - 256);

        // Place the loading frame in the middle of the screen
        loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
        loadingFrame.setY((stage.getHeight() - loadingFrame.getHeight()) / 2);

        // Place the loading bar at the same spot as the frame, adjusted a few
        // px
        loadingBar.setX(loadingFrame.getX() + 15);
        loadingBar.setY(loadingFrame.getY() + 5);

        // Place the image that will hide the bar on top of the bar, adjusted a
        // few px
        loadingBarHidden.setX(loadingBar.getX() + 35);
        loadingBarHidden.setY(loadingBar.getY() - 3);
        // The start position and how far to move the hidden loading bar
        startX = loadingBarHidden.getX();
        endX = 440;

        // The rest of the hidden bar
        loadingBg.setSize(450, 50);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setY(loadingBarHidden.getY() + 3);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (assetManager.update()) {
            // loading
            waitTime -= delta;
            if (waitTime <= 0) {
                screenNavigator.setScreen(screenNavigator.mainScreen);

            }
        }

        // Interpolate the percentage to make it more smooth
        percent = Interpolation.linear.apply(percent, assetManager.getProgress(), 0.1f);

        // Update positions (and size) to match the percentage
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();

        // Show the loading screen
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        // Dispose the loading assets as we no longer need them
        //assetManager.unload("loading/loading.pack");
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
