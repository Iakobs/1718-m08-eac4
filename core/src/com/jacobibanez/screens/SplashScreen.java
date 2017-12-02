package com.jacobibanez.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
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
 * The splash screen.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class SplashScreen implements Screen {

    private Stage stage;
    private SpaceRace game;

    public SplashScreen(SpaceRace game) {
        this.game = game;

        //start the music
        AssetManager.bgMusic.play();

        stage = getStageWithOrthographicCamera();

        //add the background to the stage
        stage.addActor(new Image(AssetManager.background));

        //add the game's title
        Container<Label> gameTitle = getAnimatedText(
                "SpaceRace",
                new Vector2(
                        Settings.GAME_WIDTH / 2,
                        //TODO Exercici 1 - a) Modifiqueu el títol de l'aplicació per a què es situï en 1/3 de la pantalla.
                        Settings.GAME_HEIGHT / 3
                ),
                Actions.repeat(
                        RepeatAction.FOREVER,
                        Actions.sequence(
                                Actions.scaleTo(1.5f, 1.5f, 1),
                                Actions.scaleTo(1, 1, 1)
                        )
                ));
        stage.addActor(gameTitle);

        //TODO Exercici 1 - b) Afegiu un altre títol que es mostri en 5/6 de la pantalla, i la mida de la lletra sigui de 0.2f
        //TODO Exercici 1 - c) Afegiu una animació sobre aquest segon títol la qual apliqui un efecte de parpelleig sobre el mateix (veure l'efecte al vídeo).
        Container<Label> text = getAnimatedText(
                "Tap Screen to Start",
                new Vector2(
                        Settings.GAME_WIDTH / 2,
                        Settings.GAME_HEIGHT / 1.2f
                ),
                Actions.repeat(
                        RepeatAction.FOREVER,
                        Actions.sequence(
                                Actions.fadeOut(0.5f),
                                Actions.fadeIn(0.5f)
                        )
                )
        );
        text.getActor().setFontScale(0.2f);
        stage.addActor(text);

        //add the spacecraft
        Image spacecraft = new Image(AssetManager.spacecraft);
        float y = Settings.GAME_HEIGHT / 2 + gameTitle.getActor().getHeight();
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

    private Stage getStageWithOrthographicCamera() {
        //create the orthographic camera
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);

        //create the viewport
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        //return the stage
        return new Stage(viewport);
    }

    private Container<Label> getAnimatedText(String text, Vector2 position, Action action) {
        Label.LabelStyle textStyle = new Label.LabelStyle(AssetManager.font, null);
        Label textLabel = new Label(text, textStyle);

        Container<Label> container = new Container<Label>(textLabel);
        container.setTransform(true);
        container.center();
        container.setPosition(position.x, position.y);

        container.addAction(action);

        return container;
    }
}
