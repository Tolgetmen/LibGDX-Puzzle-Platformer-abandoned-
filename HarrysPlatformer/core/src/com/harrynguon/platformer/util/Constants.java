package com.harrynguon.platformer.util;

import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * These constants will hold all variables that are used throughout the game globally.
 *
 * Created by Harry on 20/11/2017.
 */
public class Constants {

    /** Minimum virtual width and height */
    public static final int V_WIDTH = 1000;
    public static final int V_HEIGHT = 624;
    /** Pixel-per-metre scale of 150 pixels per metre rendered on the screen */
    public static final float PPM = 150;

    /** Player */
    public static final float MAX_WALKING_SPEED = 3f;
    public static final int PLAYER_WIDTH = 128;
    public static final int PLAYER_HEIGHT = 256;
    public static final String PLAYER_ATLAS = "art/Spritesheet/alien_green/alien_green.pack";

    /**
     * Private constructor, meaning this class cannot be instantiated
     */
    private Constants() {}
}
