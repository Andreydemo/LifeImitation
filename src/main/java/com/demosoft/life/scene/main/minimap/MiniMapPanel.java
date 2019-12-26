package com.demosoft.life.scene.main.minimap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.demosoft.life.Utils;
import com.demosoft.life.assets.AssetsLoader;
import com.demosoft.life.imitation.entity.Map;
import com.demosoft.life.imitation.entity.graphic.GraphicMap;
import com.demosoft.life.scene.FlippedStage;
import com.demosoft.life.scene.main.MainScreen;
import com.demosoft.life.spring.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class MiniMapPanel {

    boolean showed = false;

    @Autowired
    @Qualifier("minMapFlippedStage")
    private FlippedStage stage;

    @Autowired
    @Qualifier("minMapCamera")
    private OrthographicCamera minMapCamera;


    @Autowired
    private GraphicMap map;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    @Autowired
    private ContextContainer contextContainer;

    @PostConstruct
    public void init() {
    }

    private void drawBackgroundMap() {
    }

    public void toggle() {
        showed = !showed;
        if (showed) {
            map.generateMiniMap();
            Image actor = new Image(map.getMiniMap());
            Utils.foolowToCamera(actor, minMapCamera);
            stage.addActor(actor);
        } else {
            stage.clear();
        }
    }

    public boolean isShowed() {
        return showed;
    }

    public void render() {
        drawBackgroundMap();
    }
}
