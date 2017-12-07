package com.jacobibanez.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jacobibanez.helpers.AssetManager;

/**
 * A class representing an explosion.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class Explosion extends Actor {

    private Vector2 position;
    private float width, height;
    private float explosionTime;
    private Animation<TextureRegion> explosionAnimation;

    public Explosion(float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        this.position = new Vector2(x, y);
        this.explosionTime = 0.0f;
        this.explosionAnimation = AssetManager.explosionAnimation;

        //reproduce explosion sound on explosion creation
        AssetManager.explosionSound.play();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.explosionTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(
                this.explosionAnimation.getKeyFrame(this.explosionTime, false),
                this.position.x,
                this.position.y,
                this.width,
                this.height
        );

        //if the animation has finished, remove the actor
        if (this.explosionAnimation.isAnimationFinished(this.explosionTime)) {
            getParent().removeActor(this);
        }
    }
}
