package com.jacobibanez.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class Scrollable extends Actor {

    protected Vector2 position;
    protected float velocity;
    protected float width, height;
    protected boolean leftOfScreen;

    public Scrollable(float x, float y, float width, float height, float velocity) {
        this.position = new Vector2(x, y);
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        this.leftOfScreen = false;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getVelocity() {
        return velocity;
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

    public boolean isLeftOfScreen() {
        return leftOfScreen;
    }

    public float getTailX() {
        return position.x + width;
    }

    public void reset(float newX) {
        this.position.x = newX;
        this.leftOfScreen = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        position.x += velocity * delta;
        if (getTailX() < 0) {
            leftOfScreen = true;
        }
    }
}
