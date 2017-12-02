package com.jacobibanez.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.jacobibanez.utils.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class ScrollHandler extends Group {

    private static final Random RANDOM = new Random();

    private Background bg, bgBack;
    private int numAsteroids;
    private List<Asteroid> asteroids;

    public ScrollHandler() {
        initBackground();
        initAsteroids();
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    private void initBackground() {
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bgBack = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        addActor(bg);
        addActor(bgBack);
    }

    private void initAsteroids() {
        numAsteroids = 3;
        asteroids = new ArrayList<Asteroid>();

        float newSize = Asteroid.getNewRandomSize();

        Asteroid asteroid = new Asteroid(
                Settings.GAME_WIDTH,
                RANDOM.nextInt(Settings.GAME_HEIGHT - (int) newSize),
                newSize,
                newSize,
                Settings.ASTEROID_SPEED
        );
        asteroids.add(asteroid);
        addActor(asteroid);

        for (int i = 0; i < numAsteroids; i++) {
            newSize = Asteroid.getNewRandomSize();
            asteroid = new Asteroid(
                    asteroids.get(asteroids.size() - 1).getTailX() + Settings.ASTEROID_GAP,
                    RANDOM.nextInt(Settings.GAME_HEIGHT - (int) newSize),
                    newSize,
                    newSize,
                    Settings.ASTEROID_SPEED
            );
            asteroids.add(asteroid);
            addActor(asteroid);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (bg.isLeftOfScreen()) {
            bg.reset(bgBack.getTailX());
        } else if (bgBack.isLeftOfScreen()) {
            bgBack.reset(bg.getTailX());
        }

        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid asteroid = asteroids.get(i);
            if (asteroid.isLeftOfScreen()) {
                if (i == 0) {
                    asteroid.reset(asteroids.get(asteroids.size() - 1).getTailX() + Settings.ASTEROID_GAP);
                } else {
                    asteroid.reset(asteroids.get(i - 1).getTailX() + Settings.ASTEROID_GAP);
                }
            }
        }
    }

    public boolean collides(Spacecraft spacecraft) {
        for (Asteroid asteroid : asteroids) {
            if (asteroid.collides(spacecraft)) {
                return true;
            }
        }
        return false;
    }

    public void reset() {
        asteroids.get(0).reset(Settings.GAME_WIDTH);
        for (int i = 1; i < asteroids.size(); i++) {
            asteroids.get(i).reset(asteroids.get(i - 1).getTailX() + Settings.ASTEROID_GAP);
        }
    }
}
