package com.demosoft.life.imitation.entity.type;

/**
 * Created by Andrii_Korkoshko on 2/13/2017.
 */
public enum Plant {
    PLANT_TYPE_EMPTY(0x0, "Empty"),
    PLANT_TYPE_APPLE(0x1, "Apple");

    private int value;
    private String message;

    Plant(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getValue() {
        return value;
    }

    public final static long PLANT_TYPE_MASK = 0x0000_0200_0000_0000L;
    public final static int PLANT_TYPE_SHIFT = 41;

    public final static long PLANT_FRUITS_MASK        = 0x0000_FC00_0000_0000L;
    public final static int PLANT_FRUITS_SHIFT        = 42;

    public final static long ACTIVE_FLAG_PLANT_MASK   = 0x8000_0000_0000_0000L;
    public final static int ACTIVE_FLAG_PLANT_SHIFT   = 63;

}
