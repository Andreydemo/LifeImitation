package com.demosoft.life;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Andrii_Korkoshko on 2/24/2017.
 */
public class Utils {

    public static Vector2 getImageCentre(Image image) {
        return new Vector2((float) (image.getX() + image.getWidth() / 2), (float) (image.getY() + image.getHeight() / 2));
    }

    public static void foolowToCamera(Image image, Camera camera) {
        Vector2 imageCentre = getImageCentre(image);
        Vector2 diff = new Vector2(camera.position.x - imageCentre.x, camera.position.y - imageCentre.y);
        image.setX(image.getX() + diff.x);
        image.setY(image.getY() + diff.y);

    }
}
