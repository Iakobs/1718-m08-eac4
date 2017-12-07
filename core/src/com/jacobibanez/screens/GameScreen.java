package com.jacobibanez.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jacobibanez.helpers.AssetManager;
import com.jacobibanez.helpers.InputHandler;
import com.jacobibanez.objects.Explosion;
import com.jacobibanez.objects.Laser;
import com.jacobibanez.objects.ScrollHandler;
import com.jacobibanez.objects.Spacecraft;
import com.jacobibanez.utils.Settings;

/**
 * The game screen.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class GameScreen implements Screen {

    public static final String PAUSE_BUTTON_NAME = "pauseButton";
    public static final String FIRE_BUTTON_NAME = "fireButton";

    /**
     * The states of the game screen
     */
    public enum GameState {
        READY, RUNNING, GAME_OVER, PAUSE
    }

    private Batch batch;
    private Stage stage;
    private ScrollHandler scrollHandler;
    private Spacecraft spacecraft;
    private Image pauseButton, fireButton;
    private float explosionTime = 0.0f;
    private GlyphLayout textLayout;
    private GameState currentState;

    public GameScreen(Batch batch, Viewport viewport) {
        this.textLayout = new GlyphLayout();

        this.stage = new Stage(viewport, batch);
        this.batch = stage.getBatch();

        //create the actors of the screen
        this.spacecraft = new Spacecraft(
                Settings.SPACECRAFT_START_X,
                Settings.SPACECRAFT_START_Y,
                Settings.SPACECRAFT_WIDTH,
                Settings.SPACECRAFT_HEIGHT
        );
        this.scrollHandler = new ScrollHandler();
        //TODO Exercici 2 - botó de pausa a la part superior dreta
        this.pauseButton = new Image(AssetManager.pauseButton);
        this.pauseButton.setPosition(
                Settings.GAME_WIDTH - pauseButton.getWidth() - Settings.BUTTON_GAP,
                Settings.BUTTON_GAP
        );
        //TODO Exercici 3 - botó de disparar a la part inferior dreta
        this.fireButton = new Image(AssetManager.fireButton);
        this.fireButton.setPosition(
                Settings.GAME_WIDTH - fireButton.getWidth() - Settings.BUTTON_GAP,
                Settings.GAME_HEIGHT - fireButton.getHeight() - Settings.BUTTON_GAP
        );

        //add the actors to the stage, in the proper order so they are in the correct Z layer
        this.stage.addActor(scrollHandler);
        this.stage.addActor(spacecraft);
        this.stage.addActor(pauseButton);
        this.stage.addActor(fireButton);

        //identify needed actors
        this.pauseButton.setName(PAUSE_BUTTON_NAME);
        this.fireButton.setName(FIRE_BUTTON_NAME);

        //set the input processor of the screen
        Gdx.input.setInputProcessor(new InputHandler(this));

        this.currentState = GameState.READY;
    }

    public Stage getStage() {
        return stage;
    }

    public Spacecraft getSpacecraft() {
        return spacecraft;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    public void reset() {
        //set proper variables to default values
        this.currentState = GameState.READY;
        this.explosionTime = 0.0f;

        //reset actors
        this.spacecraft.reset();
        this.scrollHandler.reset();
        this.stage.addActor(spacecraft);
    }

    public void fire() {
        //TODO Exercici 3 - es dispara el làser en prémer el botó de disparar
        this.scrollHandler.fire(new Laser(
                spacecraft.getX() + spacecraft.getWidth(),
                spacecraft.getY() + (spacecraft.getHeight() / 2 - Settings.LASER_HEIGHT / 2),
                Settings.LASER_WIDTH,
                Settings.LASER_HEIGHT,
                Settings.LASER_SPEED
        ));
    }

    public void pauseScreen() {
        //TODO Exercici 2 - s'oculta el botó de pausa i s'abaixa el volum de la música
        setCurrentState(GameScreen.GameState.PAUSE);
        AssetManager.bgMusic.setVolume(0.25f);
        this.pauseButton.setVisible(false);
        this.fireButton.setVisible(false);
        this.spacecraft.pause();
    }

    public void resumeScreen() {
        setCurrentState(GameScreen.GameState.RUNNING);
        AssetManager.bgMusic.setVolume(1f);
        this.pauseButton.setVisible(true);
        this.fireButton.setVisible(true);
        this.spacecraft.resume();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //by default, we only draw the stage
        this.stage.draw();

        //render depending on current state
        switch (this.currentState) {
            case READY:
                updateReady();
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case GAME_OVER:
                updateGameOver(delta);
                break;
            case PAUSE:
                updatePause(delta);
                break;
        }
    }

    @Override
    public void resize(int width, int height) {

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

    }

    private void updateReady() {
        //ready state, before playing
        this.textLayout.setText(AssetManager.font, "Are you\nready?");

        this.batch.begin();
        AssetManager.font.draw(
                this.batch,
                this.textLayout,
                (Settings.GAME_WIDTH / 2) - textLayout.width / 2,
                (Settings.GAME_HEIGHT / 2) - textLayout.height / 2
        );
        this.batch.end();
    }

    private void updateRunning(float delta) {
        //running state, we are playing the game!
        //act the stage
        this.stage.act(delta);

        //check for collisions of the spacecraft
        if (this.scrollHandler.collides(this.spacecraft)) {
            AssetManager.explosionSound.play();
            this.spacecraft.getParent().removeActor(this.spacecraft);
            this.textLayout.setText(AssetManager.font, "Game Over :'(");
            this.currentState = GameState.GAME_OVER;
        }
    }

    private void updateGameOver(float delta) {
        //game over state
        stage.act(delta);

        //show game over text
        batch.begin();
        AssetManager.font.draw(
                batch,
                textLayout,
                (Settings.GAME_WIDTH / 2) - textLayout.width / 2,
                (Settings.GAME_HEIGHT / 2) - textLayout.height / 2
        );
        //animate the explosion of the spacecraft
        batch.draw(
                AssetManager.explosionAnimation.getKeyFrame(explosionTime, false),
                spacecraft.getX() + spacecraft.getWidth() / 2 - 32,
                spacecraft.getY() + spacecraft.getHeight() / 2 - 32,
                64,
                64
        );
        batch.end();

        explosionTime += delta;
    }

    private void updatePause(float delta) {
        //pause state
        //TODO Exercici 2 - fent act() de la nau, com que tots els objectes comparteixen batch, el parpalleig s'escampa
        spacecraft.act(delta);

        //show pause text
        textLayout.setText(AssetManager.font, "Pause");
        batch.begin();
        AssetManager.font.draw(
                batch,
                textLayout,
                (Settings.GAME_WIDTH / 2) - textLayout.width / 2,
                (Settings.GAME_HEIGHT / 2) - textLayout.height / 2
        );
        batch.end();
    }
}
