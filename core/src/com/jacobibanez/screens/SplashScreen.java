package com.jacobibanez.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jacobibanez.SpaceRace;
import com.jacobibanez.helpers.AssetManager;
import com.jacobibanez.utils.Settings;

/**
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class SplashScreen implements Screen {

    private Stage stage;
    private SpaceRace game;

    private Label.LabelStyle textStyle;
    private Label textLabel;

    public SplashScreen(SpaceRace game) {
        AssetManager.bgMusic.play();

        this.game = game;

        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);

        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        stage = new Stage(viewport);

        stage.addActor(new Image(AssetManager.background));

        textStyle = new Label.LabelStyle(AssetManager.font, null);
        textLabel = new Label("SpaceRace", textStyle);

        Container<Label> container = new Container<Label>(textLabel);
        container.setTransform(true);
        container.center();
        container.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 2);

        container.addAction(Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                        Actions.scaleTo(1.5f, 1.5f, 1),
                        Actions.scaleTo(1, 1, 1)
                )
        ));
        stage.addActor(container);

        Image spacecraft = new Image(AssetManager.spacecraft);
        float y = Settings.GAME_HEIGHT / 2 + textLabel.getHeight();
        spacecraft.addAction(Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                        Actions.moveTo(0 - spacecraft.getWidth(), y),
                        Actions.moveTo(Settings.GAME_WIDTH, y, 5)
                )
        ));
        stage.addActor(spacecraft);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport()));
            dispose();
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
}
