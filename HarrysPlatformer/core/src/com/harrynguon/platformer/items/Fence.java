package com.harrynguon.platformer.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.harrynguon.platformer.screens.PlayScreen;

/**
 * Not an item, but changes sprite to an open-fence when the key is picked up
 *
 * Created by Harry on 23/11/2017.
 */

public class Fence extends Item {

    public Fence(PlayScreen screen, TextureRegion texture, float x, float y) {
        super(screen, texture, x, y);
    }

    public void keyPickedUp() {
        //setRegion(Constants.OPEN_FENCE);
    }

}
