package com.demosoft.life.scene.main.info;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.demosoft.life.assets.AssetsLoader;
import com.demosoft.life.logic.statistic.Statistic;
import com.demosoft.life.scene.format.XFormatter;
import com.demosoft.life.scene.main.MainScreen;
import com.demosoft.life.spring.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Andrii_Korkoshko on 2/13/2017.
 */
@Component
public class MapInfoPanel {

    private TextArea baseElement;
    private Label label;

    @Autowired
    private AssetsLoader assetsLoader;

    @Autowired
    private ContextContainer context;


    @PostConstruct
    void init() {
        Skin skin = assetsLoader.getSkin(MainScreen.UISKIN);
        baseElement = new TextArea("mapInfoPanel", skin);
        baseElement.setBounds(25, context.translateY(435), 500, 400);
        baseElement.setDisabled(true);

        label = new Label("MapImpl Info", skin);
        label.setBounds(25, context.translateY(35), 300, 25);
    }

    public void reset() {
        baseElement.clear();
    }

    public final void update(int days) {
        String info = String.format("Date: %s"
                        + "\n "
                        + "\n Common area: %s"
                        + "\n       ┗ Water area: %s"
                        + "\n       ┗ Land area: %s"
                        + "\n People: %s"
                        + "\n       ┗ Men: %s"
                        + "\n       ┗ Women: %s"
                        + "\n                      ┗ Pregnant: %s"
                        + "\n Children were born: %s"
                        + "\n Children died: %s"
                        + "\n People died: %s"
                        + "\n       ┗ Low Energy: %s"
                        + "\n       ┗ Low Satiety: %s"
                        + "\n       ┗ Age: %s"
                        + "\n       ┗ Lost: %s"
                        + "\n People Density (Land): %.2f"
                        + "\n People Age Mean: %s"
                        + "\n Plants: %s"
                        + "\n Plants Fruits: %s"
                        + "\n Plants Density (Land): %.2f"
                        + "\n Plants Fruits Mean: %.2f"
                        + "\n Plants Fruits / People Ratio: %.2f",
                XFormatter.formatDateShort(days),
                Statistic.CELLS,
                Statistic.CELLS_WATER,
                Statistic.CELLS_LAND,
                Statistic.people,
                Statistic.peopleMen,
                Statistic.peopleWomen,
                Statistic.peopleWomenPregnant,
                Statistic.childrenWereBorn,
                Statistic.childrenDied,
                Statistic.peopleDied,
                Statistic.peopleDiedByEnergy,
                Statistic.peopleDiedBySatiety,
                Statistic.peopleDiedByAge,
                Statistic.peopleDiedByLost,
                Statistic.getPeopleLandDensity(),
                XFormatter.formatDate((int) Statistic.getPeopleAgeMean()),
                Statistic.plants,
                Statistic.plantsFruits,
                Statistic.getPlantsLandDensity(),
                Statistic.getPlantsFruitsMean(),
                Statistic.getPlantsFruitsPeopleRatio());
        try {

            this.baseElement.setText(info);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    public void addToStage(Stage stage) {
        stage.addActor(label);
        stage.addActor(baseElement);
    }

    private void setupInfo() {
    }
}
