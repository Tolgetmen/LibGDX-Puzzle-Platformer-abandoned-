package com.harrynguon.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.harrynguon.platformer.screens.MainMenuScreen;
import com.harrynguon.platformer.util.Assets;

/**
 * The root node of the Game. This will create the game itself, and the spritebatch, and the
 * asset instance.
 */
public class PlatformerGame extends Game {

	/** Resources. These will all be parsed around the game */
	public SpriteBatch batch;

	/**
	 * Called only once upon initialisation. This will create the initial screen instance
	 * (new PlayScreen()).
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		AssetManager assetManager = new AssetManager();
		Assets.instance.init(assetManager);
		setScreen(new MainMenuScreen(this));
	}

	/**
	 * Render to the screen every 1/60 seconds.
	 */
	@Override
	public void render () {
		Gdx.gl.glClearColor(0.9f, 0.9f, 0.7f, 1f); // beige
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// this will call render() on the current screen
		super.render();
	}

	/**
	 * Dispose of all resources when the game has been closed.
	 */
	@Override
	public void dispose () {
		batch.dispose();
		Assets.instance.unloadAssets();
		Assets.instance.getAssetManager().dispose();
		screen.dispose();
	}

	public Assets getAssets() {
		return Assets.instance;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}
