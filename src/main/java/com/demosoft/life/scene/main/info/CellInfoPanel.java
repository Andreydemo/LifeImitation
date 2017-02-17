package com.demosoft.life.scene.main.info;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.demosoft.life.assets.AssetsLoader;
import com.demosoft.life.imitation.entity.*;
import com.demosoft.life.imitation.entity.Cell;
import com.demosoft.life.imitation.entity.graphic.*;
import com.demosoft.life.imitation.entity.impl.CellImpl;
import com.demosoft.life.imitation.entity.impl.MapImpl;
import com.demosoft.life.imitation.entity.impl.UcfCoder;
import com.demosoft.life.imitation.entity.type.PlantType;
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

        label = new Label("GraphicCell Info", skin);
        label.setBounds(1350, context.translateY(35), 300, 25);
    }

    public void addToStage(Stage stage) {
        stage.addActor(label);
        stage.addActor(baseElement);
    }

    public void update(int x, int y) {
        GraphicCell cell = map.getCellAt(x, y);
        long cellData = ((CellImpl) cell).getValue();
        GraphicLandscape graphicLandscape = cell.getGraphicLandscape();
        GraphicHuman graphicHuman = cell.getGraphicHuman();
        GraphicPlant graphicPlant = cell.getGraphicPlant();
        String info = String.format("Raw: %s\n"
                        + "\n Raw value: %s\n\n"
                        + "\n LandscapeType type as string: %s"
                        + "\n LandscapeType type: %s"
                        + "\n "
                        + "\n x : %s; y : %s"
                        + "\n GraphicHuman type: %s"
                        + "\n GraphicHuman age: %s"
                        + "\n GraphicHuman energy: %s"
                        + "\n GraphicHuman satiety: %s"
                        + "\n GraphicHuman pregnancy: %s"
                        + "\n "
                        + "\n PlantType type: %s"
                        + "\n PlantType name: %s"
                        + "\n PlantType fruits: %s"
                        + "\n "
                        + "\n Active flag (GraphicHuman): %s"
                        + "\n Active flag (PlantType): %s",
                XFormatter.formatRaw(cellData),
                cellData,
                graphicLandscape.getMessage(),
                graphicLandscape.getType().getValue(),
                x, y,
                graphicHuman.getType().getValue(),
                XFormatter.formatDate(graphicHuman.getAge()),
                graphicHuman.getAge(),
                graphicHuman.getSatiety(),
                XFormatter.formatDate(graphicHuman.getPregnancy()),
                graphicPlant.getType().getValue(),
                graphicPlant.getMessage(),
                graphicPlant.getFruits(),
                cell.getActiveFlagHuman(),
                cell.getActiveFlagPlant());
        baseElement.setText(info);
    }

    public void reset() {
        baseElement.setText("-");
    }

    private void setupInfo() {
    }
}
