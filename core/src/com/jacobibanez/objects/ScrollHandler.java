package com.jacobibanez.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.jacobibanez.helpers.AssetManager;
import com.jacobibanez.utils.Settings;

import java.util.Random;

/**
 * A class responsible for managing all scrollable actors in the scene.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class ScrollHandler extends Group {

    private static final Random RANDOM = new Random();
    //TODO Exercici 3 - màxim de 4 asteroides
    private static final int MAX_ASTEROIDS = 4;
    private static final float ASTEROID_CREATION_INTERVAL = 1.0f;

    private Background bg, bgBack;
    private float lastAsteroidCreationTime;
    private DelayedRemovalArray<Asteroid> asteroids;
    private DelayedRemovalArray<Laser> lasers;

    public ScrollHandler() {
        initBackground();

        this.asteroids = new DelayedRemovalArray<Asteroid>();
        this.lasers = new DelayedRemovalArray<Laser>();
        this.lastAsteroidCreationTime = ASTEROID_CREATION_INTERVAL;
    }

    public DelayedRemovalArray<Asteroid> getAsteroids() {
        return asteroids;
    }

    private void initBackground() {
        //create two equal backgrounds, one just behind the other, so they will endlessly scroll
        this.bg = new Background(
                0,
                0,
                Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT,
                Settings.BG_SPEED,
                AssetManager.background
        );
        this.bgBack = new Background(
                bg.getTailX(),
                0,
                Settings.GAME_WIDTH * 2,
                Settings.GAME_HEIGHT,
                Settings.BG_SPEED,
                AssetManager.background
        );

        //add the backgrounds to the stage
        addActor(bg);
        addActor(bgBack);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.lastAsteroidCreationTime += delta;

        createAsteroid();
        checkLaserAndAsteroidCollision();

        //remove lost lasers
        for (Asteroid asteroid : this.asteroids) {
            if (asteroid.isOutOfScreen()) {
                removeActor(asteroid);
                this.asteroids.removeValue(asteroid, true);
            }
        }

        //remove lost asteroids
        for (Laser laser : this.lasers) {
            if (laser.isOutOfScreen()) {
                removeActor(laser);
                this.lasers.removeValue(laser, true);
            }
        }

        //scroll the background
        if (this.bg.isOutOfScreen()) {
            this.bg.reset(this.bgBack.getTailX());
        } else if (this.bgBack.isOutOfScreen()) {
            this.bgBack.reset(this.bg.getTailX());
        }
    }

    public boolean collides(Spacecraft spacecraft) {
        for (Asteroid asteroid : this.asteroids) {
            if (asteroid.collides(spacecraft)) {
                return true;
            }
        }
        return false;
    }

    public void reset() {
        this.lastAsteroidCreationTime = ASTEROID_CREATION_INTERVAL;

        for (Asteroid asteroid : this.asteroids) {
            removeActor(asteroid);
        }
        this.asteroids.clear();

        for (Laser laser : this.lasers) {
            removeActor(laser);
        }
        this.lasers.clear();
    }

    public void fire(Laser laser) {
        addActor(laser);
        this.lasers.add(laser);
    }

    private void checkLaserAndAsteroidCollision() {
        //TODO Exercici 3 - Si el làser colisiona amb un asteroide, aquest explota
        for (Laser laser : this.lasers) {
            for (Asteroid asteroid : this.asteroids) {
                if (laser.collides(asteroid)) {
                    //TODO Exercici 3 - El làser desapareix
                    removeActor(laser);
                    this.lasers.removeValue(laser, true);
                    //TODO Exercici 3 - L'asteroide desapareix
                    removeActor(asteroid);
                    this.asteroids.removeValue(asteroid, true);
                    //TODO Exercici 3 - Una explosió s'anima al lloc on l'asteroide ha explotat
                    addActor(new Explosion(asteroid.getX(), asteroid.getY(), asteroid.getWidth(), asteroid.getHeight()));
                }
            }
        }
    }

    private void createAsteroid() {
        //TODO Exercici 3 - Creació independent d'asteroides cada segon i un màxim de 4
        if (this.lastAsteroidCreationTime > ASTEROID_CREATION_INTERVAL
                && this.asteroids.size <= MAX_ASTEROIDS) {
            this.lastAsteroidCreationTime = 0.0f;

            float newSize = Asteroid.getNewRandomSize();
            Asteroid asteroid = new Asteroid(
                    Settings.GAME_WIDTH,
                    RANDOM.nextInt(Settings.GAME_HEIGHT - (int) newSize),
                    newSize,
                    newSize,
                    Settings.ASTEROID_SPEED
            );

            this.asteroids.add(asteroid);
            addActor(asteroid);
        }
    }
}
