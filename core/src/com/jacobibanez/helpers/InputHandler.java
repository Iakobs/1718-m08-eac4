package com.jacobibanez.helpers;

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

    private GameScreen screen;
    private Stage stage;
    private Spacecraft spacecraft;

    private int previousY = 0;

    public InputHandler(GameScreen screen) {
        this.screen = screen;
        this.stage = screen.getStage();
        this.spacecraft = screen.getSpacecraft();
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
        switch (screen.getCurrentState()) {
            case READY:
                screen.setCurrentState(GameScreen.GameState.RUNNING);
                break;
            case RUNNING:
                this.previousY = screenY;

                Vector2 stageCoord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                Actor actorHit = stage.hit(stageCoord.x, stageCoord.y, true);

                if (actorHit != null) {
                    //TODO Exercici 2 - si fem click al botó de pausa, es canvia l'estat
                    if (actorHit.getName().equals(GameScreen.PAUSE_BUTTON_NAME)) {
                        screen.pauseScreen();
                    } else if (actorHit.getName().equals(GameScreen.FIRE_BUTTON_NAME)) {

                    }
                }
                break;
            case GAME_OVER:
                screen.reset();
                break;
            case PAUSE:
                //TODO Exercici 2 - quan fem click a qualsevol lloc de la pantalla, continua el joc
                screen.resumeScreen();
                break;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (screen.getCurrentState().equals(GameScreen.GameState.RUNNING)) {
            spacecraft.goStraight();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (screen.getCurrentState().equals(GameScreen.GameState.RUNNING)) {
            if (Math.abs(previousY - screenY) > 2) {
                if (previousY < screenY) {
                    spacecraft.goDown();
                } else {
                    spacecraft.goUp();
                }
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
