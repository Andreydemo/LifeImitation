package com.demosoft.life.scene.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.demosoft.life.assets.AssetsLoader;
import com.demosoft.life.imitation.entity.Map;
import com.demosoft.life.logic.force.Force;
import com.demosoft.life.logic.statistic.Statistic;
import com.demosoft.life.scene.main.info.InfoPanelContainer;
import com.demosoft.life.spring.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Andrii_Korkoshko on 2/13/2017.
 */
@Component
public class ControlPanel {

    private TextButton playButton;
    private TextButton stopButton;
    private TextButton pauseButton;
    private TextButton generateLandScapeButton;

    private TextButton generatePlantsButton;
    private TextField plantsCount;
    private Label plantsCount_Label;

    private TextButton generateHumansButton;
    private TextField humansMenCount;
    private Label humansMenCount_Label;
    private TextField humansWomanCount;
    private Label humansWomanCount_Label;

    @Autowired
    private AssetsLoader assetsLoader;

    @Autowired
    private Map map;

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
        initGenerateButton(skin);
        initGeneratePlantsButton(skin);
        initGenerateHumansButton(skin);
    }

    private void initGenerateButton(Skin skin) {
        generateLandScapeButton = new TextButton("GENERATE Landscape", skin);
        generateLandScapeButton.setBounds(1300, context.translateY(Gdx.graphics.getHeight() - 125), 200, 25);
        generateLandScapeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                map.generateLandscape();
            }
        });

    }

    private void initGeneratePlantsButton(Skin skin) {
        generatePlantsButton = new TextButton("GENERATE Plants", skin);
        generatePlantsButton.setBounds(1300, context.translateY(Gdx.graphics.getHeight() - 75), 200, 25);

        plantsCount = new TextField("10", skin);
        plantsCount.setBounds(1550, context.translateY(Gdx.graphics.getHeight() - 75), 50, 25);
        plantsCount.setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());

        plantsCount_Label = new Label("plants count", skin);
        plantsCount_Label.setBounds(1550, context.translateY(Gdx.graphics.getHeight() - 100), 100, 25);

        generatePlantsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                map.placeTrees(Integer.valueOf(plantsCount.getText()));
            }
        });

    }

    private void initGenerateHumansButton(Skin skin) {
        generateHumansButton = new TextButton("GENERATE Humans", skin);
        generateHumansButton.setBounds(1300, context.translateY(Gdx.graphics.getHeight() - 25), 200, 25);


        humansMenCount = new TextField("10", skin);
        humansMenCount.setBounds(1550, context.translateY(Gdx.graphics.getHeight() - 25), 50, 25);
        humansMenCount.setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());

        humansMenCount_Label = new Label("men count", skin);
        humansMenCount_Label.setBounds(1550, context.translateY(Gdx.graphics.getHeight() - 50), 100, 25);

        humansWomanCount = new TextField("10", skin);
        humansWomanCount.setBounds(1650, context.translateY(Gdx.graphics.getHeight() - 25), 50, 25);
        humansWomanCount.setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());

        humansWomanCount_Label = new Label("woman count", skin);
        humansWomanCount_Label.setBounds(1650, context.translateY(Gdx.graphics.getHeight() - 50), 100, 25);


        generateHumansButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                map.placePeoples(Integer.valueOf(humansMenCount.getText()), Integer.valueOf(humansWomanCount.getText()));
            }
        });

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
        stage.addActor(generateLandScapeButton);

        stage.addActor(generatePlantsButton);
        stage.addActor(plantsCount);
        stage.addActor(plantsCount_Label);

        stage.addActor(generateHumansButton);
        stage.addActor(humansWomanCount);
        stage.addActor(humansWomanCount_Label);
        stage.addActor(humansMenCount);
        stage.addActor(humansMenCount_Label);
    }
}
