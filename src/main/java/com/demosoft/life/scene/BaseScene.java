package com.demosoft.life.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by andrii_korkoshko on 23.01.17.
 */
public class BaseScene extends ScreenAdapter {
    private boolean keyHandled;
    public BaseScene() {
        keyHandled=false;
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            if(keyHandled){
                return;
            }
            handleBackPress();
            keyHandled=true;
        }else{
            keyHandled=false;
        }
    }
    protected void handleBackPress() {
    }

}
