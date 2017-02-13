package com.demosoft.life.scene.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.demosoft.life.assets.AssetsLoader;
import com.demosoft.life.logic.force.Force;
import com.demosoft.life.logic.statistic.Statistic;
import com.demosoft.life.scene.main.info.InfoPanelContainer;
import com.demosoft.life.spring.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by Andrii_Korkoshko on 2/13/2017.
 */
public class ControlPanel {

    private TextButton playButton;
    private TextButton stopButton;
    private TextButton pauseButton;

    @Autowired
    private AssetsLoader assetsLoader;

    @Autowired
    private ContextContainer context;

    @Autowired
    private Statistic statistic;

    @Autowired
    private Force force;

    @Autowired
    InfoPanelContainer infoPanelContainer;

    @PostConstruct
    void init() {
        Skin skin = assetsLoader.getSkin(MainScreen.UISKIN);
        initPlayButton(skin);
        initStopButton(skin);
        initPauseButton(skin);
    }


    private void initPauseButton(Skin skin) {
        pauseButton = new TextButton("PAUSE", skin);
        pauseButton.setBounds(1050, context.translateY(Gdx.graphics.getHeight() - 100), 200, 50);
        pauseButton.setTouchable(Touchable.disabled);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                force.pause();
                playButton.setTouchable(Touchable.enabled);
                pauseButton.setTouchable(Touchable.disabled);
                stopButton.setTouchable(Touchable.enabled);
            }
        });

    }

    private void initStopButton(Skin skin) {
        stopButton = new TextButton("STOP", skin);
        stopButton.setBounds(800, context.translateY(Gdx.graphics.getHeight() - 100), 200, 50);
        stopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                force.stop();
                statistic.reset();
                infoPanelContainer.getCellInfoPanel().reset();
                infoPanelContainer.getEventsInfoPanel().reset();
                infoPanelContainer.getMapInfoPanel().reset();
            }
        });

    }

    private void initPlayButton(Skin skin) {
        playButton = new TextButton("PLAY", skin);
        playButton.setBounds(550, context.translateY(Gdx.graphics.getHeight() - 100), 200, 50);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                force.start();
                playButton.setTouchable(Touchable.disabled);
                pauseButton.setTouchable(Touchable.enabled);
                stopButton.setTouchable(Touchable.disabled);
            }
        });

    }

    public void addToStage(Stage stage) {
        stage.addActor(pauseButton);
        stage.addActor(stopButton);
        stage.addActor(playButton);
    }
}
