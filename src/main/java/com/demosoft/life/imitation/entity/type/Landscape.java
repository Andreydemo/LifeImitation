package com.demosoft.life.imitation.entity.type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Korkoshko on 2/13/2017.
 */
public enum Landscape {

    LANDSCAPE_TYPE_EMPTY(0x0, "Empty"),
    LANDSCAPE_TYPE_WATER_LOW(0x1, "Water low"),
    LANDSCAPE_TYPE_WATER_HIGH(0x2, "Water high"),
    LANDSCAPE_TYPE_GROUND_LOW(0x3, "Ground_low"),
    LANDSCAPE_TYPE_GROUND_HIGH(0x4, "Ground high"),
    LANDSCAPE_TYPE_GRASS_LOW(0x5, "Grass low"),
    LANDSCAPE_TYPE_GRASS_HIGH(0x6, "Grass high");

    private int value;
    private String message;

    public String getMessage() {
        return message;
    }

    public int getValue() {
        return value;
    }

    Landscape(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public static Landscape getByValue(int value) {
        List<Landscape> collect = Arrays.stream(Landscape.values()).filter(it -> it.getValue() == value).collect(Collectors.toList());
        return collect.size() > 0 ? collect.get(0) : LANDSCAPE_TYPE_EMPTY;
    }

    public final static long LANDSCAPE_TYPE_MASK = 0x0000_0000_0000_0007L;
    public final static int LANDSCAPE_TYPE_SHIFT = 0;
}
