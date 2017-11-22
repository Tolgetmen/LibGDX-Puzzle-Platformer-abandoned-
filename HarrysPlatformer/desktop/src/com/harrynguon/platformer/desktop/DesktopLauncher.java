package com.harrynguon.platformer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.harrynguon.platformer.PlatformerGame;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "The Wandering Alien";
		config.width = 1000;
		config.height = 624;
		new LwjglApplication(new PlatformerGame(), config);
	}

}
