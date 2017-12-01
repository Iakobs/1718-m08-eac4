package com.jacobibanez.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
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

    private static final String SPACECRAFT_NAME = "spacecraft";

    private Stage stage;
    private Spacecraft spacecraft;
    private ScrollHandler scrollHandler;

    private ShapeRenderer shapeRenderer;
    private Batch batch;

    private boolean gameOver = false;
    private float explosionTime = 0.0f;

    private GlyphLayout textLayout;

    public GameScreen() {
        shapeRenderer = new ShapeRenderer();

        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);

        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
        stage = new Stage(viewport);
        batch = stage.getBatch();

        spacecraft = new Spacecraft(
                Settings.SPACECRAFT_START_X,
                Settings.SPACECRAFT_START_Y,
                Settings.SPACECRAFT_WIDTH,
                Settings.SPACECRAFT_HEIGHT
        );
        scrollHandler = new ScrollHandler();

        stage.addActor(scrollHandler);
        stage.addActor(spacecraft);

        spacecraft.setName(SPACECRAFT_NAME);

        AssetManager.bgMusic.play();

        Gdx.input.setInputProcessor(new InputHandler(this));

        textLayout = new GlyphLayout();
        textLayout.setText(AssetManager.font, "GameOver");
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

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();

        if (!gameOver) {
            if (scrollHandler.collides(spacecraft)) {
                AssetManager.explosionSound.play();
                stage.getRoot().findActor(SPACECRAFT_NAME).remove();
                gameOver = true;
            }
        } else {
            batch.begin();
            batch.draw(AssetManager.explosionAnimation.getKeyFrame(
                    explosionTime, false),
                    (spacecraft.getX() + spacecraft.getWidth() / 2) - 32,
                    spacecraft.getY() + spacecraft.getHeight() / 2 - 32,
                    64,
                    64
            );

            AssetManager.font.draw(
                    batch,
                    textLayout,
                    Settings.GAME_WIDTH / 2 - textLayout.width / 2,
                    Settings.GAME_HEIGHT / 2 - textLayout.height / 2
            );

            batch.end();

            explosionTime += delta;
        }

        if (scrollHandler.collides(spacecraft)) {
            Gdx.app.log("App", "Explosion");
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

    private void drawElements() {
//        Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
//        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
