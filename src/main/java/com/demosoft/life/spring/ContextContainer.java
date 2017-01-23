package com.demosoft.life.spring;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.demosoft.life.LifeImitationApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by andrii_korkoshko on 23.01.17.
 */
@Component
public class ContextContainer {

    @Autowired
    public OrthographicCamera camera;

    @Autowired
    public Viewport viewport;

    public LifeImitationApplication game;
}
