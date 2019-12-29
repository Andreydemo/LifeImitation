package com.demosoft.life.scene.main.info;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.demosoft.life.assets.AssetsLoader;
import com.demosoft.life.scene.base.CornerLink;
import com.demosoft.life.scene.base.MoveDragListener;
import com.demosoft.life.scene.base.ResizeDragListener;
import com.demosoft.life.scene.main.MainScreen;
import com.demosoft.life.scene.main.MapRender;
import com.demosoft.life.spring.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class DebugInfoPanel {

    private TextArea baseElement;
    private Label label;

    @Autowired
    private AssetsLoader assetsLoader;

    @Autowired
    private ContextContainer context;

    @Autowired
    private MapRender mapRender;

    @PostConstruct
    void init() {
        Skin skin = assetsLoader.getSkin(MainScreen.UISKIN);
        baseElement = new TextArea("mapInfoPanel", skin);
        baseElement.setBounds(1350, context.translateY(835), 500, 250);
        baseElement.setDisabled(true);


        label = new Label("Debug Info", skin);
        label.setBounds(1350, context.translateY(585), 300, 25);

        baseElement.setName("text");
        label.setName("label");
        baseElement.addListener(new MoveDragListener(baseElement, label));
        baseElement.addListener(new ResizeDragListener(baseElement, new CornerLink(label, CornerLink.Corner.UP_X_DOWN_Y)));
    }

    public void update() {
        String info = String.format("Debug: \n"
                        + "Camera Position:\n "
                        + "\n x : %s; y : %s\n"
                        + "scale: %s\n "
                        + "Focused cell\n"
                        + "\n x : %s; y : %s\n",
                context.getCamera().position.x,
                context.getCamera().position.y,
                context.getCamera().zoom,
                mapRender.getFocusedX(),
                mapRender.getFocusedY());
        baseElement.setText(info);

    }

    public void addToStage(Stage stage) {
        stage.addActor(label);
        stage.addActor(baseElement);
    }

}
