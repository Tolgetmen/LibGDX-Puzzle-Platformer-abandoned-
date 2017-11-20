package com.harrynguon.platformer.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * Created by harry on 20/11/17.
 */

public abstract class PlatformerScreen implements Screen {

    protected Game game;

    public PlatformerScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // not to override here, but as a screen instance
    }

    @Override
    public void resize(int width, int height) {
        // fit viewport and camera etc
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        // should be overidden in every single instance class
    }
}
