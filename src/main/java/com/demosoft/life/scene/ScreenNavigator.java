package com.demosoft.life.scene;

import com.demosoft.life.scene.loading.LoadingScreen;
import com.demosoft.life.scene.main.MainScreen;
import com.demosoft.life.spring.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by andrii_korkoshko on 23.01.17.
 */
@Component
public class ScreenNavigator {

    @Autowired
    private ContextContainer contex;

    @Autowired
    public LoadingScreen loadingScreen;

    @Autowired
    public MainScreen mainScreen;

    public void setScreen(BaseScene screen){
        contex.game.setScreen(screen);
    }
}
