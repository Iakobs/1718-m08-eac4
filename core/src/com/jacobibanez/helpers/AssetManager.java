package com.jacobibanez.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Class responsible for load all game assets.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class AssetManager {

    private static TextureAtlas atlas;

    public static TextureRegion spacecraft, spacecraftDown, spacecraftUp, background, pauseButton,
            fireButton, laser, asteroid;

    public static Animation<TextureRegion> explosionAnimation;

    public static Sound explosionSound, fireSound;
    public static Music bgMusic;

    public static BitmapFont font;

    public static void load() {
        atlas = new TextureAtlas(Gdx.files.internal("atlas.atlas"));

        loadBackground();
        loadButtons();
        loadSpacecraft();
        loadLaser();
        loadAsteroid();
        loadExplosion();
        loadSounds();
        loadMusic();
        loadFonts();
    }

    public static void dispose() {
        atlas.dispose();
        explosionSound.dispose();
        fireSound.dispose();
        bgMusic.dispose();
    }

    private static void loadBackground() {
        background = atlas.findRegion("background");
        background.flip(false, true);
    }

    private static void loadButtons() {
        //TODO Exercici 2 - icona del botó de pausa
        pauseButton = atlas.findRegion("pausebutton");
        pauseButton.flip(false, true);

        //TODO Exercici 3 - icona del botó de disparar
        fireButton = atlas.findRegion("firebutton");
        fireButton.flip(false, true);
    }

    private static void loadSpacecraft() {
        spacecraft = atlas.findRegion("spaceshipStraight");
        spacecraft.flip(false, true);

        spacecraftUp = atlas.findRegion("spaceshipUp");
        spacecraftUp.flip(false, true);

        spacecraftDown = atlas.findRegion("spaceshipDown");
        spacecraftDown.flip(false, true);
    }

    private static void loadLaser() {
        //TODO Exercici 3 - imatge del làser
        laser = atlas.findRegion("fireball");
        laser.flip(false, true);
    }

    private static void loadAsteroid() {
        asteroid = atlas.findRegion("shell");
        asteroid.flip(false, true);
    }

    private static void loadExplosion() {
        TextureRegion[][] tmp = atlas.findRegion("explosion").split(64, 64);

        TextureRegion[] explosion = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                explosion[index++] = tmp[i][j];
            }
        }

        explosionAnimation = new Animation<TextureRegion>(0.04f, explosion);
        explosionAnimation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    private static void loadSounds() {
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        fireSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.ogg"));
    }

    private static void loadMusic() {
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Afterburner.ogg"));
        bgMusic.setLooping(true);
    }

    private static void loadFonts() {
        font = new BitmapFont(Gdx.files.internal("fonts/space.fnt"), true);
        font.getData().setScale(0.4f);
    }
}
