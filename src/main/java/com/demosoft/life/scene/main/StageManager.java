package com.demosoft.life.scene.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class StageManager implements StageHandler {

    public static final Comparator<BaseStageHandler> STAGE_HANDLER_COMPARATOR = new Comparator<BaseStageHandler>() {
        @Override
        public int compare(BaseStageHandler o1, BaseStageHandler o2) {
            int i1 = o1.getInputOrderNumber() == null ? -1 : o1.getInputOrderNumber();
            int i2 = o2.getInputOrderNumber() == null ? -1 : o2.getInputOrderNumber();
            return i2 - i1;
        }
    };
    @Autowired
    private List<BaseStageHandler> stageHandlers;

    @PostConstruct
    void init() {
        Collections.sort(stageHandlers, STAGE_HANDLER_COMPARATOR);
    }

    @Override
    public void show() {
        stageHandlers.forEach(StageHandler::show);
    }

    @Override
    public void hide() {
        stageHandlers.forEach(StageHandler::hide);
    }

    @Override
    public void render() {
        stageHandlers.forEach(StageHandler::render);
    }

    @Override
    public void unfocusAll() {
        stageHandlers.forEach(StageHandler::unfocusAll);
    }
}
