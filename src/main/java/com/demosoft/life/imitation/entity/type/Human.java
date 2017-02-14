package com.demosoft.life.imitation.entity.type;

import com.demosoft.life.imitation.entity.UcfCoder;
import com.demosoft.life.scene.format.XFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Korkoshko on 2/13/2017.
 */
public enum Human {

    HUMAN_TYPE_EMPTY(0x0, "Empty"),
    HUMAN_TYPE_MAN(0x1, "Man"),
    HUMAN_TYPE_WOMAN(0x2, "Woman");

    private int value;
    private String message;

    Human(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getValue() {
        return value;
    }

    public static Human getByValue(int value) {
        List<Human> collect = Arrays.stream(Human.values()).filter(it -> it.getValue() == value).collect(Collectors.toList());
        return collect.size() > 0 ? collect.get(0) : HUMAN_TYPE_EMPTY;
    }

    public static Human decodeAndGetByValue(long value) {
        return getByValue(UcfCoder.decodeHumanType(value));
    }

    public final static long HUMAN_TYPE_MASK_BASE = 0x0003L;
    public final static int HUMAN_TYPE_SHIFT = 4;
    public final static long HUMAN_TYPE_MASK = HUMAN_TYPE_MASK_BASE << HUMAN_TYPE_SHIFT;


    // HUMAN - AGE
    public final static long HUMAN_AGE_MASK_BASE = 0x7FFF;
    public final static int HUMAN_AGE_SHIFT = 6;
    public final static long HUMAN_AGE_MASK = HUMAN_AGE_MASK_BASE << HUMAN_AGE_SHIFT;
    public final static String HUMAN_AGE_MASK_AS_STRING = XFormatter.hex(HUMAN_AGE_MASK_BASE);
    // HUMAN - ENERGY
    public final static long HUMAN_ENERGY_MASK_BASE = 0x3F;
    public final static int HUMAN_ENERGY_SHIFT = 21;
    public final static long HUMAN_ENERGY_MASK = HUMAN_ENERGY_MASK_BASE << HUMAN_ENERGY_SHIFT;
    // HUMAN - SATIETY
    public final static long HUMAN_SATIETY_MASK_BASE = 0x3F;
    public final static int HUMAN_SATIETY_SHIFT = 27;
    public final static long HUMAN_SATIETY_MASK = HUMAN_SATIETY_MASK_BASE << HUMAN_SATIETY_SHIFT;
    // HUMAN - PREGNANCY
    public final static long HUMAN_PREGNANCY_MASK_BASE = 0x1FF;
    public final static int HUMAN_PREGNANCY_SHIFT = 33;
    public final static long HUMAN_PREGNANCY_MASK = HUMAN_PREGNANCY_MASK_BASE << HUMAN_PREGNANCY_SHIFT;

    public final static long ACTIVE_FLAG_HUMAN_MASK_BASE = 0x1;
    public final static int ACTIVE_FLAG_HUMAN_SHIFT = 63;
    public final static long ACTIVE_FLAG_HUMAN_MASK = ACTIVE_FLAG_HUMAN_MASK_BASE << ACTIVE_FLAG_HUMAN_SHIFT;
}