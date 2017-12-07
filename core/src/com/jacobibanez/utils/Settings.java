package com.jacobibanez.utils;

/**
 * Utility class with some app-wide settings
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class Settings {

    public static final int GAME_WIDTH = 240;
    public static final int GAME_HEIGHT = 136;

    public static final float SPACECRAFT_VELOCITY = 50f;
    public static final int SPACECRAFT_WIDTH = 36;
    public static final int SPACECRAFT_HEIGHT = 15;
    public static final float SPACECRAFT_START_X = 20f;
    public static final float SPACECRAFT_START_Y = GAME_HEIGHT / 2 - SPACECRAFT_HEIGHT / 2;

    public static final float MAX_ASTEROID = 1.5f;
    public static final float MIN_ASTEROID = 0.5f;

    public static final int ASTEROID_SPEED = 150;
    public static final int ASTEROID_GAP = 75;
    public static final int BG_SPEED = 100;

    public static final int BUTTON_GAP = Math.round(GAME_WIDTH * 0.025f);

    public static final int LASER_WIDTH = 10;
    public static final int LASER_HEIGHT = 10;
    public static final int LASER_SPEED = 200;
}
