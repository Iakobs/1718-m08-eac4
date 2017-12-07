package com.jacobibanez.helpers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jacobibanez.objects.Spacecraft;
import com.jacobibanez.screens.GameScreen;

/**
 * Class responsible for managing the user's input.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class InputHandler implements InputProcessor {

    private GameScreen screen;
    private Stage stage;
    private Spacecraft spacecraft;

    private int previousY = 0;
    //variable for tracking the pointer which is moving the spacecraft
    private int movementPointer;

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
                this.screen.setCurrentState(GameScreen.GameState.RUNNING);
                break;
            case RUNNING:
                Vector2 stageCoord = this.stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                Actor actorHit = this.stage.hit(stageCoord.x, stageCoord.y, true);

                //check if an actor has been touched
                if (actorHit != null) {
                    //TODO Exercici 2 - si fem click al botó de pausa, es canvia l'estat
                    if (actorHit.getName().equals(GameScreen.PAUSE_BUTTON_NAME)) {
                        this.screen.pauseScreen();
                    } else if (actorHit.getName().equals(GameScreen.FIRE_BUTTON_NAME)) {
                        //TODO Exercici 3 - si fem click al botó de disparar, es dispara
                        this.screen.fire();
                        if (this.movementPointer == pointer) {
                            this.movementPointer = -1;
                        }
                    }
                } else {
                    this.movementPointer = pointer;
                    this.previousY = screenY;
                }

                break;
            case GAME_OVER:
                this.screen.reset();
                break;
            case PAUSE:
                //TODO Exercici 2 - quan fem click a qualsevol lloc de la pantalla, continua el joc
                this.screen.resumeScreen();
                break;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //only responds to touch events if you are in RUNNING state
        if (this.screen.getCurrentState().equals(GameScreen.GameState.RUNNING)) {
            if (this.movementPointer == pointer) {
                this.spacecraft.goStraight();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //only responds to touch events if you are in RUNNING state
        if (this.screen.getCurrentState().equals(GameScreen.GameState.RUNNING)) {
            if (this.movementPointer == pointer) {
                if (Math.abs(this.previousY - screenY) > 2) {
                    if (this.previousY < screenY) {
                        this.spacecraft.goDown();
                    } else {
                        this.spacecraft.goUp();
                    }

                    this.previousY = screenY;
                    return true;
                }
            }
        }
        return false;
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
