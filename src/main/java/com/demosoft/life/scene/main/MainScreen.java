package com.demosoft.life.scene.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.demosoft.life.assets.AssetsLoader;
import com.demosoft.life.scene.BaseScene;
import com.demosoft.life.scene.CameraMoveKeyListener;
import com.demosoft.life.scene.FlippedStage;
import com.demosoft.life.scene.main.info.InfoPanelContainer;
import com.demosoft.life.spring.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by andrii_korkoshko on 23.01.17.
 */
@Component
public class MainScreen extends BaseScene {
    public static final String UISKIN = "uiskin";
    private Image screenBg;
    private TextureAtlas atlas;

    @Autowired
    @Qualifier("flippedStage")
    private FlippedStage stage;

    @Autowired
    @Qualifier("flippedUiStage")
    private FlippedStage uiStage;

    @Autowired
    private CameraMoveKeyListener cameraMoveKeyListener;

    private Skin skin;
    //  private SelectBox<String> selectBox;
    private Vector2 debugPosition = new Vector2(1000, Gdx.graphics.getHeight() - 500);

    @Autowired
    private ControlPanel controlPanel;

    @Autowired
    InfoPanelContainer infoPanelContainer;

    @Autowired
    private ContextContainer context;

    @Autowired
    private AssetsLoader assetsLoader;

    @Autowired
    MapRender mapRender;

    @PostConstruct
    private void init() {


        skin = assetsLoader.getSkin(UISKIN);
        atlas = new TextureAtlas(Gdx.files.internal("ui/newUi.pack"));

        controlPanel.addToStage(uiStage);
        infoPanelContainer.addToStage(uiStage);




        stage.setFlipped(false);
        uiStage.setFlipped(false);


    }

    @Override
    public void show() {
        initStage();

        context.camera.setToOrtho(false);
        context.uiCamera.setToOrtho(false);
        Gdx.gl.glClearColor(128/255f, 128/255f, 128/255f, 1);
        //initScreenBg();
        if (Gdx.input.getInputProcessor() == null) {
            Gdx.input.setInputProcessor(new InputMultiplexer());
        }
        if (Gdx.input.getInputProcessor() instanceof InputMultiplexer) {
            ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(0, uiStage);
            ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(1, stage);
            ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(2, cameraMoveKeyListener);
            System.out.println("added");
        }
    }

    private void initStage() {
        stage.clear();

        stage.addListener(mapRender.clickListener);
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("-> stage " + event + "x: " + x + " y: " + y);
                for (Actor act : stage.getRoot().getChildren()) {
                    if (act instanceof SelectBox) {
                        System.out.println(((SelectBox) act).getX() + " " + ((SelectBox) act).getY());
                    }
                    if (act instanceof Image) {
                        System.out.println(((Image) act).getX() + " " + ((Image) act).getY());

                    }
                }
                super.clicked(event, x, y);
            }
        });
    }

    @Override
    public void hide() {
        context.camera.setToOrtho(true);
        context.uiCamera.setToOrtho(true);
        if (Gdx.input.getInputProcessor() instanceof InputMultiplexer) {
            ((InputMultiplexer) Gdx.input.getInputProcessor()).removeProcessor(stage);
            ((InputMultiplexer) Gdx.input.getInputProcessor()).removeProcessor(uiStage);
            ((InputMultiplexer) Gdx.input.getInputProcessor()).removeProcessor(cameraMoveKeyListener);
            System.out.println("removed");
        }
    }

    @Override
    public void render(float delta) {
        try {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.getBatch().setProjectionMatrix(context.camera.combined);

            stage.act();
            stage.draw();

            context.camera.update();

            cameraMoveKeyListener.processKeyPressed();

            mapRender.render();


            uiStage.getBatch().setProjectionMatrix(context.uiCamera.combined);

            uiStage.act();
            uiStage.draw();

            context.uiCamera.update();


            context.drawPosition();

            super.render(delta);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        uiStage.dispose();
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
