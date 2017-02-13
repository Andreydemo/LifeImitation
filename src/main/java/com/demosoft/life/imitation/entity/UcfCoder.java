package com.demosoft.life.imitation.entity;

import com.demosoft.life.imitation.entity.type.Human;
import com.demosoft.life.imitation.entity.type.Landscape;
import com.demosoft.life.imitation.entity.type.Plant;

/**
 * Created by Andrii_Korkoshko on 1/24/2017.
 */
public class UcfCoder {


    public final static long encodeLandscapeType(long uc, int u) {
        return encode(uc, u, Landscape.LANDSCAPE_TYPE_MASK, Landscape.LANDSCAPE_TYPE_SHIFT);
    }

    public final static int decodeLandscapeType(long uc) {
        return decode(uc, Landscape.LANDSCAPE_TYPE_MASK, Landscape.LANDSCAPE_TYPE_SHIFT);
    }

    public final static String decodeLandscapeTypeAsString(long uc) {
        return Landscape.getByValue( decodeLandscapeType(uc)).getMessage();
    }

    public final static long encodeHumanType(long uc, int u) {
        return encode(uc, u, Human.HUMAN_TYPE_MASK, Human.HUMAN_TYPE_SHIFT);
    }

    public final static int decodeHumanType(long uc) {
        return decode(uc, Human.HUMAN_TYPE_MASK, Human.HUMAN_TYPE_SHIFT);
    }

    public final static long encodeHumanAge(long uc, int u) {
        return encode(uc, u, Human.HUMAN_AGE_MASK, Human.HUMAN_AGE_SHIFT);
    }

    public final static int decodeHumanAge(long uc) {
        return decode(uc, Human.HUMAN_AGE_MASK, Human.HUMAN_AGE_SHIFT);
    }

    public final static long encodeHumanEnergy(long uc, int u) {
        return encode(uc, u, Human.HUMAN_ENERGY_MASK, Human.HUMAN_ENERGY_SHIFT);
    }

    public final static int decodeHumanEnergy(long uc) {
        return decode(uc, Human.HUMAN_ENERGY_MASK, Human.HUMAN_ENERGY_SHIFT);
    }

    public final static long encodeHumanSatiety(long uc, int u) {
        return encode(uc, u, Human.HUMAN_SATIETY_MASK, Human.HUMAN_SATIETY_SHIFT);
    }

    public final static int decodeHumanSatiety(long uc) {
        return decode(uc, Human.HUMAN_SATIETY_MASK, Human.HUMAN_SATIETY_SHIFT);
    }

    public final static long encodeHumanPregnancy(long uc, int u) {
        return encode(uc, u, Human.HUMAN_PREGNANCY_MASK, Human.HUMAN_PREGNANCY_SHIFT);
    }

    public final static int decodeHumanPregnancy(long uc) {
        return decode(uc, Human.HUMAN_PREGNANCY_MASK, Human.HUMAN_PREGNANCY_SHIFT);
    }

    public final static long encodePlantType(long uc, int u) {
        return encode(uc, u, Plant.PLANT_TYPE_MASK, Plant.PLANT_TYPE_SHIFT);
    }

    public final static int decodePlantType(long uc) {
        return decode(uc, Plant.PLANT_TYPE_MASK, Plant.PLANT_TYPE_SHIFT);
    }

    public final static long encodePlantFruits(long uc, int u) {
        return encode(uc, u, Plant.PLANT_FRUITS_MASK, Plant.PLANT_FRUITS_SHIFT);
    }

    public final static int decodePlantFruits(long uc) {
        return decode(uc, Plant.PLANT_FRUITS_MASK, Plant.PLANT_FRUITS_SHIFT);
    }

    public final static long encodeActiveFlagHuman(long uc, int u) {
        return encode(uc, u, Human.ACTIVE_FLAG_HUMAN_MASK, Human.ACTIVE_FLAG_HUMAN_SHIFT);
    }

    public final static int decodeActiveFlagHuman(long uc) {
        return decode(uc, Human.ACTIVE_FLAG_HUMAN_MASK, Human.ACTIVE_FLAG_HUMAN_SHIFT);
    }

    public final static long encodeActiveFlagPlant(long uc, int u) {
        return encode(uc, u, Plant.ACTIVE_FLAG_PLANT_MASK, Plant.ACTIVE_FLAG_PLANT_SHIFT);
    }

    public final static int decodeActiveFlagPlant(long uc) {
        return decode(uc, Plant.ACTIVE_FLAG_PLANT_MASK, Plant.ACTIVE_FLAG_PLANT_SHIFT);
    }

    // Example:
    // ===================================================================================

    // 1. ~mask
    // 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|1 1|000
    // ~
    // -----------------------------------------------------------------------------------
    // 1111 1111 1111 1111 1111 1111 1111 1111   1111 1111 1111 1111 1111 1111 111|0 0|111

    // 2. (uc & ~mask)
    // 0000 0000 0000 0000 0110 0000 0111 0001   1010 0000 0100 1110 0010 0000 000|1 0|000
    // &
    // 1111 1111 1111 1111 1111 1111 1111 1111   1111 1111 1111 1111 1111 1111 111|0 0|111
    // -----------------------------------------------------------------------------------
    // 0000 0000 0000 0000 0110 0000 0111 0001   1010 0000 0100 1110 0010 0000 000|0 0|000

    // 3. (long) u
    // 0000 0000 0000 0000 0000 0000 0000 00|01|
    // (long)
    // -----------------------------------------------------------------------------------
    // 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 0000 00|01|

    // 4. (long) u << shift
    // 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 0000 00|01|
    // << 3
    // -----------------------------------------------------------------------------------
    // 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|0 1|000


    // 5. ((long) u << shift & mask) - additional protection
    // 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|0 1|000
    // &
    // 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|1 1|000
    // -----------------------------------------------------------------------------------
    // 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|0 1|000

    // 6. (uc & ~mask) | ((long) u << shift & mask)
    // 0000 0000 0000 0000 0110 0000 0111 0001   1010 0000 0100 1110 0010 0000 000|0 0|000
    // |
    // 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|0 1|000
    // -----------------------------------------------------------------------------------
    // 0000 0000 0000 0000 0110 0000 0111 0001   1010 0000 0100 1110 0010 0000 000|0 1|000

    private final static long encode(long uc, int u, long mask, int shift) {
        return (uc & ~mask) | ((long) u << shift & mask);
    }

    // Example:
    // ===================================================================================

    // 1. (uc & mask)
    // 0000 0000 0000 0000 0110 0000 0111 0001   1010 0000 0100 1110 0010 0000 000|0 1|000
    // &
    // 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|1 1|000
    // -----------------------------------------------------------------------------------
    // 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|0 1|000

    // 2. ((uc & mask) >>> 3)
    // 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 000|0 1|000
    // >>> 3
    // -----------------------------------------------------------------------------------
    // 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 0000 00|01|

    // 3. (int) ((uc & mask) >>> shift)
    // 0000 0000 0000 0000 0000 0000 0000 0000   0000 0000 0000 0000 0000 0000 0000 00|01|
    // (int)
    // -----------------------------------------------------------------------------------
    // 0000 0000 0000 0000 0000 0000 0000 00|01|

    private final static int decode(long uc, long mask, int shift) {
        return (int) ((uc & mask) >>> shift);
    }
}
