package com.demosoft.life.scene.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.demosoft.life.assets.AssetsLoader;
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
    public static final String UISKIN = "uiskin";
    private Image screenBg;
    private TextureAtlas atlas;

    @Autowired
    private FlippedStage stage;
    private Skin skin;
    private SelectBox<String> selectBox;
    private Vector2 debugPosition = new Vector2(500, Gdx.graphics.getHeight() - 500);
    private TextButton playButton;
   /* private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;*/


    @Autowired
    private ContextContainer context;

    @Autowired
    private AssetsLoader assetsLoader;

    @Autowired
    MapRender mapRender;

    @PostConstruct
    private void init() {
       /* tiledMap = new TiledMap();
        MapLayers layers = tiledMap.getLayers();

        TiledMapTileLayer layer1 = new TiledMapTileLayer(100, 100, 10, 10);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

        cell.setTile(new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("map/black_cell.bmp")))));
        layer1.setCell(99, 99, cell);
        layer1.setCell(50, 50, cell);
        layer1.setCell(25, 25, cell);
        layer1.setCell(0, 0, cell);

        layers.add(layer1);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);*/

        skin = assetsLoader.getSkin(UISKIN);
        selectBox = new SelectBox<String>(skin);
        playButton = new TextButton("PLAY GAME", skin);


        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("hello");
            }
        });


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
        atlas = new TextureAtlas(Gdx.files.internal("ui/newUi.pack"));
        screenBg = new Image(atlas.findRegion("screen-bg"));
        screenBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        foolowToCamera(screenBg);
        stage.setFlipped(true);
        stage.setDebugAll(true);
        stage.addActor(screenBg);
        stage.addActor(selectBox);
        stage.addActor(playButton);
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
        // stage.getBatch().setProjectionMatrix(context.camera.combined);
        stage.act();
        stage.draw();
        context.camera.update();
       /* tiledMapRenderer.setView( context.camera);
        tiledMapRenderer.render();*/
        mapRender.render();

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
