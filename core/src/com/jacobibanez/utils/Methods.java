package com.jacobibanez.utils;

import java.util.Random;

/**
 * Utility class with some useful methods.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class Methods {

    /**
     * Returns a random float between an including range.
     *
     * @param min The minimum value of the range.
     * @param max The maximum value of the range.
     * @return The random float.
     */
    public static float randomFloat(float min, float max) {
        return new Random().nextFloat() * (max - min) + min;
    }
}
