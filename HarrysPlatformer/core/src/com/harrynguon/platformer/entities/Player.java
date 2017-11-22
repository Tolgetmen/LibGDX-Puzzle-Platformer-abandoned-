package com.harrynguon.platformer.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.harrynguon.platformer.screens.PlayScreen;
import com.harrynguon.platformer.util.Assets;

/**
 * This is the instance of the player that will be controlled within the game.
 *
 * Created by Harry on 22/11/2017.
 */

public class Player extends Sprite {

    /**
     * Animations
     */
    public enum State {
        STANDING,
        WALKING,
        JUMPING
    }

    public State currentState;
    public State previousState;
    private float stateTimer;
    private boolean runningRight;
    private boolean canJump;

    /** The player will be inside a world with a physics simulation */
    private World world;
    private Map level;
    /** Collision/physics body */
    public Body b2body;

    /**
     * This constructs the player.
     * @param screen
     */
    public Player(PlayScreen screen) {
        world = screen.getWorld();
        level = screen.getMap();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0f;
        runningRight = true;
        canJump = true;
        // set initial animation frame
        setRegion(Assets.instance.playerAssets.stand);
    }

    /**
     * Updates the player's position based on its physics body location after movement
     * @param dt  the delta time
     */
    public void update(float dt) {
        setPosition(b2body.getPosition().x, b2body.getPosition().y);
        setRegion(getFrame(dt));
    }

    /**
     * Finds the correct frame to display (for animations)
     * @param dt  the delta time
     * @return  the texture region of the frame
     */
    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = Assets.instance.playerAssets.jump;
                break;
            case WALKING:
                region = Assets.instance.playerAssets.walk.getKeyFrame(stateTimer);
                break;
            case STANDING:
            default:
                region = Assets.instance.playerAssets.stand;
                break;
        }
        if ((b2body.getLinearVelocity().x < 0.0f || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0.0f || runningRight) && region.isFlipX()) {
            //facing to the left but running to the right
            region.flip(true, false);
            runningRight = true;
        }
        // when transitioning to new state, reset timer, else increment to it
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    /**
     * Checks the player's speed to determine what state it's in
     * @return  the State enum for the current frame
     */
    private State getState() {
        if (b2body.getLinearVelocity().y > 0 || b2body.getLinearVelocity().y < 0 && previousState
                == State.JUMPING) {
            return State.JUMPING;
        } else if (b2body.getLinearVelocity().y < 0) { //falling animation (not implemented)
            return State.JUMPING;
        } else if (b2body.getLinearVelocity().x != 0) {
            return State.WALKING;
        } else {
            return State.STANDING;
        }
    }

    public void setB2body(Body b2body) {
        this.b2body = b2body;
    }

    public boolean canJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }
}
