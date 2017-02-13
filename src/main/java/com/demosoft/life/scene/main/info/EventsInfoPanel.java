package com.demosoft.life.scene.main.info;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.demosoft.life.assets.AssetsLoader;
import com.demosoft.life.scene.format.XFormatter;
import com.demosoft.life.scene.main.MainScreen;
import com.demosoft.life.spring.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Andrii_Korkoshko on 2/10/2017.
 */
@Component
public class EventsInfoPanel {

    private List<String> baseElement;
    private Label label;
    private ScrollPane scrollElement;

    @Autowired
    private AssetsLoader assetsLoader;

    @Autowired
    private ContextContainer context;

    @PostConstruct
    void init() {
        Skin skin = assetsLoader.getSkin(MainScreen.UISKIN);
        baseElement = new List<>(skin);

        scrollElement = new ScrollPane(baseElement, skin);

        scrollElement.setBounds(25, context.translateY(885), 500, 400);
        scrollElement.setFadeScrollBars(false);
        scrollElement.setFlickScroll(false);

        label = new Label("Events Info", skin);
        label.setBounds(25, context.translateY(485), 300, 25);

    }

    public void update(int days, String event) {
        baseElement.getItems().add(formatEvent(days, event));
    }

    public void addToStage(Stage stage) {
        stage.addActor(label);
        stage.addActor(scrollElement);
    }

    public void reset() {
        baseElement.clear();
    }


    private String formatEvent(int days, String event) {
        return "[" + XFormatter.formatDateShort(days) + "] " + event;
    }

}
