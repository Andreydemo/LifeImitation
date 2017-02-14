package com.demosoft.life.imitation.entity.type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Korkoshko on 2/13/2017.
 */
public enum Landscape {

    LANDSCAPE_TYPE_EMPTY(0x0, "Empty",0xF0F0F0FF),
    LANDSCAPE_TYPE_WATER_LOW(0x1, "Water low",0x60A4B1FF),
    LANDSCAPE_TYPE_WATER_HIGH(0x2, "Water high",0x60A4B1FF),
    LANDSCAPE_TYPE_SAND_LOW(0x3, "Sand_low",0xd8bd6aFF),
    LANDSCAPE_TYPE_SAND_HIGH(0x4, "Sand high",0xfcd046FF),
    LANDSCAPE_TYPE_GROUND_LOW(0x5, "Ground_low",0xDDB985FF),
    LANDSCAPE_TYPE_GROUND_HIGH(0x6, "Ground high",0xD1AF7DFF),
    LANDSCAPE_TYPE_GRASS_LOW(0x7, "Grass low",0xB3D77EFF),
    LANDSCAPE_TYPE_GRASS_HIGH(0x8, "Grass high",0xA8C976FF),
    LANDSCAPE_TYPE_ROCK_LOW(0x9, "Rock low",0xAAA49DFF),
    LANDSCAPE_TYPE_ROCK_HIGH(0xA, "Rock high",0x958D85FF),
    LANDSCAPE_TYPE_SNOW(0xB, "Snow high",0xFFFAFAFF),
    LANDSCAPE_TYPE_ICE(0xC, "Ice high",0xBFEFFFFF);


    private int value;
    private int color;
    private String message;

    public String getMessage() {
        return message;
    }

    public int getValue() {
        return value;
    }

    public int getColor() {
        return color;
    }

    Landscape(int value, String message, int color) {
        this.value = value;
        this.message = message;
        this.color = color;
    }

    public static Landscape getByValue(int value) {
        List<Landscape> collect = Arrays.stream(Landscape.values()).filter(it -> it.getValue() == value).collect(Collectors.toList());
        return collect.size() > 0 ? collect.get(0) : LANDSCAPE_TYPE_EMPTY;
    }

    public final static long LANDSCAPE_TYPE_MASK = 0x0000_0000_0000_000FL;
    public final static int LANDSCAPE_TYPE_SHIFT = 0;
    public final static int LANDSCAPE_MAX_VALUE= LANDSCAPE_TYPE_ICE.value;
}
