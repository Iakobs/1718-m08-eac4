package com.jacobibanez.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.jacobibanez.helpers.AssetManager;
import com.jacobibanez.utils.Settings;

/**
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class Spacecraft extends Actor {

    public static final int SPACECRAFT_STRAIGHT = 0;
    public static final int SPACECRAFT_UP = 1;
    public static final int SPACECRAFT_DOWN = 2;

    private Vector2 position;
    private int width, height;
    private int direction;
    private Rectangle collisionRectangle;

    public Spacecraft(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        this.position = new Vector2(x, y);

        direction = SPACECRAFT_STRAIGHT;

        collisionRectangle = new Rectangle();

        setBounds();
        setTouchable(Touchable.enabled);
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

        setBounds();
    }

    private void setBounds() {
        setBounds(position.x, position.y, width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
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
}
