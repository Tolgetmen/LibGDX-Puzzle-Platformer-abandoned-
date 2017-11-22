package com.harrynguon.platformer.world;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.harrynguon.platformer.PlatformerGame;
import com.harrynguon.platformer.entities.Player;
import com.harrynguon.platformer.screens.PlayScreen;
import com.harrynguon.platformer.util.Constants;

/**
 * Tells the program what to do when two b2d objects collide with each other.
 *
 * Created by Harry on 22/11/2017.
 */

public class WorldContactListener implements ContactListener {

    private PlayScreen screen;
    private PlatformerGame game;
    private Player player;

    public WorldContactListener(PlayScreen screen) {
        this.screen = screen;
        this.game = screen.getGame();
        this.player = screen.getPlayer();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        // determine what has collided with what
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch (cDef) {
            case Constants.PLAYER_JUMP_BIT | Constants.OBJECT_BIT:
                player.setCanJump(true);
                break;
            case Constants.PLAYER_BIT | Constants.LOCK_BIT:
                // check if player contains the key. If so, traverse the map
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
