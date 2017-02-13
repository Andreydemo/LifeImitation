package com.demosoft.life.scene.main.info;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.demosoft.life.assets.AssetsLoader;
import com.demosoft.life.imitation.entity.Map;
import com.demosoft.life.imitation.entity.UcfCoder;
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
    private Map map;

    @PostConstruct
    void init() {
        Skin skin = assetsLoader.getSkin(MainScreen.UISKIN);
        baseElement = new TextArea("cellInfoPanel", skin);
        baseElement.setBounds(1350, context.translateY(535), 500, 500);
        baseElement.setDisabled(true);

        label = new Label("Cell Info", skin);
        label.setBounds(1350, context.translateY(35), 300, 25);
    }

    public void addToStage(Stage stage) {
        stage.addActor(label);
        stage.addActor(baseElement);
    }

    public void update(int y, int x) {
        long cellData = map.getRawDataAt(y, x);
        String info = String.format("Landscape type: %s"
                        + "\n "
                        + "\n Human type: %s"
                        + "\n Human age: %s"
                        + "\n Human energy: %s"
                        + "\n Human satiety: %s"
                        + "\n Human pregnancy: %s"
                        + "\n "
                        + "\n Plant type: %s"
                        + "\n Plant fruits: %s"
                        + "\n "
                        + "\n Active flag (Human): %s"
                        + "\n Active flag (Plant): %s",
                UcfCoder.decodeLandscapeType(cellData),
                UcfCoder.decodeHumanType(cellData),
                XFormatter.formatDate(UcfCoder.decodeHumanAge(cellData)),
                UcfCoder.decodeHumanEnergy(cellData),
                UcfCoder.decodeHumanSatiety(cellData),
                XFormatter.formatDate(UcfCoder.decodeHumanPregnancy(cellData)),
                UcfCoder.decodePlantType(cellData),
                UcfCoder.decodePlantFruits(cellData),
                UcfCoder.decodeActiveFlagHuman(cellData),
                UcfCoder.decodeActiveFlagPlant(cellData));
        baseElement.setText(XFormatter.formatRaw(cellData) + "\n\n" + info);
    }

    public void reset() {
        baseElement.setText("-");
    }

    private void setupInfo() {
    }
}
