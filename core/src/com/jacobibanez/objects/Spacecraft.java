package com.jacobibanez.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.jacobibanez.helpers.AssetManager;
import com.jacobibanez.utils.Settings;

/**
 * A class representing a spaceship.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class Spacecraft extends Actor {

    private static final int SPACECRAFT_STRAIGHT = 0;
    private static final int SPACECRAFT_UP = 1;
    private static final int SPACECRAFT_DOWN = 2;

    private final Rectangle collisionRectangle;

    private Vector2 position;
    private int width, height;
    private int direction;

    private boolean paused;

    public Spacecraft(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        this.position = new Vector2(x, y);

        this.direction = SPACECRAFT_STRAIGHT;

        this.collisionRectangle = new Rectangle();
    }

    @Override
    public float getX() {
        return position.x;
    }

    @Override
    public float getY() {
        return position.y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public void goUp() {
        this.direction = SPACECRAFT_UP;
    }

    public void goDown() {
        this.direction = SPACECRAFT_DOWN;
    }

    public void goStraight() {
        this.direction = SPACECRAFT_STRAIGHT;
    }

    public void reset() {
        this.position.x = Settings.SPACECRAFT_START_X;
        this.position.y = Settings.SPACECRAFT_START_Y;
        this.direction = SPACECRAFT_STRAIGHT;
    }

    public void pause() {
        this.paused = true;
        //TODO Exercici 2 - acció de parpalleig
        addAction(Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                        Actions.fadeOut(0.5f),
                        Actions.fadeIn(0.5f)
                )
        ));
    }

    public void resume() {
        this.paused = false;
        clearActions();
        addAction(Actions.alpha(1f));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        //TODO Exercici 2 - si està pausada, no fa res (tot s'atura)
        if (!this.paused) {
            float velocityIncrement = Settings.SPACECRAFT_VELOCITY * delta;
            switch (this.direction) {
                case SPACECRAFT_UP:
                    //maximum negative Y is top of screen
                    if (this.getY() - velocityIncrement >= 0) {
                        this.position.y -= velocityIncrement;
                    }
                    break;
                case SPACECRAFT_DOWN:
                    //maximum positive Y is end of screen
                    if (this.getY() + this.getHeight() + velocityIncrement <= Settings.GAME_HEIGHT) {
                        this.position.y += velocityIncrement;
                    }
                    break;
                case SPACECRAFT_STRAIGHT:
                    break;
            }

            this.collisionRectangle.set(getX(), getY() + 3, getWidth(), 10);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        //TODO Exercici 2 - apliquem l'alpha a la batch per l'acció de parpalleig
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a);

        batch.draw(getSpacecraftTexture(), getX(), getY(), getWidth(), getHeight());
    }

    private TextureRegion getSpacecraftTexture() {
        switch (this.direction) {
            case SPACECRAFT_STRAIGHT:
                return AssetManager.spacecraft;
            case SPACECRAFT_UP:
                return AssetManager.spacecraftUp;
            case SPACECRAFT_DOWN:
                return AssetManager.spacecraftDown;
        }
        return null;
    }
}
