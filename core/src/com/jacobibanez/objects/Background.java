package com.jacobibanez.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A class representing a background.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class Background extends Scrollable {

    private TextureRegion texture;

    public Background(float x, float y, float width, float height, float velocity,
                      TextureRegion texture) {
        super(x, y, width, height, velocity, Direction.RIGHT_TO_LEFT);
        this.texture = texture;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.disableBlending();
        batch.draw(
                this.getTexture(),
                this.getX(),
                this.getY(),
                this.getWidth(),
                this.getHeight()
        );
        batch.enableBlending();
    }
}
