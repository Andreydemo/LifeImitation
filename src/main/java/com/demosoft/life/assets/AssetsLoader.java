package com.demosoft.life.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Andrii_Korkoshko on 1/23/2017.
 */
@Component
public class AssetsLoader {

    @Autowired
    private AssetManager assetManager;

    public static final String IMAGES_PROPERTIES = "images.properties";
    public static final String PACKS_PROPERTIES = "packs.properties";
    public static final String SKINS_PROPERTIES = "skins.properties";

    public static final String PACK_PREFIX = "pack-";
    public static final String IMAGE_PREFIX = "image-";
    public static final String ANIMATION_PREFIX = "animation-";

    private static Map<String, TextureRegion> stringImageMap = new HashMap<>();
    private static Map<String, Array<TextureAtlas.AtlasRegion>> stringAnimationMap = new HashMap<>();
    private static Map<String, Skin> stringSkinMap = new HashMap<>();

    private static boolean loaded = false;

    @PostConstruct
    void init() {
        if (!loaded) {
            loadPacks();
            loadImages();
            loadSkins();
            System.out.println("resources loaded");
        }
    }

    private void loadPacks() {
        Properties prop = loadProperties(PACKS_PROPERTIES);

        loadPacks(prop);
        Map<String, TextureAtlas> packs = retrivePacks(prop);
        for (Map.Entry<String, TextureAtlas> entry : packs.entrySet()) {
            processPackImages(prop, entry);
            processPackAnimations(prop, entry);
        }
    }

    private void processPackAnimations(Properties prop, Map.Entry<String, TextureAtlas> entry) {
        if (prop.containsKey(ANIMATION_PREFIX + entry.getKey())) {
            String[] values = ((String) prop.get(ANIMATION_PREFIX + entry.getKey())).split(",");
            for (String value : values) {
                String imageKey = entry.getKey() + "/" + value;
                stringAnimationMap.put(imageKey, entry.getValue().findRegions(value));
            }
        }
    }

    private void processPackImages(Properties prop, Map.Entry<String, TextureAtlas> entry) {
        if (prop.containsKey(IMAGE_PREFIX + entry.getKey())) {
            String[] values = ((String) prop.get(IMAGE_PREFIX + entry.getKey())).split(",");
            for (String value : values) {
                String imageKey = entry.getKey() + "/" + value;
                stringImageMap.put(imageKey, entry.getValue().findRegion(value));
            }
        }
    }

    private Map<String, TextureAtlas> retrivePacks(Properties prop) {
        Map<String, TextureAtlas> packs = new HashMap<>();
        for (Map.Entry<Object, Object> entry : prop.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.startsWith(PACK_PREFIX)) {

                packs.put(key.replaceFirst(PACK_PREFIX, ""), assetManager.get(value, TextureAtlas.class));
            }
        }
        return packs;
    }

    private void loadPacks(Properties prop) {
        for (Map.Entry<Object, Object> entry : prop.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.startsWith(PACK_PREFIX)) {
                assetManager.load(value, TextureAtlas.class);
            }
        }
        assetManager.finishLoading();
    }

    private void loadImages() {
        Properties prop = loadProperties(IMAGES_PROPERTIES);
        for (Map.Entry<Object, Object> entry : prop.entrySet()) {
            TextureRegion image = new TextureRegion(new Texture(Gdx.files.internal((String) entry.getValue())));
            stringImageMap.put((String) entry.getKey(), image);
        }
    }

    private void loadSkins() {
        Properties prop = loadProperties(SKINS_PROPERTIES);
        for (Map.Entry<Object, Object> entry : prop.entrySet()) {
            Skin skin = new Skin(Gdx.files.internal((String) entry.getValue()));
            stringSkinMap.put((String) entry.getKey(), skin);
        }
    }


    private Properties loadProperties(String path) {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = Gdx.files.internal(path).read();
            prop.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public Image getImage(String resourceName) {
        return new Image(stringImageMap.get(resourceName));
    }

    public Skin getSkin(String resourceName) {
        return stringSkinMap.get(resourceName);
    }

    public Image getImage(String resourceName, boolean yFlip) {
        TextureRegion textureRegion = stringImageMap.get(resourceName);
        textureRegion.flip(false, yFlip);
        return new Image(textureRegion);
    }

    public Animation getAnimation(String resourceName, float frameDuration) {
        return new Animation(frameDuration, stringAnimationMap.get(resourceName));
    }
}
