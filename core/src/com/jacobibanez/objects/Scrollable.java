package com.jacobibanez.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jacobibanez.utils.Settings;

/**
 * Abstract class for objects that are scrollable.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public abstract class Scrollable extends Actor {

    /**
     * An enumerated class indicating the direction of the scroll action.
     * <p>
     * The default direction is {@link Direction#LEFT_TO_RIGHT}.
     */
    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    protected Vector2 position;
    protected float velocity;
    protected float width, height;
    protected boolean outOfScreen;
    protected Direction direction = Direction.LEFT_TO_RIGHT;

    public Scrollable(float x, float y, float width, float height, float velocity) {
        this.position = new Vector2(x, y);
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        this.outOfScreen = false;
    }

    public Scrollable(float x, float y, float width, float height, float velocity, Direction direction) {
        velocity *= -1.0f;

        this.position = new Vector2(x, y);
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        this.outOfScreen = false;
        this.direction = direction;
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

    public boolean isOutOfScreen() {
        return outOfScreen;
    }

    public float getTailX() {
        if (this.direction.equals(Direction.LEFT_TO_RIGHT)) {
            return getX();
        } else {
            return getX() + getWidth();
        }
    }

    public void reset(float newX) {
        this.position.x = newX;
        this.outOfScreen = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        this.position.x += this.velocity * delta;
        switch (this.direction) {
            case LEFT_TO_RIGHT:
                this.outOfScreen = getTailX() > Settings.GAME_WIDTH;
                break;
            case RIGHT_TO_LEFT:
                this.outOfScreen = getTailX() < 0;
                break;
        }
    }
}
