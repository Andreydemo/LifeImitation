package com.demosoft.life.scene.main.info;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.demosoft.life.assets.AssetsLoader;
import com.demosoft.life.imitation.entity.Cell;
import com.demosoft.life.imitation.entity.Landscape;
import com.demosoft.life.imitation.entity.graphic.*;
import com.demosoft.life.scene.base.CornerLink;
import com.demosoft.life.scene.base.MoveDragListener;
import com.demosoft.life.scene.base.ResizeDragListener;
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
public class CellInfoPanel {

    private TextArea baseElement;
    private Label label;
    final StringBuilder build = new StringBuilder();


    @Autowired
    private AssetsLoader assetsLoader;

    @Autowired
    private ContextContainer context;

    @Autowired
    private GraphicMap map;

    @PostConstruct
    void init() {
        Skin skin = assetsLoader.getSkin(MainScreen.UISKIN);
        baseElement = new TextArea("cellInfoPanel", skin);
        baseElement.setBounds(1350, context.translateY(535), 500, 500);
        baseElement.setDisabled(true);
        baseElement.setName("text");

        label = new Label("GraphicCell Info", skin);
        label.setBounds(1350, context.translateY(35), 300, 25);
        label.setName("label");
        baseElement.addListener(new MoveDragListener(baseElement, label));
        baseElement.addListener(new ResizeDragListener(baseElement, new CornerLink(label, CornerLink.Corner.UP_X_DOWN_Y)));
    }

    public void addToStage(Stage stage) {
        stage.addActor(label);
        stage.addActor(baseElement);
    }

    public void update(int x, int y) {
        GraphicCell gCell = map.getCellAt(x, y);
        Cell cell = gCell.getCell();
        GraphicLandscape graphicLandscape = gCell.getGraphicLandscape();
        Landscape landscape = graphicLandscape.getLandscape();

        StringBuilder result = new StringBuilder()
                .append("LandscapeType type as string: ").append(graphicLandscape.getMessage())
                .append("\nLandscapeType type: ").append(landscape.getType())
                .append("\nx : ").append(x)
                .append("; y : ").append(y);

        cell.getHuman().ifPresent(human -> {
            result.append("\nHuman type: ").append(human.getType())
                    .append("\nHuman active: ").append(human.isActive())
                    .append("\nHuman age: ").append(XFormatter.formatDate(human.getAge()))
                    .append("\nHuman energy: ").append(human.getEnergy())
                    .append("\nHuman satiety: ").append(human.getSatiety())
                    .append("\nHuman pregnancy: ").append(XFormatter.formatDate(human.getPregnancy()));
        });
        cell.getPlant().ifPresent(plant -> {
            result.append("\nPlant type: ").append(plant.getType())
                    .append("\nPlant active: ").append(plant.isActive())
                    .append("\nPlant name: ").append(plant.getType().getMessage())
                    .append("\nPlant fruits: ").append(plant.getFruits());
        });

        baseElement.setText(result.toString());
    }

    public void reset() {
        baseElement.setText("-");
    }

    private void setupInfo() {
    }
}
