package com.harrynguon.platformer.screens;

import com.badlogic.gdx.Screen;
import com.harrynguon.platformer.PlatformerGame;

/**
 * Base Screen class that every game screen will extend. Everything that will be common with each
 * screen is the assets instance, and the game instance.
 *
 * Created by harry on 20/11/17.
 */

public abstract class BaseScreen implements Screen {

    /** Game instance used to access objects e.g. the World */
    protected PlatformerGame game;

    /**
     * Super constructor for each BaseScreen implementation. Game instance is common
     * @param game
     */
    public BaseScreen(PlatformerGame game) {
        this.game = game;
    }

    /**
     * Dispose of all resources. Each sub-class should have their own implementation of this
     * method to increase efficiency of disposing resources.
     */
    @Override
    public abstract void dispose();

    /**
     * Renders the screen every 1/60 seconds
     * @param dt  the delta time elapsed
     */
    @Override
    public abstract void render(float dt);

    /**
     * Handle the user input; convert raw input into user actions. Unique for each Game Screen
     */
    public abstract void handleInput();

    /**
     * Update the locations of objects, players, camera, etc. Depends on the screen.
     * @param dt
     */
    public abstract void update(float dt);

    /**
     * Resize the viewport if the user orientates their phone.
     * @param width  the new width
     * @param height  the new height
     */
    @Override
    public abstract void resize(int width, int height);

    /** Called when this screen becomes the current screen for the game */
    @Override
    public void show() {
    }

    /** This is what happens if the user pauses the game */
    @Override
    public void pause() {
    }

    /** What happens when the user resumes the game */
    @Override
    public void resume() {
    }

    /** Called when this screen is no longer the current screen for the game */
    @Override
    public void hide() {
    }

    public PlatformerGame getGame() {
        return game;
    }
}
