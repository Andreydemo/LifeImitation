package com.demosoft.life.scene.base;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MoveDragListener extends DragListener {

    private final Actor mainActor;
    private Actor[] actors;
    private Map<String, Point> start = new HashMap<>();
    private boolean started = false;

    public MoveDragListener(Actor actor, Actor... actors) {
        this.mainActor = actor;
        this.actors = actors;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        log.info("touchDown {} {} {} {} {}", event, x, y, pointer, button);
        boolean b = super.touchDown(event, x, y, pointer, button);
        if (x > (mainActor.getWidth() * 0.1) && y > (mainActor.getHeight() * 0.1)
                && x < (mainActor.getWidth() * 0.9) && y < (mainActor.getHeight() * 0.9)) {
            started = true;
            start.put(mainActor.getName(), new Point(mainActor.getX(), mainActor.getY()));
            if (actors != null) {
                for (Actor actor : actors) {
                    start.put(actor.getName(), new Point(actor.getX(), actor.getY()));
                }
            }
        }
        return b;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        started = false;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        super.touchDragged(event, x, y, pointer);
        log.info("touchDragged {} {} {} {}", event, x, y, pointer);
        if (x > (mainActor.getWidth() * 0.1) && y > (mainActor.getHeight() * 0.1)
                && x < (mainActor.getWidth() * 0.9) && y < (mainActor.getHeight() * 0.9)
                && started) {
            mainActor.setX(getXDiff(event, mainActor));
            mainActor.setY(getYDiff(event, mainActor));
            if (actors != null) {
                for (Actor actor : actors) {
                    actor.setX(getXDiff(event, actor));
                    actor.setY(getYDiff(event, actor));
                }
            }
        }
    }

    private float getYDiff(InputEvent event, Actor actor) {
        return start.get(actor.getName()).getY() - (getStageTouchDownY() - event.getStageY());
    }

    private float getXDiff(InputEvent event, Actor actor) {
        return start.get(actor.getName()).getX() - (getStageTouchDownX() - event.getStageX());
    }


}
