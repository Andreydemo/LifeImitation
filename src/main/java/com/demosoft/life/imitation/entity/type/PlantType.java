package com.demosoft.life.imitation.entity.type;

import com.demosoft.life.imitation.entity.impl.UcfCoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Korkoshko on 2/13/2017.
 */
public enum PlantType {
    PLANT_TYPE_EMPTY(0x0, "Empty", 0xF0F0F0FF),
    PLANT_TYPE_APPLE(0x1, "Apple", 0x2F6900FF),
    PLANT_TYPE_CHERY(0x2, "Chery", 0x812E00FF),
    PLANT_TYPE_OAK(0x3, "Oak", 0x476B29FF);

    private int value;
    private int color;
    private String message;

    PlantType(int value, String message, int color) {
        this.value = value;
        this.message = message;
        this.color = color;
    }

    public String getMessage() {
        return message;
    }

    public int getValue() {
        return value;
    }

    public int getColor() {
        return color;
    }

    public static PlantType getByValue(int value) {
        List<PlantType> collect = Arrays.stream(PlantType.values()).filter(it -> it.getValue() == value).collect(Collectors.toList());
        return collect.size() > 0 ? collect.get(0) : PLANT_TYPE_EMPTY;
    }

    public static PlantType decodeAndGetByValue(long value) {
        return getByValue(UcfCoder.decodePlantType(value));
    }

    public final static long PLANT_TYPE_MASK_BASE = 0x3;
    public final static int PLANT_TYPE_SHIFT = 42;
    public final static long PLANT_TYPE_MASK = PLANT_TYPE_MASK_BASE << PLANT_TYPE_SHIFT;

    public final static long PLANT_FRUITS_MASK_BASE = 0x3F;
    public final static int PLANT_FRUITS_SHIFT = 44;
    public final static long PLANT_FRUITS_MASK = PLANT_FRUITS_MASK_BASE << PLANT_FRUITS_SHIFT;

    public final static long ACTIVE_FLAG_PLANT_MASK_BASE = 0x1;
    public final static int ACTIVE_FLAG_PLANT_SHIFT = 63;
    public final static long ACTIVE_FLAG_PLANT_MASK = ACTIVE_FLAG_PLANT_MASK_BASE << ACTIVE_FLAG_PLANT_SHIFT;

}
