package com.demosoft.life.scene.main;

import com.demosoft.life.imitation.entity.graphic.GraphicCell;
import com.demosoft.life.imitation.entity.impl.UcfCoder;
import com.demosoft.life.imitation.entity.type.HumanType;
import com.demosoft.life.imitation.entity.type.LandscapeType;
import com.demosoft.life.imitation.entity.type.PlantType;

import java.util.Optional;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public class CellRenderer {

    public final static int COMMON_COLOR_EMPTY = 0xF0F0F0FF;
    public final static int COMMON_COLOR_UNDEFINED = 0xC22E3500;
    public final static int HUMAN_TYPE_COLOR_MAN = 0x1D8EA3FF;
    public final static int HUMAN_TYPE_COLOR_WOMAN = 0xB82C8FFF;

    public static int getBackgroundColor(long cellData) {
        int color = COMMON_COLOR_EMPTY;
        int landscapeType = UcfCoder.decodeLandscapeType(cellData);
        LandscapeType landscape = LandscapeType.getByValue(landscapeType);
        if (landscape != null) {
            color = landscape.getColor();
        } else {
            color = COMMON_COLOR_UNDEFINED;
        }
        return color;
    }

    public static Optional<Integer> getForegroundColor(long cellData) {
        Integer color = COMMON_COLOR_EMPTY;
        int humanType = UcfCoder.decodeHumanType(cellData);
        int plantType = UcfCoder.decodePlantType(cellData);
        if (humanType != HumanType.HUMAN_TYPE_EMPTY.getValue()) {
            if (humanType == HumanType.HUMAN_TYPE_MAN.getValue()) {
                color = HUMAN_TYPE_COLOR_MAN;
            } else if (humanType == HumanType.HUMAN_TYPE_WOMAN.getValue()) {
                color = HUMAN_TYPE_COLOR_WOMAN;
            } else {
                color = COMMON_COLOR_UNDEFINED;
            }
        } else if (plantType != PlantType.PLANT_TYPE_EMPTY.getValue()) {

            color = PlantType.getByValue(plantType).getColor();
        } else {
            return Optional.empty();
        }
        return Optional.of(color);
    }

    public static Optional<Integer> getForegroundColor(GraphicCell cellData) {
        Integer color = COMMON_COLOR_EMPTY;
        int humanType = cellData.getHuman().getType().getValue();
        int plantType = cellData.getPlant().getType().getValue();
        if (humanType != HumanType.HUMAN_TYPE_EMPTY.getValue()) {
            if (humanType == HumanType.HUMAN_TYPE_MAN.getValue()) {
                color = HUMAN_TYPE_COLOR_MAN;
            } else if (humanType == HumanType.HUMAN_TYPE_WOMAN.getValue()) {
                color = HUMAN_TYPE_COLOR_WOMAN;
            } else {
                color = COMMON_COLOR_UNDEFINED;
            }
        } else if (plantType != PlantType.PLANT_TYPE_EMPTY.getValue()) {

            color = PlantType.getByValue(plantType).getColor();
        } else {
            return Optional.empty();
        }
        return Optional.of(color);
    }

}
