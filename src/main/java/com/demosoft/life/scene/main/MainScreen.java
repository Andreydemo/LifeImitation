package com.demosoft.life.scene.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.demosoft.life.assets.AssetsLoader;
import com.demosoft.life.scene.BaseScene;
import com.demosoft.life.scene.FlippedStage;
import com.demosoft.life.scene.main.info.InfoPanelContainer;
import com.demosoft.life.spring.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FlippedStage stage;
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
        initScreenBg();

        controlPanel.addToStage(stage);
        infoPanelContainer.addToStage(stage);
        stage.addListener(mapRender.clickListener);




        stage.setFlipped(true);
        stage.setDebugAll(true);


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

    private void initScreenBg() {
        screenBg = new Image(atlas.findRegion("white-screen-bg"));
        screenBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        foolowToCamera(screenBg);
        stage.addActor(screenBg);
    }


    private void initSelectBox() {
        SelectBox<String> selectBox = new SelectBox<>(skin);
        selectBox.setItems(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"});
        selectBox.setSelectedIndex(0);
        selectBox.setDebug(true);
        selectBox.setBounds(debugPosition.x, debugPosition.y, 200, 50);
        selectBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("->" + event + "x: " + x + " y: " + y);
                super.clicked(event, x, y);
            }
        });
    }

    @Override
    public void show() {
        if (Gdx.input.getInputProcessor() == null) {
            Gdx.input.setInputProcessor(new InputMultiplexer());
        }
        if (Gdx.input.getInputProcessor() instanceof InputMultiplexer) {
            ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(0, stage);
            System.out.println("added");
        }
    }

    @Override
    public void hide() {
        if (Gdx.input.getInputProcessor() instanceof InputMultiplexer) {
            ((InputMultiplexer) Gdx.input.getInputProcessor()).removeProcessor(stage);
            System.out.println("removed");
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        context.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().setProjectionMatrix(context.camera.combined);
        stage.act();
        stage.draw();
        context.camera.update();
        mapRender.render();
        context.drawPosition();

        for (Actor act : stage.getRoot().getChildren()) {
            if (act instanceof SelectBox) {
                // System.out.println(((SelectBox)act).getX() + " " + ((SelectBox)act).getY());
            }
        }

        super.render(delta);
        context.camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // System.out.println((context.camera.position + " " + (float) (screenBg.getX() + screenBg.getWidth() / 2) + ":" + (float) (screenBg.getY() + screenBg.getHeight() / 2)));
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
