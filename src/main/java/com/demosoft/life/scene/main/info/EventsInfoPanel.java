package com.demosoft.life.scene.main.info;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.demosoft.life.assets.AssetsLoader;
import com.demosoft.life.scene.format.XFormatter;
import com.demosoft.life.scene.main.MainScreen;
import com.demosoft.life.spring.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * Created by Andrii_Korkoshko on 2/10/2017.
 */
@Component
public class EventsInfoPanel {

    private List<String> baseElement;
    private ArrayList listOfAllElements = new ArrayList();
    private Label label;
    private ScrollPane scrollElement;

    @Autowired
    private AssetsLoader assetsLoader;

    @Autowired
    private ContextContainer context;

    private int maxItemOnView;

    @PostConstruct
    void init() {
        Skin skin = assetsLoader.getSkin(MainScreen.UISKIN);
        baseElement = new List<>(skin);

        scrollElement = new ScrollPane(baseElement, skin);
        scrollElement.setScrollBarPositions(false, true);
        scrollElement.setBounds(25, context.translateY(985), 500, 500);
        scrollElement.layout();
       /* scrollElement.setFadeScrollBars(false);
        scrollElement.setFlickScroll(false);
        scrollElement.setVisible(true);*/
        maxItemOnView = (int) (baseElement.getHeight() / baseElement.getItemHeight());
        label = new Label("Events Info", skin);
        label.setBounds(25, context.translateY(485), 300, 25);

    }

    public void update(int days, String event) {
        listOfAllElements.add(formatEvent(days, event));
        if (baseElement.getItems().size <= maxItemOnView) {
            baseElement.getItems().add(formatEvent(days, event));
        } else {
            scrollViewList();
            baseElement.getItems().add(formatEvent(days, event));
        }
        baseElement.setSelectedIndex(baseElement.getItems().size - 1);
    }

    private void scrollViewList() {
        //make temp variable to hold last element

        //make a loop to run through the array list
        int lastElementIndex = baseElement.getItems().size - 1;
        for (int i = 0; i < lastElementIndex; i++) {
            //set the last element to the value of the 2nd to last element
            baseElement.getItems().set(i, baseElement.getItems().get(i + 1));
        }
        baseElement.getItems().removeIndex(lastElementIndex);
    }

    public void addToStage(Stage stage) {
        stage.addActor(label);
        stage.addActor(scrollElement);
        baseElement.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                System.out.println("baseElement SCROLLED");
                return super.scrolled(event, x, y, amount);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("baseElement touchUp");
                super.touchUp(event, x, y, pointer, button);
            }
        });
        scrollElement.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                System.out.println("scrollElement SCROLLED");
                return super.scrolled(event, x, y, amount);
            }
        });
    }

    public void reset() {
        baseElement.clear();
    }


    private String formatEvent(int days, String event) {
        return "[" + XFormatter.formatDateShort(days) + "] " + event;
    }


    public void act(float delta) {
        scrollElement.act(delta);
    }

}
