package com.harrynguon.platformer.util;

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

    /** Music & Sound Effects */
    public static final String MAIN_MENU_MUSIC = "sounds/mainmenu/mainmenu.wav";
    public static final String BUTTON_SOUND = "sounds/mainmenu/load.wav";
    public static final String LEVEL1_MUSIC = "sounds/level1/toesandwater.mp3";

    /** Fonts */
    public static final String MAIN_MENU_TITLE_FONT = "font/kenney high.fnt";
    public static final String MAIN_MENU_BUTTONS_FONT = "font/kenney pixel.fnt";

    /** Items */
    public static final String ITEM_ATLAS = "art/items/items.pack";

    /** Collision bits */
    public static final short OBJECT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short PLAYER_JUMP_BIT = 4;
    public static final short ENEMY_BIT = 8;
    public static final short LOCK_BIT = 16;
    public static final short KEY_BIT = 32;

    /**
     * Private constructor, meaning this class cannot be instantiated
     */
    private Constants() {}
}
