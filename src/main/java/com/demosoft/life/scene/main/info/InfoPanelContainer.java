package com.demosoft.life.scene.main.info;

import com.badlogic.gdx.scenes.scene2d.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Andrii_Korkoshko on 2/13/2017.
 */
@Component
public class InfoPanelContainer {
    @Autowired
    private CellInfoPanel cellInfoPanel;
    @Autowired
    private EventsInfoPanel eventsInfoPanel;
    @Autowired
    private MapInfoPanel mapInfoPanel;
    @Autowired
    private DebugInfoPanel debugInfoPanel;


    public void addToStage(Stage stage) {
        cellInfoPanel.addToStage(stage);
        eventsInfoPanel.addToStage(stage);
        mapInfoPanel.addToStage(stage);
        debugInfoPanel.addToStage(stage);
    }

    public CellInfoPanel getCellInfoPanel() {
        return cellInfoPanel;
    }

    public EventsInfoPanel getEventsInfoPanel() {
        return eventsInfoPanel;
    }

    public MapInfoPanel getMapInfoPanel() {
        return mapInfoPanel;
    }

    public DebugInfoPanel getDebugInfoPanel() {
        return debugInfoPanel;
    }
}
