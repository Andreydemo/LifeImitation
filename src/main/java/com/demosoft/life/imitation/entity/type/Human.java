package com.demosoft.life.imitation.entity.type;

/**
 * Created by Andrii_Korkoshko on 2/13/2017.
 */
public enum Human {

    HUMAN_TYPE_EMPTY(0x0, "Empty"),
    HUMAN_TYPE_MAN(0x1, "Water low"),
    HUMAN_TYPE_WOMAN(0x2, "Water high");

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
    public final static long HUMAN_TYPE_MASK = 0x0000_0000_0000_0018L;
    public final static int HUMAN_TYPE_SHIFT = 4;


    // HUMAN - AGE
    public final static long HUMAN_AGE_MASK           = 0x0000_0000_000F_FFE0L;
    public final static int HUMAN_AGE_SHIFT           = 6;
    // HUMAN - ENERGY
    public final static long HUMAN_ENERGY_MASK        = 0x0000_0000_03F0_0000L;
    public final static int HUMAN_ENERGY_SHIFT        = 21;
    // HUMAN - SATIETY
    public final static long HUMAN_SATIETY_MASK       = 0x0000_0000_FC00_0000L;
    public final static int HUMAN_SATIETY_SHIFT       = 27;
    // HUMAN - PREGNANCY
    public final static long HUMAN_PREGNANCY_MASK     = 0x0000_01FF_0000_0000L;
    public final static int HUMAN_PREGNANCY_SHIFT     = 33;

    public final static long ACTIVE_FLAG_HUMAN_MASK   = 0x4000_0000_0000_0000L;
    public final static int ACTIVE_FLAG_HUMAN_SHIFT   = 63;
}
