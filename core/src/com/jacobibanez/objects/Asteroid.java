package com.jacobibanez.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.jacobibanez.helpers.AssetManager;
import com.jacobibanez.utils.Methods;
import com.jacobibanez.utils.Settings;

import java.util.Random;

/**
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class Asteroid extends Scrollable {

    private static final int ORIGINAL_SIZE = 34;

    private float runTime;
    private Circle collisionCircle;

    public Asteroid(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);
        this.runTime = Methods.randomFloat(0, 1);

        this.collisionCircle = new Circle();
    }

    public static float getNewRandomSize() {
        return Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * ORIGINAL_SIZE;
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);

        width = height = getNewRandomSize();
        position.y = new Random().nextInt(Settings.GAME_HEIGHT - (int) getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.asteroidAnimation.getKeyFrame(runTime), position.x, position.y, width, height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.runTime += delta;

        collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);
    }

    public boolean collides(Spacecraft spacecraft) {
        if (position.x <= spacecraft.getX() + spacecraft.getWidth()) {
            return (Intersector.overlaps(collisionCircle, spacecraft.getCollisionRectangle()));
        }
        return false;
    }
}
