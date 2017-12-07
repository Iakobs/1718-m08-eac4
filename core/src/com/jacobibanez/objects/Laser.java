package com.jacobibanez.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.jacobibanez.helpers.AssetManager;

/**
 * A class representing a laser beam.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class Laser extends Scrollable {

    private final Circle collisionCircle;

    public Laser(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        this.collisionCircle = new Circle();

        //set the origin of the texture and add the rotation animation
        setOrigin(width / 2 + 1, height / 2);
        addAction(Actions.repeat(
                RepeatAction.FOREVER,
                Actions.rotateBy(90f, 0.05f))
        );

        //reproduce fire sound when the laser is created
        AssetManager.fireSound.play();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.collisionCircle.set(
                getX() + getWidth() / 2.0f,
                getY() + getWidth() / 2.0f,
                getWidth() / 2.0f
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(
                AssetManager.laser,
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                getWidth(),
                getHeight(),
                getScaleX(),
                getScaleY(),
                getRotation()
        );
    }

    /**
     * Checks if the laser has collided with an asteroid.
     *
     * @param asteroid The asteroid to check.
     * @return True if a collision occurs. False otherwise.
     */
    public boolean collides(Asteroid asteroid) {
        return Intersector.overlaps(this.collisionCircle, asteroid.getCollisionCircle());
    }
}
