package com.harrynguon.platformer.control;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

/**
 * This class controls the raw input -> user actions within the game by setting boolean flags for
 * raw input entered.
 *
 * Created by Harry on 22/11/2017.
 */

public class Controller extends InputAdapter implements InputProcessor {

    /** Fields for the user actions */
    private boolean up, left, down, right;

    /** Custom constructor */
    public Controller() {
        super();
    }

    /**
     * Sets the boolean flags to whichever keys are being pressed at the current frame
     * @param keycode  the value of the key being pressed
     * @return  always false, but sets boolean field flags accordingly
     */
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                up = true;
                break;
            case Input.Keys.LEFT:
                left = true;
                break;
            case Input.Keys.RIGHT:
                right = true;
                break;
        }
        return false;
    }

    /**
     * Upon release, it means there is no user action that is going to occur
     * @param keycode  the value of the key being pressed
     * @return  always false, but sets boolean field flags accordingly
     */
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                up = false;
                break;
            case Input.Keys.LEFT:
                left = false;
                break;
            case Input.Keys.RIGHT:
                right = false;
                break;
        }
        return false;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isRight() {
        return right;
    }
}
