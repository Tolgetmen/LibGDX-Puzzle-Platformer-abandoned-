package com.harrynguon.platformer.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.harrynguon.platformer.screens.PlayScreen;
import com.harrynguon.platformer.util.Constants;

/**
 * Created by Harry on 23/11/2017.
 */

public abstract class Item extends Sprite {

    protected PlayScreen screen;
    protected World world;

    public Item(PlayScreen screen, TextureRegion texture, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        setSize(texture.getRegionWidth() / Constants.PPM, texture.getRegionHeight() / Constants
                .PPM);
        setRegion(texture);
    }

}
