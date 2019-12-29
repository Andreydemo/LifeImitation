package com.demosoft.life.scene.base;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ResizeDragListener extends DragListener {

    private final Actor mainActor;
    private Map<String, Point> start = new HashMap<>();
    private boolean startedXUp = false;
    private boolean startedXDown = false;
    private boolean startedYUp = false;
    private boolean startedYDown = false;

    public ResizeDragListener(Actor actor) {
        this.mainActor = actor;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        log.info("touchDown {} {} {} {} {}", event, x, y, pointer, button);
        boolean b = super.touchDown(event, x, y, pointer, button);
        if (x < (mainActor.getWidth() * 0.1)) {
            start.put(mainActor.getName(), new Point(mainActor.getX(), mainActor.getY()));
            startedXUp = true;
        }

        if (y < (mainActor.getHeight() * 0.1)) {
            start.put(mainActor.getName(), new Point(mainActor.getX(), mainActor.getY()));
            startedYUp = true;
        }

        if (x > (mainActor.getWidth() * 0.9)) {
            start.put(mainActor.getName(), new Point(mainActor.getX(), mainActor.getY()));
            startedXDown = true;
        }

        if (y > (mainActor.getHeight() * 0.9)) {
            start.put(mainActor.getName(), new Point(mainActor.getX(), mainActor.getY()));
            startedYDown = true;
        }
        return b;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        startedXUp = false;
        startedXDown = false;
        startedYUp = false;
        startedYDown = false;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        super.touchDragged(event, x, y, pointer);
        log.info("touchDragged {} {} {} {}", event, x, y, pointer);

        if (x < (mainActor.getWidth() * 0.1) && startedXUp) {
            mainActor.setWidth(getWidthDiff(event, mainActor));
        }

        if (y < (mainActor.getHeight() * 0.1) && startedYUp) {
            mainActor.setHeight(getHeightDiff(event, mainActor));
        }

        if (x > (mainActor.getWidth() * 0.9) && startedXDown) {
            mainActor.setWidth(getWidthDiff(event, mainActor));
        }

        if (y > (mainActor.getHeight() * 0.9) && startedYDown) {
            mainActor.setHeight(getHeightDiff(event, mainActor));
        }

    }

    private float getHeightDiff(InputEvent event, Actor actor) {

        return start.get(actor.getName()).getY() - (getStageTouchDownY() - event.getStageY());
    }

    private float getWidthDiff(InputEvent event, Actor actor) {
        return start.get(actor.getName()).getX() - (getStageTouchDownX() - event.getStageX());
    }


}
