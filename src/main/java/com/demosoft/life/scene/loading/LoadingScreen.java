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
import com.demosoft.life.scene.ScreenNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Andrii_Korkoshko on 1/17/2017.
 */
@Component
public class LoadingScreen extends ScreenAdapter {


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

    public LoadingScreen() {
    }

    @Override
    public void show() {
        //stage.setViewport(game.viewport);

        // Tell the manager to load assets for the loading screen
        assetManager.load("loading/loading.pack", TextureAtlas.class);
        // Wait until they are finished loading
        assetManager.finishLoading();

        // Initialize the stage where we will place everything
        //stage = new Stage(viewport);

        // Get our textureatlas from the manager
        TextureAtlas atlas = assetManager.get("loading/loading.pack", TextureAtlas.class);
        TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("logo.png")));
        region.flip(false, true);

        // Grab the regions from the atlas and create some images
        logo = new Image(region);
        loadingFrame = new Image(atlas.findRegion("loading-frame"));
        loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
        screenBg = new Image(atlas.findRegion("screen-bg"));
        loadingBg = new Image(atlas.findRegion("loading-frame-bg"));

        // Add the loading bar animation
        Animation anim = new Animation(0.05f, atlas.findRegions("loading-bar-anim"));
        anim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        loadingBar = new LoadingBar(anim);

        // Or if you only need a static bar, you can do
        // loadingBar = new Image(atlas.findRegion("loading-bar1"));

        // Add all the actors to the stage
        stage.addActor(screenBg);
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);
        stage.addActor(loadingBarHidden);
        stage.addActor(loadingFrame);
        stage.addActor(logo);

        assetManager.load("logo.png", Texture.class);
        /*assetManager.load("menu_background.jpg", Texture.class);
        assetManager.load("gameover.png", Texture.class);
       assetManager.load("life.png", Texture.class);
       assetManager.load("sounds/journey.mp3", Music.class);
       assetManager.load("sounds/pop.ogg", Sound.class);
       assetManager.load("sounds/crash.ogg", Sound.class);
       assetManager.load("sounds/alarm.ogg", Sound.class);
       assetManager.load("sounds/shield.ogg", Sound.class);
       assetManager.load("sounds/fuel.ogg", Sound.class);
       assetManager.load("sounds/star.ogg", Sound.class);
       assetManager.load("thrustcopterassets.txt", TextureAtlas.class);
       assetManager.load("impact-40.fnt", BitmapFont.class);
       assetManager.load("Smoke", ParticleEffect.class);
       assetManager.load("Explosion", ParticleEffect.class);
       assetManager.load("player.pack", TextureAtlas.class);
       assetManager.load("isometric_hero/hero.pack", TextureAtlas.class);*/
        // Add everything to be loaded, for instance:

        //game.manager.load("uiskin.atlas", TextureAtlas.class);
        //game.manager.load("default.fnt", BitmapFont.class);
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
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (assetManager.update()) { // Load some, will return true if done
            // loading
            waitTime -= delta;
            if (waitTime <= 0) {
                //game.font=game.manager.get("impact-40.fnt", BitmapFont.class);
                //game.setScreen(new TiledLevelDemo(game));

                //applicationContainer.getGame().setScreen(mainMenuScene);
                screenNavigator.setScreen(screenNavigator.mainScreen);

                //game.setScreen(new ThrustCopterScene(game));
                //game.setScreen(new Sample3D(game));
                //game.setScreen(new Interaction3D(game));
                //game.setScreen(new SmokingPlane(game));
                //game.setScreen(new BulletSample(game));
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
        assetManager.unload("loading/loading.pack");
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
