package com.jacobibanez.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jacobibanez.helpers.AssetManager;
import com.jacobibanez.helpers.InputHandler;
import com.jacobibanez.objects.Asteroid;
import com.jacobibanez.objects.ScrollHandler;
import com.jacobibanez.objects.Spacecraft;
import com.jacobibanez.utils.Settings;

import java.util.List;

/**
 * The game screen.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class GameScreen implements Screen {

    public static final String SPACECRAFT_NAME = "spacecraft";
    public static final String PAUSE_BUTTON_NAME = "pauseButton";
    public static final String FIRE_BUTTON_NAME = "fireButton";

    public enum GameState {
        READY, RUNNING, GAME_OVER, PAUSE
    }

    private Stage stage;
    private Spacecraft spacecraft;
    private ScrollHandler scrollHandler;
    private Image pauseButton, fireButton;

    private ShapeRenderer shapeRenderer;
    private Batch batch;

    private float explosionTime = 0.0f;

    private GlyphLayout textLayout;

    private GameState currentState;

    public GameScreen(Batch batch, Viewport viewport) {
        this.shapeRenderer = new ShapeRenderer();

        this.stage = new Stage(viewport, batch);
        this.batch = stage.getBatch();

        this.spacecraft = new Spacecraft(
                Settings.SPACECRAFT_START_X,
                Settings.SPACECRAFT_START_Y,
                Settings.SPACECRAFT_WIDTH,
                Settings.SPACECRAFT_HEIGHT
        );
        this.scrollHandler = new ScrollHandler();
        this.pauseButton = new Image(AssetManager.pauseButton);
        this.fireButton = new Image(AssetManager.fireButton);
        //TODO Exercici 2 - botó de pausa a la part superior dreta
        this.pauseButton.setPosition(
                Settings.GAME_WIDTH - pauseButton.getWidth() - Settings.BUTTON_GAP,
                Settings.BUTTON_GAP
        );
        //TODO Exercici 3 - botó de disparar a la part inferior dreta
        this.fireButton.setPosition(
                Settings.GAME_WIDTH - fireButton.getWidth() - Settings.BUTTON_GAP,
                Settings.GAME_HEIGHT - fireButton.getHeight() - Settings.BUTTON_GAP
        );

        this.stage.addActor(scrollHandler);
        this.stage.addActor(spacecraft);
        this.stage.addActor(pauseButton);
        this.stage.addActor(fireButton);

        this.spacecraft.setName(SPACECRAFT_NAME);
        this.pauseButton.setName(PAUSE_BUTTON_NAME);
        this.fireButton.setName(FIRE_BUTTON_NAME);

        Gdx.input.setInputProcessor(new InputHandler(this));

        this.currentState = GameState.READY;
    }

    public Stage getStage() {
        return stage;
    }

    public Spacecraft getSpacecraft() {
        return spacecraft;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    public void reset() {
        textLayout.setText(AssetManager.font, "Are you\nready?");

        spacecraft.reset();
        scrollHandler.reset();

        currentState = GameState.READY;

        stage.addActor(spacecraft);

        explosionTime = 0.0f;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();

        switch (currentState) {
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

//        drawElements();
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

    public void pauseScreen() {
        //TODO Exercici 2 - s'oculta el botó de pausa i s'abaixa el volum de la música
        setCurrentState(GameScreen.GameState.PAUSE);
        pauseButton.setVisible(false);
        fireButton.setVisible(false);
        AssetManager.bgMusic.setVolume(0.25f);
    }

    public void resumeScreen() {
        setCurrentState(GameScreen.GameState.RUNNING);
        pauseButton.setVisible(true);
        fireButton.setVisible(true);
        AssetManager.bgMusic.setVolume(1f);
    }

    private void updateReady() {
        textLayout = new GlyphLayout();
        textLayout.setText(AssetManager.font, "Are you\nready?");

        batch.begin();
        AssetManager.font.draw(
                batch,
                textLayout,
                (Settings.GAME_WIDTH / 2) - textLayout.width / 2,
                (Settings.GAME_HEIGHT / 2) - textLayout.height / 2
        );
        batch.end();
    }

    private void updateRunning(float delta) {
        stage.act(delta);

        if (scrollHandler.collides(spacecraft)) {
            AssetManager.explosionSound.play();
            stage.getRoot().findActor(SPACECRAFT_NAME).remove();
            textLayout.setText(AssetManager.font, "Game Over :'(");
            currentState = GameState.GAME_OVER;
        }
    }

    private void updateGameOver(float delta) {
        stage.act(delta);

        batch.begin();
        AssetManager.font.draw(
                batch,
                textLayout,
                (Settings.GAME_WIDTH / 2) - textLayout.width / 2,
                (Settings.GAME_HEIGHT / 2) - textLayout.height / 2
        );
        batch.draw(AssetManager.explosionAnimation.getKeyFrame(
                explosionTime, false),
                (spacecraft.getX() + spacecraft.getWidth() / 2) - 32,
                spacecraft.getY() + spacecraft.getHeight() / 2 - 32,
                64,
                64
        );
        batch.end();

        explosionTime += delta;
    }

    private void updatePause(float delta) {
        textLayout = new GlyphLayout();
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

    private void drawElements() {

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(new Color(0, 1, 0, 1));
        shapeRenderer.rect(spacecraft.getX(), spacecraft.getY(), spacecraft.getWidth(), spacecraft.getHeight());

        List<Asteroid> asteroids = scrollHandler.getAsteroids();
        Asteroid asteroid;

        for (int i = 0; i < asteroids.size(); i++) {
            asteroid = asteroids.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
            }
            shapeRenderer.circle(
                    asteroid.getX() + asteroid.getWidth() / 2,
                    asteroid.getY() + asteroid.getHeight() / 2,
                    asteroid.getWidth() / 2
            );
        }

        shapeRenderer.end();
    }
}
