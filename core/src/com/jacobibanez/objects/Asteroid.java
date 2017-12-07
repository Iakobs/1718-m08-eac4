package com.jacobibanez.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.jacobibanez.helpers.AssetManager;
import com.jacobibanez.utils.Methods;
import com.jacobibanez.utils.Settings;

/**
 * A class representing an asteroid.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class Asteroid extends Scrollable {

    private static final int ORIGINAL_SIZE = 34;

    private final Circle collisionCircle;

    public Asteroid(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity, Direction.RIGHT_TO_LEFT);

        this.collisionCircle = new Circle();

        //set the origin of the texture and add the rotation animation
        this.setOrigin(width / 2 + 1, height / 2);
        this.addAction(Actions.repeat(
                RepeatAction.FOREVER,
                Actions.rotateBy(-90f, 0.2f))
        );
    }

    public Circle getCollisionCircle() {
        return collisionCircle;
    }

    /**
     * Returns a new random size for an asteroid, between a globally defined threshold.
     *
     * @return The random size.
     */
    public static float getNewRandomSize() {
        return Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * ORIGINAL_SIZE;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.collisionCircle.set(
                this.getX() + this.getWidth() / 2.0f,
                this.getY() + this.getWidth() / 2.0f,
                this.getWidth() / 2.0f
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(
                AssetManager.asteroid,
                this.getX(),
                this.getY(),
                this.getOriginX(),
                this.getOriginY(),
                this.getWidth(),
                this.getHeight(),
                this.getScaleX(),
                this.getScaleY(),
                this.getRotation()
        );
    }

    /**
     * Checks if the asteroid has collided with an spacecraft.
     *
     * @param spacecraft The spacecraft to check.
     * @return True if a collision occurs. False otherwise.
     */
    public boolean collides(Spacecraft spacecraft) {
        if (this.position.x <= spacecraft.getX() + spacecraft.getWidth()) {
            return (Intersector.overlaps(this.collisionCircle, spacecraft.getCollisionRectangle()));
        }
        return false;
    }
}
