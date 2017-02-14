package com.demosoft.life.scene.main;

import com.demosoft.life.imitation.entity.UcfCoder;
import com.demosoft.life.imitation.entity.type.Human;
import com.demosoft.life.imitation.entity.type.Landscape;
import com.demosoft.life.imitation.entity.type.Plant;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public class CellRenderer {

    public final static int COMMON_COLOR_EMPTY = 0xF0F0F0FF;
    public final static int COMMON_COLOR_UNDEFINED = 0xC22E3500;
    public final static int LANDSCAPE_TYPE_COLOR_WATER_LOW = 0x60A4B1FF;
    public final static int LANDSCAPE_TYPE_COLOR_WATER_HIGH = 0x6CB8C6FF;
    public final static int LANDSCAPE_TYPE_COLOR_GROUND_LOW = 0xDDB985FF;
    public final static int LANDSCAPE_TYPE_COLOR_GROUND_HIGH = 0xD1AF7DFF;
    public final static int LANDSCAPE_TYPE_COLOR_GRASS_LOW = 0xB3D77EFF;
    public final static int LANDSCAPE_TYPE_COLOR_GRASS_HIGH = 0xA8C976FF;
    public final static int HUMAN_TYPE_COLOR_MAN = 0x1D8EA3FF;
    public final static int HUMAN_TYPE_COLOR_WOMAN = 0xB82C8FFF;
    public final static int TREE_TYPE_COLOR_APPLE = 0x62B122FF;

    public static int getBackgroundColor(long cellData) {
        int color = COMMON_COLOR_EMPTY;
        int landscapeType = UcfCoder.decodeLandscapeType(cellData);
        Landscape landscape = Landscape.getByValue(landscapeType);
        if (landscape != null) {
            color = landscape.getColor();
        } else {
            color = COMMON_COLOR_UNDEFINED;
        }
        return color;
    }

    public static int getForegroundColor(long cellData) {
        int color = COMMON_COLOR_EMPTY;
        int humanType = UcfCoder.decodeHumanType(cellData);
        int plantType = UcfCoder.decodePlantType(cellData);
        if (humanType != Human.HUMAN_TYPE_EMPTY.getValue()) {
            if (humanType == Human.HUMAN_TYPE_MAN.getValue()) {
                color = HUMAN_TYPE_COLOR_MAN;
            } else if (humanType == Human.HUMAN_TYPE_WOMAN.getValue()) {
                color = HUMAN_TYPE_COLOR_WOMAN;
            } else {
                color = COMMON_COLOR_UNDEFINED;
            }
        } else if (plantType != Plant.PLANT_TYPE_EMPTY.getValue()) {
            if (plantType == Plant.PLANT_TYPE_APPLE.getValue()) {
                color = TREE_TYPE_COLOR_APPLE;
            } else {
                color = COMMON_COLOR_UNDEFINED;
            }
        } else {
            color = COMMON_COLOR_UNDEFINED;
        }
        return color;
    }

}
