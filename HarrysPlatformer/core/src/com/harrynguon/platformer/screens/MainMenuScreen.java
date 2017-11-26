package com.harrynguon.platformer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.harrynguon.platformer.PlatformerGame;
import com.harrynguon.platformer.util.Assets;
import com.harrynguon.platformer.util.Constants;

/**
 * Main menu screen for the game
 * https://stackoverflow.com/questions/32451921/how-to-create-libgdx-main-menu-screen
 * Created by Harry on 22/11/2017.
 */

public class MainMenuScreen extends BaseScreen {

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;

    public MainMenuScreen(PlatformerGame game) {
        super(game);
        // play the main menu music
        Assets.instance.soundAssets.mainMenu.play();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }


    @Override
    public void show() {
        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.top();

        //Create buttons
        // title
        TextButton.TextButtonStyle title = new TextButton.TextButtonStyle();
        BitmapFont font = Assets.instance.fontAssets.mainMenuTitleFont;
        font.getData().setScale(2f);
        font.setColor(new Color(Color.GOLDENROD));
        title.font = font;
        TextButton titleLabel = new TextButton("The Lost Wanderer (Prototype)", title);
        // labels
        TextButton.TextButtonStyle labels = new TextButton.TextButtonStyle();
        BitmapFont labelsFont = Assets.instance.fontAssets.mainMenuButtonsFont;
        labelsFont.getData().setScale(1.5f);
        labels.font = labelsFont;

        TextButton playButton = new TextButton("Play", labels);
        TextButton optionsButton = new TextButton("Options", labels);
        TextButton exitButton = new TextButton("Exit", labels);

        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.instance.soundAssets.mainMenu.stop();
                Assets.instance.soundAssets.btnSound.play(0.6f);
                game.setScreen(new PlayScreen(game));
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.instance.soundAssets.mainMenu.stop();
                Assets.instance.soundAssets.btnSound.play(0.6f);
                Gdx.app.exit();
            }
        });

        //Add buttons to table
        mainTable.add(titleLabel).padTop(100);
        mainTable.row();
        mainTable.add(playButton).padTop(100);
        mainTable.row();
        mainTable.add(optionsButton).padTop(100);
        mainTable.row();
        mainTable.add(exitButton).padTop(100);

        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.3f, .5f, .56f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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
        stage.dispose();
    }
}
