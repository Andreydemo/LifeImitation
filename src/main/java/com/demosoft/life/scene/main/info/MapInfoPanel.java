package com.demosoft.life.scene.main.info;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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

        label = new Label("Map Info", skin);
        label.setBounds(25, context.translateY(35), 300, 25);
    }

    public void reset() {
        baseElement.clear();
    }

    public final void update(int days) {
        String info = String.format("<html>Date: %s"
                        + "<br> "
                        + "<br> Common area: %s"
                        + "<br> &nbsp ┗ Water area: %s"
                        + "<br> &nbsp ┗ Land area: %s"
                        + "<br> People: %s"
                        + "<br> &nbsp ┗ Men: %s"
                        + "<br> &nbsp ┗ Women: %s"
                        + "<br> &nbsp&nbsp&nbsp&nbsp ┗ Pregnant: %s"
                        + "<br> Children were born: %s"
                        + "<br> Children died: %s"
                        + "<br> People died: %s"
                        + "<br> &nbsp ┗ Low Energy: %s"
                        + "<br> &nbsp ┗ Low Satiety: %s"
                        + "<br> &nbsp ┗ Age: %s"
                        + "<br> &nbsp ┗ Lost: %s"
                        + "<br> People Density (Land): %.2f"
                        + "<br> People Age Mean: %s"
                        + "<br> Plants: %s"
                        + "<br> Plants Fruits: %s"
                        + "<br> Plants Density (Land): %.2f"
                        + "<br> Plants Fruits Mean: %.2f"
                        + "<br> Plants Fruits / People Ratio: %.2f</html>",
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
        this.baseElement.setText(info);
    }


    public void addToStage(Stage stage) {
        stage.addActor(label);
        stage.addActor(baseElement);
    }

    private void setupInfo() {
    }
}
