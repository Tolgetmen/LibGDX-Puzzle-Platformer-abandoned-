package com.harrynguon.platformer.util;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.harrynguon.platformer.entities.Player;
import com.harrynguon.platformer.screens.PlayScreen;

/**
 * This class is a helper class that spawns Box2D objects according to the object layers within
 * the Tiled map.
 *
 * Created by Harry on 22/11/2017.
 */
public class EntitySpawner {

    private PlayScreen screen;
    private World world;
    private Map map;

    /**
     * Accesses the current world and map
     * @param screen  the reference to the game screen
     */
    public EntitySpawner(PlayScreen screen) {
        this.screen = screen;
        world = screen.getWorld();
        map = screen.getMap();
    }

    /**
     * Creates the player instance within the world.
     * @return  the player instance that is constructed
     */
    public Player spawnPlayer() {
        Player player = new Player(screen);
        // define physics body within the world
        player.setB2body(createBody(world));
        defineBounds(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, player);
        return player;
    }

    /**
     * Creates the physics body and sets its initial position.
     * @param world  the simulated world it's in
     * @return  the physics body for the player
     */
    private Body createBody(World world) {
        BodyDef bdef = new BodyDef();
        // Initial position
        // grab the player object rectangle from the map as the spawn point
        MapObject playerObject = map.getLayers().get("SpawnPoints").getObjects().getByType
                (RectangleMapObject.class).first();
        // grab the player object rectangle from the map
        Rectangle rect = ((RectangleMapObject) playerObject).getRectangle();
        // set the Box2D rectangle as the player's spawn point
        bdef.position.set(((rect.getX() + rect.getWidth() / 2) / Constants.PPM),
                (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
        // Kinetic movement
        bdef.type = BodyDef.BodyType.DynamicBody;
        return world.createBody(bdef);
    }

    /**
     * This method creates the collision shape within the physics body.
     * @param width  the sprite width
     * @param height  the sprite height
     * @param player  the player to work with
     */
    private void defineBounds(int width, int height, Player player) {
        player.setBounds(0, 0, (width / 2) / Constants.PPM, (height / 2) / Constants.PPM);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        // width and height used for body position calculations
        float bWidth = player.getBoundingRectangle().getWidth();
        float bHeight = player.getBoundingRectangle().getHeight();
        shape.setRadius(0.16f); // size of player
        // bottom collision circle
        shape.setPosition(new Vector2(
                (shape.getPosition().x + bWidth / 2),
                (shape.getPosition().y + bHeight / 5)));
        fdef.shape = shape;
        player.b2body.createFixture(fdef).setUserData(this);

        FixtureDef fdef2 = new FixtureDef();
        CircleShape shape2 = new CircleShape();
        shape2.setRadius(0.16f); // size of player
        // top collision circle
        shape2.setPosition(new Vector2(
                (shape2.getPosition().x + bWidth / 2),
                (shape2.getPosition().y + bHeight / 3)));
        fdef2.shape = shape2;
        player.b2body.createFixture(fdef2).setUserData(this);
    }

    /**
     * Spawns all Box2D object layer objects
     */
    public void spawnCollisionObjects() {
        // construct each Box2D object within the level. what needs to be done is to seperate
        // this into a class.
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        // create all objects within the map from layer 1
        // starting from bottom up, index start from 0. Layer 1 == object layer for this level
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject
                .class)) {
            // grab the object rectangle from the map
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            // set the Box2D rectangle to the correct location on the map
            bdef.position.set((rect.getX() + rect.getWidth() / 2) /  Constants.PPM,
                    (rect.getY() + rect.getHeight() / 2) /  Constants.PPM);
            // create the object within the world instance
            body = world.createBody(bdef);
            // set it is a rectangle shape and create the fixture
            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) /  Constants.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

}
