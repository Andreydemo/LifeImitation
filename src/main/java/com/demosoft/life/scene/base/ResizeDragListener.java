package com.demosoft.life.scene.base;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ResizeDragListener extends DragListener {

    private final Actor mainActor;
    private Map<String, Point> startSize = new HashMap<>();
    private Map<String, Point> startPosition = new HashMap<>();
    private boolean startedXUp = false;
    private boolean startedXDown = false;
    private boolean startedYUp = false;
    private boolean startedYDown = false;
    private Map<CornerLink.Corner, List<CornerLink>> linkedActors;
    private CornerLink[] cornerLinks;

    public ResizeDragListener(Actor actor, CornerLink... cornerLinks) {
        this.mainActor = actor;
        this.cornerLinks = cornerLinks;
        if (cornerLinks != null) {
            linkedActors = Arrays.stream(cornerLinks).collect(Collectors.groupingBy(CornerLink::getCorner));
        }
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        boolean b = super.touchDown(event, x, y, pointer, button);
        if (x < (mainActor.getWidth() * 0.1)) {
            log.info("touchDown startedXUp {} {} {} {} {}", event, x, y, pointer, button);
            startSize.put(mainActor.getName(), new Point(mainActor.getWidth(), mainActor.getHeight()));
            startPosition.put(mainActor.getName(), new Point(mainActor.getX(), mainActor.getY()));
            startedXUp = true;
        }

        if (y < (mainActor.getHeight() * 0.1)) {
            log.info("touchDown startedYUp {} {} {} {} {}", event, x, y, pointer, button);
            startSize.put(mainActor.getName(), new Point(mainActor.getWidth(), mainActor.getHeight()));
            startPosition.put(mainActor.getName(), new Point(mainActor.getX(), mainActor.getY()));
            startedYUp = true;
        }

        if (x > (mainActor.getWidth() * 0.9)) {
            log.info("touchDown startedXDown {} {} {} {} {}", event, x, y, pointer, button);
            startSize.put(mainActor.getName(), new Point(mainActor.getWidth(), mainActor.getHeight()));
            startPosition.put(mainActor.getName(), new Point(mainActor.getX(), mainActor.getY()));
            startedXDown = true;
        }

        if (y > (mainActor.getHeight() * 0.9)) {
            log.info("touchDown startedYDown {} {} {} {} {}", event, x, y, pointer, button);
            startSize.put(mainActor.getName(), new Point(mainActor.getWidth(), mainActor.getHeight()));
            startPosition.put(mainActor.getName(), new Point(mainActor.getX(), mainActor.getY()));
            startedYDown = true;
        }

        if(cornerLinks != null){
            for (CornerLink cornerLink : cornerLinks) {
                Actor actor = cornerLink.getActor();
                startPosition.put(actor.getName(), new Point(actor.getX(), actor.getY()));
            }
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


        if (x < (mainActor.getWidth() * 0.1) && startedXUp) {
            log.info("touchDragged XUp {} {} {} {}", event, x, y, pointer);
            mainActor.setWidth(getWidthDiffUp(event, mainActor));
            mainActor.setX(getXDiff(event, mainActor));
            if(linkedActors != null){
                List<CornerLink> downY = linkedActors.getOrDefault(CornerLink.Corner.UP_X_DOWN_Y, Collections.emptyList());
                List<CornerLink> upY = linkedActors.getOrDefault(CornerLink.Corner.UP_X_UP_Y, Collections.emptyList());
                downY.forEach(cornerLink -> cornerLink.getActor().setX(getXDiff(event, cornerLink.getActor())));
                upY.forEach(cornerLink -> cornerLink.getActor().setX(getXDiff(event, cornerLink.getActor())));
            }
        }

        if (y < (mainActor.getHeight() * 0.1) && startedYUp) {
            log.info("touchDragged YUp {} {} {} {}", event, x, y, pointer);
            mainActor.setHeight(getHeightDiffUp(event, mainActor));
            mainActor.setY(getYDiff(event, mainActor));
        }

        if (x > (mainActor.getWidth() * 0.9) && startedXDown) {
            log.info("touchDragged XDown {} {} {} {}", event, x, y, pointer);
            mainActor.setWidth(getWidthDiffDown(event, mainActor));
        }

        if (y > (mainActor.getHeight() * 0.9) && startedYDown) {
            log.info("touchDragged YDown {} {} {} {}", event, x, y, pointer);
            mainActor.setHeight(getHeightDiffDown(event, mainActor));
            if(linkedActors != null){
                List<CornerLink> upX = linkedActors.getOrDefault(CornerLink.Corner.UP_X_DOWN_Y, Collections.emptyList());
                List<CornerLink> downX = linkedActors.getOrDefault(CornerLink.Corner.DOWN_X_DOWN_Y, Collections.emptyList());
                upX.forEach(cornerLink -> cornerLink.getActor().setY(getYDiff(event, cornerLink.getActor())));
                downX.forEach(cornerLink -> cornerLink.getActor().setY(getYDiff(event, cornerLink.getActor())));
            }
        }

    }

    private float getHeightDiffDown(InputEvent event, Actor actor) {
        return startSize.get(actor.getName()).getY() - (getStageTouchDownY() - event.getStageY());
    }

    private float getHeightDiffUp(InputEvent event, Actor actor) {
        return startSize.get(actor.getName()).getY() + (getStageTouchDownY() - event.getStageY());
    }

    private float getWidthDiffDown(InputEvent event, Actor actor) {
        return startSize.get(actor.getName()).getX() - (getStageTouchDownX() - event.getStageX());
    }

    private float getWidthDiffUp(InputEvent event, Actor actor) {
        return startSize.get(actor.getName()).getX() + (getStageTouchDownX() - event.getStageX());
    }

    private float getYDiff(InputEvent event, Actor actor) {
        return startPosition.get(actor.getName()).getY() - (getStageTouchDownY() - event.getStageY());
    }

    private float getXDiff(InputEvent event, Actor actor) {
        return startPosition.get(actor.getName()).getX() - (getStageTouchDownX() - event.getStageX());
    }


}
