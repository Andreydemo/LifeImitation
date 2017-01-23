package com.demosoft.life.scene.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.demosoft.life.scene.BaseScene;
import com.demosoft.life.scene.FlippedStage;
import com.demosoft.life.spring.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by andrii_korkoshko on 23.01.17.
 */
@Component
public class MainScreen extends BaseScene {
    Image screenBg;
    TextureAtlas atlas;

    FlippedStage stage;
    Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    SelectBox<String> debugLevel = new SelectBox<String>(skin);
    Vector2 debugPosition = new Vector2(500, Gdx.graphics.getHeight() - 500);

    public static final Vector2 debugSize = new Vector2(800, 600);

    @Autowired
    private ContextContainer context;

   /* @Autowired
    private FlagController flags;*/
/*
    @Autowired
    private Logger logger;*/

    @PostConstruct
    private void init() {
        debugLevel.setItems(new String[]{"Item 1", "Item 2", "Item 3","Item 4"});
        debugLevel.setSelectedIndex(0);
        debugLevel.setDebug(true);
        debugLevel.setBounds(debugPosition.x, debugPosition.y, 200, 50);
        debugLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("->" + event + "x: " + x + " y: " + y);
                super.clicked(event, x, y);
            }
        });
        atlas = new TextureAtlas(Gdx.files.internal("ui/newUi.pack"));
        screenBg = new Image(atlas.findRegion("screen-bg"));
        screenBg.setSize(debugSize.x, debugSize.y);
        foolowToCamera(screenBg);
        stage = new FlippedStage(context.viewport);
        stage.setFlipped(true);
        if (Gdx.input.getInputProcessor() instanceof InputMultiplexer) {
            ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(0, stage);
        }
        stage.setViewport(context.viewport);
        stage.setDebugAll(true);
        stage.addActor(screenBg);
        stage.addActor(debugLevel);
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("-> stage " + event + "x: " + x + " y: " + y);
                for (Actor act : stage.getRoot().getChildren()) {
                    if (act instanceof SelectBox) {
                       // logger.logInfo(((SelectBox) act).getX() + " " + ((SelectBox) act).getY());
                    }
                    if (act instanceof Image) {
                        //logger.logInfo(((Image) act).getX() + " " + ((Image) act).getY());

                    }
                }
                super.clicked(event, x, y);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        context.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //context.getActiveScene().render(delta);
        //if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
           // if (flags.debugEnabled) {
             //   context.loadActiveScene();
               // flags.debugEnabled = false;
            //} else {
             //   context.saveActiveScene();
               // context.game.setScreen(this);
                //flags.debugEnabled = true;

           // }
      //  }
       /* foolowToCamera(screenBg);
        foollowToCamera(debugLevel);*/


        stage.getBatch().setProjectionMatrix(context.camera.combined);
        stage.act();
        stage.draw();


        /*for (Actor act : stage.getRoot().getChildren()) {
            if (act instanceof SelectBox) {
                logger.logInfo(((SelectBox)act).getX() + " " + ((SelectBox)act).getY());

            }
        }*/

        super.render(delta);
        context.camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
       // logger.logDebug(context.getCamera().position + " " + (float) (screenBg.getX() + screenBg.getWidth() / 2) + ":" + (float) (screenBg.getY() + screenBg.getHeight() / 2));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Vector2 getImageCentre(Image image) {
        return new Vector2((float) (image.getX() + image.getWidth() / 2), (float) (image.getY() + image.getHeight() / 2));
    }

    public Vector2 getImageCentre(SelectBox image) {
        return new Vector2((float) (image.getX() + image.getWidth() / 2), (float) (image.getY() + image.getHeight() / 2));
    }

    public void foolowToCamera(Image image) {
        Vector2 imageCentre = getImageCentre(image);
        Vector2 diff = new Vector2(context.camera.position.x - imageCentre.x, context.camera.position.y - imageCentre.y);
        image.setX(image.getX() + diff.x);
        image.setY(image.getY() + diff.y);

    }

    public void foollowToCamera(SelectBox box) {
      /*  Vector2 imageCentre = getImageCentre(box);
        Vector2 diff = new Vector2(context.getCamera().position.x - Gdx.graphics.getWidth()/ 2, context.getCamera().position.y - Gdx.graphics.getHeight() / 2);
        box.setX(debugPosition.x + diff.x);
        box.setY(debugPosition.y + diff.y);*/
    }

}
