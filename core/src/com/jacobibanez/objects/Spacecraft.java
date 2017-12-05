package com.jacobibanez.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.jacobibanez.helpers.AssetManager;
import com.jacobibanez.utils.Settings;

/**
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class Spacecraft extends Actor {

    public static final int SPACECRAFT_STRAIGHT = 0;
    public static final int SPACECRAFT_UP = 1;
    public static final int SPACECRAFT_DOWN = 2;

    private static final Action pauseAction = Actions.repeat(
            RepeatAction.FOREVER,
            Actions.sequence(
                    Actions.fadeOut(0.5f),
                    Actions.fadeIn(0.5f)
            )
    );

    private Vector2 position;
    private int width, height;
    private int direction;
    private Rectangle collisionRectangle;

    private boolean paused;

    public Spacecraft(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        this.position = new Vector2(x, y);

        direction = SPACECRAFT_STRAIGHT;

        collisionRectangle = new Rectangle();
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

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    @Override
    public float getHeight() {
        return height;
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

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!paused) {
            float velocityIncrement = Settings.SPACECRAFT_VELOCITY * delta;
            switch (direction) {
                case SPACECRAFT_UP:
                    if (this.position.y - velocityIncrement >= 0) {
                        this.position.y -= velocityIncrement;
                    }
                    break;
                case SPACECRAFT_DOWN:
                    if (this.position.y + height + velocityIncrement <= Settings.GAME_HEIGHT) {
                        this.position.y += velocityIncrement;
                    }
                    break;
                case SPACECRAFT_STRAIGHT:
                    break;
            }

            collisionRectangle.set(position.x, position.y + 3, width, 10);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a);
//        if (paused) {
//            batch.setColor(color.r, color.g, color.b, color.a);
//        } else {
//            batch.setColor(color.r, color.g, color.b, 1f);
//        }

        batch.draw(getSpacecraftTexture(), position.x, position.y, width, height);
    }

    public TextureRegion getSpacecraftTexture() {
        switch (direction) {
            case SPACECRAFT_STRAIGHT:
                return AssetManager.spacecraft;
            case SPACECRAFT_UP:
                return AssetManager.spacecraftUp;
            case SPACECRAFT_DOWN:
                return AssetManager.spacecraftDown;
        }
        return null;
    }

    public void reset() {
        position.x = Settings.SPACECRAFT_START_X;
        position.y = Settings.SPACECRAFT_START_Y;
        direction = SPACECRAFT_STRAIGHT;
        collisionRectangle = new Rectangle();
    }

    public void pause() {
        this.paused = true;
        this.addAction(pauseAction);
    }

    public void resume() {
        this.paused = false;
        this.addAction(Actions.alpha(1f));
        this.removeAction(pauseAction);
    }
}
