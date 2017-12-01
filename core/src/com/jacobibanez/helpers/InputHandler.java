package com.jacobibanez.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jacobibanez.objects.Spacecraft;
import com.jacobibanez.screens.GameScreen;

/**
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class InputHandler implements InputProcessor {

    private Spacecraft spacecraft;
    private GameScreen screen;
    private int previousY = 0;

    private Vector2 stageCoord;
    private Stage stage;

    public InputHandler(GameScreen screen) {
        this.screen = screen;
        this.spacecraft = screen.getSpacecraft();

        this.stage = screen.getStage();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.previousY = screenY;

        stageCoord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
        Actor actorHit = stage.hit(stageCoord.x, stageCoord.y, true);
        if (actorHit != null) {
            Gdx.app.log("HIT", actorHit.getName());
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        spacecraft.goStraight();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (Math.abs(previousY - screenY) > 2) {
            if (previousY < screenY) {
                spacecraft.goDown();
            } else {
                spacecraft.goUp();
            }
        }
        previousY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
