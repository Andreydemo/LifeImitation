package com.demosoft.life.logic.math;

/**
 * Created by Andrii_Korkoshko on 2/10/2017.
 */
public class XMath {

    public static int   getValueInRange(int value, int min, int max) {
        return Math.max(Math.min(value, max), min);
    }
}
