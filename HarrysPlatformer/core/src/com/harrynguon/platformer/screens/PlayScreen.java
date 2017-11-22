package com.harrynguon.platformer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.harrynguon.platformer.PlatformerGame;
import com.harrynguon.platformer.control.Controller;
import com.harrynguon.platformer.entities.Player;
import com.harrynguon.platformer.items.Item;
import com.harrynguon.platformer.util.Assets;
import com.harrynguon.platformer.util.Constants;
import com.harrynguon.platformer.world.EntitySpawner;
import com.harrynguon.platformer.world.WorldContactListener;

/**
 * This is the play screen class, responsible for rendering the current level in the game, along
 * with all of the objects, the player, etc.
 *
 * Created by harry on 20/11/17.
 */

public class PlayScreen extends BaseScreen {

    /** Camera, viewport, display variables */
    private OrthographicCamera camera;
    private Viewport viewport;
    /** Simulated world instance (it has 1.4x gravity) */
    private World world;
    private EntitySpawner entitySpawner;
    /** Used to draw visible Box2D lines around each Box2D object */
    private Box2DDebugRenderer b2dr;
    /** Map fields */
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    /** Player and input */
    private Player player;
    private Controller controller;
    /** Play music after a delay */
    private float initialMusic = 0f;
    private boolean hasPlayed = false;

    /**
     * Constructs the play screen instance. All fields are initialised.
     *
     * @param game
     */
    public PlayScreen(PlatformerGame game) {
        super(game);
        camera = new OrthographicCamera();
        // set viewport to be the virtual width divided by the PPM -> scales everything down by
        // the PPM. Every pixel p represents p / PPM units.
        viewport = new StretchViewport(
                Constants.V_WIDTH / Constants.PPM,
                Constants.V_HEIGHT / Constants.PPM,
                camera);
        // set the 0,0 location to be the bottom left corner of the screen
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        viewport.apply();
        // create the world with a gravity of 1.4x the normal amount. the proportions for this
        // game are messed up
        world = new World(new Vector2(0, -9.81f * 1.4f), true);
        b2dr = new Box2DDebugRenderer();
        /**
         * TODO :
         * set all of the map stuff into a seperate class, so when a map gets traversed,
         * no problems will be encountered
         */
        // load the level and set the renderer for it
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("levels/level1.tmx");
        // now load the entities
        entitySpawner = new EntitySpawner(this);
        player = entitySpawner.spawnPlayer();
        entitySpawner.spawnCollisionObjects();
        entitySpawner.spawnKey();
        entitySpawner.spawnLockObject();
        world.setContactListener(new WorldContactListener(this));
        // scale the units to be as game units (pixels / PPM)
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PPM);
        renderer.setView(camera);
        // create controller for this screen
        controller = new Controller();
        Gdx.input.setInputProcessor(controller);
        // initial camera position
        camera.position.x = player.b2body.getPosition().x;
        camera.position.y = player.b2body.getPosition().y;
        Assets.instance.soundAssets.levelOne.play();
    }

    /**
     * Render each pixel of the screen per 1/60 seconds.
     *
     * @param dt  the delta time elapsed
     */
    @Override
    public void render(float dt) {
        // first update all new locations and positions of every entity
        update(dt);
        if (!hasPlayed) {
            initialMusic += dt;
            if (initialMusic <= 4.0f) {
                Assets.instance.soundAssets.levelOne.setVolume(initialMusic / 4);
            } else {
                hasPlayed = true;
            }
        }
        // render the map first, followed by the collision boxes and then every entity on top
        renderer.render();
        b2dr.render(world, camera.combined);
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Item item: entitySpawner.getItems()) {
            //if (!item.isPickedUp())
            //^^ if so then destroy its body as well
            item.draw(game.batch);
        }
        game.batch.end();
    }

    /**
     * Handle the user input; convert raw input into user actions. Unique for each Game Screen.
     * Up - Jump up
     * Left - Walk left
     * Right - Walk right
     */
    @Override
    public void handleInput() {
        // jump up
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.canJump()) {
            player.b2body.applyLinearImpulse(new Vector2(0, 6f), player.b2body
                    .getWorldCenter(), true);
            player.setCanJump(false);
        }
        // walk left
        if (controller.isLeft() && player.b2body.getLinearVelocity().x >= Constants
                .MAX_WALKING_SPEED * -1) {
            player.b2body.applyLinearImpulse(new Vector2(-0.2f , 0), player.b2body
                    .getWorldCenter(), true);
        }
        // walk right
        if (controller.isRight() && player.b2body.getLinearVelocity().x <=
                Constants.MAX_WALKING_SPEED) {
            player.b2body.applyLinearImpulse(new Vector2(0.2f, 0), player.b2body
                    .getWorldCenter(), true);
        }
        // not pressing anything, slow it down
        if (!controller.isRight() && !controller.isLeft()) {
            player.b2body.setLinearDamping(1f);
        }
    }

    /**
     * Updates all of the entities positions after input
     * @param dt  the game's elapsed delta time in this frame
     */
    @Override
    public void update(float dt) {
        // first handle the input
        handleInput();
        // apply the world physics simulatio
        world.step(1/60f, 6, 2);
        // update entities after simulation has been processed
        player.update(dt);
        // linear interpolation (start vs. endpoint)
        float lerp = 2.5f;
        // update the position of the camera by following the player
        Vector3 position = camera.position;
        position.x += (player.b2body.getPosition().x - position.x) * lerp * dt;
        position.y += (player.b2body.getPosition().y - position.y + 0.5f) * lerp * dt;
        camera.update();
        /** clamp camera within bounds of map
         * cam.position.x = MathUtils.clamp(cam.position.x, evalWidth / 2f,
         totalMapWidth - evalWidth / 2f);
         cam.position.y = MathUtils.clamp(cam.position.y, evh / 2f,
         totalMapHeight - evh / 2f);
         */
        // prepare for rendering after everything has been updated
        renderer.setView(camera);
    }

    /**
     * Updates the viewport's dimensions accordingly
     * @param width  the new width
     * @param height  the new height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        /** needs to be implemented*/
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }
}
