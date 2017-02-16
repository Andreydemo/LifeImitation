package com.demosoft.life.imitation.entity.type;

import com.demosoft.life.imitation.entity.impl.UcfCoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Korkoshko on 2/13/2017.
 */
public enum LandscapeType {

    LANDSCAPE_TYPE_EMPTY(0x0, "Empty", 0xF0F0F0FF, true, true),
    LANDSCAPE_TYPE_WATER_LOW(0x1, "Water low", 0x60A4B1FF, true, false),
    LANDSCAPE_TYPE_WATER_HIGH(0x2, "Water high", 0x60A4B1FF, true, false),
    LANDSCAPE_TYPE_SAND_LOW(0x3, "Sand_low", 0xd8bd6aFF, false, false),
    LANDSCAPE_TYPE_SAND_HIGH(0x4, "Sand high", 0xfcd046FF, false, false),
    LANDSCAPE_TYPE_GROUND_LOW(0x5, "Ground_low", 0xDDB985FF, false, false),
    LANDSCAPE_TYPE_GROUND_MEDIUM(0x6, "Ground medium", 0xD1AF7DFF, false, false),
    LANDSCAPE_TYPE_GROUND_HIGH(0x7, "Ground high", 0xC3A475FF, false, false),
    LANDSCAPE_TYPE_GRASS_LOW(0x8, "Grass low", 0xB3D77EFF, false, false),
    LANDSCAPE_TYPE_GRASS_MEDIUM(0x9, "Grass medium", 0xA8C976FF, false, false),
    LANDSCAPE_TYPE_GRASS_HIGH(0xA, "Grass high", 0x8CB84AFF, false, false),
    LANDSCAPE_TYPE_ROCK_LOW(0xB, "Rock low", 0xAAA49DFF, false, false),
    LANDSCAPE_TYPE_ROCK_MEDIUM(0xC, "Rock medium", 0x958D85FF, false, true),
    LANDSCAPE_TYPE_ROCK_HIGH(0xD, "Rock high", 0x89827BFF, false, true),
    LANDSCAPE_TYPE_SNOW(0xE, "Snow high", 0xFFFAFAFF, false, true),
    LANDSCAPE_TYPE_ICE(0xF, "Ice high", 0xBFEFFFFF, false, true);


    private int value;
    private int color;
    private boolean watterBlock;
    private boolean rockBlock;
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

    public boolean isRockBlock() {
        return rockBlock;
    }

    public boolean isWatterBlock() {
        return watterBlock;
    }

    LandscapeType(int value, String message, int color, boolean watterBlock, boolean rockBlock) {
        this.value = value;
        this.message = message;
        this.color = color;
        this.watterBlock = watterBlock;
        this.rockBlock = rockBlock;
    }

    public static LandscapeType getByValue(int value) {
        List<LandscapeType> collect = Arrays.stream(LandscapeType.values()).filter(it -> it.getValue() == value).collect(Collectors.toList());
        return collect.size() > 0 ? collect.get(0) : LANDSCAPE_TYPE_EMPTY;
    }

    public static LandscapeType decodeAndGetByValue(long value) {
       return getByValue(UcfCoder.decodeLandscapeType(value));
    }

    public final static long LANDSCAPE_TYPE_MASK = 0x0000_0000_0000_000FL;
    public final static int LANDSCAPE_TYPE_SHIFT = 0;
    public final static int LANDSCAPE_MAX_VALUE = LANDSCAPE_TYPE_ICE.value;
}
