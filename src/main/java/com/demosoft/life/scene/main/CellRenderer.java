package com.demosoft.life.scene.main;

import com.demosoft.life.imitation.entity.UcfCoder;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public class CellRenderer {

    private final static int COMMON_COLOR_EMPTY = 0xF0F0F0FF;
    private final static int COMMON_COLOR_UNDEFINED = 0xC22E35FF;
    private final static int LANDSCAPE_TYPE_COLOR_WATER_LOW = 0x60A4B1FF;
    private final static int LANDSCAPE_TYPE_COLOR_WATER_HIGH = 0x6CB8C6FF;
    private final static int LANDSCAPE_TYPE_COLOR_GROUND_LOW = 0xDDB985FF;
    private final static int LANDSCAPE_TYPE_COLOR_GROUND_HIGH = 0xD1AF7DFF;
    private final static int LANDSCAPE_TYPE_COLOR_GRASS_LOW = 0xB3D77EFF;
    private final static int LANDSCAPE_TYPE_COLOR_GRASS_HIGH = 0xA8C976FF;
    private final static int HUMAN_TYPE_COLOR_MAN = 0x1D8EA3FF;
    private final static int HUMAN_TYPE_COLOR_WOMAN = 0xB82C8FFF;
    private final static int TREE_TYPE_COLOR_X = 0x22B14CFF;

    public static int getBackgroundColor(long cellData) {
        int color = COMMON_COLOR_EMPTY;
        int landscapeType = UcfCoder.decodeLandscapeType(cellData);
        switch (landscapeType) {
            case UcfCoder.LANDSCAPE_TYPE_WATER_LOW: {
                color = LANDSCAPE_TYPE_COLOR_WATER_LOW;
                break;
            }
            case UcfCoder.LANDSCAPE_TYPE_WATER_HIGH: {
                color = LANDSCAPE_TYPE_COLOR_WATER_HIGH;
                break;
            }
            case UcfCoder.LANDSCAPE_TYPE_GROUND_LOW: {
                color = LANDSCAPE_TYPE_COLOR_GROUND_LOW;
                break;
            }
            case UcfCoder.LANDSCAPE_TYPE_GROUND_HIGH: {
                color = LANDSCAPE_TYPE_COLOR_GROUND_HIGH;
                break;
            }
            case UcfCoder.LANDSCAPE_TYPE_GRASS_LOW: {
                color = LANDSCAPE_TYPE_COLOR_GRASS_LOW;
                break;
            }
            case UcfCoder.LANDSCAPE_TYPE_GRASS_HIGH: {
                color = LANDSCAPE_TYPE_COLOR_GRASS_HIGH;
                break;
            }
            default: {
                color = COMMON_COLOR_UNDEFINED;
                break;
            }
        }
        return color;
    }

    public static int getForegroundColor(long cellData) {
        int color = COMMON_COLOR_EMPTY;

        int humanType = UcfCoder.decodeHumanType(cellData);
        int plantType = UcfCoder.decodePlantType(cellData);

        if (humanType == UcfCoder.HUMAN_TYPE_EMPTY || plantType == UcfCoder.PLANT_TYPE_EMPTY) {
            if (humanType != UcfCoder.HUMAN_TYPE_EMPTY) {
                switch (humanType) {
                    case UcfCoder.HUMAN_TYPE_MAN: {
                        color = HUMAN_TYPE_COLOR_MAN;
                        break;
                    }
                    case UcfCoder.HUMAN_TYPE_WOMAN: {
                        color = HUMAN_TYPE_COLOR_WOMAN;
                        break;
                    }
                    default: {
                        color = COMMON_COLOR_UNDEFINED;
                        break;
                    }
                }
            } else if (plantType != UcfCoder.PLANT_TYPE_EMPTY) {
                switch (plantType) {
                    case UcfCoder.PLANT_TYPE_X: {
                        color = TREE_TYPE_COLOR_X;
                        break;
                    }
                    default: {
                        color = COMMON_COLOR_UNDEFINED;
                        break;
                    }
                }
            }
        } else {
            color = COMMON_COLOR_UNDEFINED;
        }
        return color;
    }

    /**
     * ?????
     * @param cellData
     * @return
     */
    public static char getUnit(long cellData) {
        char unit = ' ';
        int humanType = UcfCoder.decodeHumanType(cellData);
        int plantType = UcfCoder.decodePlantType(cellData);
        if (humanType != UcfCoder.HUMAN_TYPE_EMPTY || plantType != UcfCoder.PLANT_TYPE_EMPTY) {
            unit = 'E';
        } else {
            unit = 'F';
        }
        return unit;
    }

}
