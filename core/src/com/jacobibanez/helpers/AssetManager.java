package com.jacobibanez.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class AssetManager {

    public static Texture sheet;

    public static TextureRegion spacecraft, spacecraftDown, spacecraftUp, background, pauseButton;

    public static TextureRegion[] asteroid;
    public static Animation<TextureRegion> asteroidAnimation;

    public static TextureRegion[] explosion;
    public static Animation<TextureRegion> explosionAnimation;

    public static Sound explosionSound;
    public static Music bgMusic;

    public static BitmapFont font;

    public static void load() {
        sheet = new Texture(Gdx.files.internal("unnamed.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        spacecraft = new TextureRegion(sheet, 0, 36, 36, 15);
        spacecraft.flip(false, true);

        spacecraftUp = new TextureRegion(sheet, 36, 36, 36, 15);
        spacecraftUp.flip(false, true);

        spacecraftDown = new TextureRegion(sheet, 72, 36, 36, 15);
        spacecraftDown.flip(false, true);

        pauseButton = new TextureRegion(sheet, 0, 0, 36, 36);
        pauseButton.flip(false, true);

        asteroid = new TextureRegion[16];
        for (int i = 0; i < asteroid.length; i++) {
            asteroid[i] = new TextureRegion(sheet, i * 34, 51, 34, 34);
            asteroid[i].flip(false, true);
        }

        asteroidAnimation = new Animation<TextureRegion>(0.05f, asteroid);
        asteroidAnimation.setPlayMode(Animation.PlayMode.LOOP_REVERSED);

        explosion = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                explosion[index] = new TextureRegion(sheet, j * 64, 36 + i * 64 + 49, 64, 64);
                explosion[index].flip(false, true);
                index++;
            }
        }

        explosionAnimation = new Animation<TextureRegion>(0.04f, explosion);
        explosionAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        background = new TextureRegion(sheet, 0, 213, 480, 135);
        background.flip(false, true);

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Afterburner.ogg"));
        bgMusic.setVolume(0.2f);
        bgMusic.setLooping(true);

        font = new BitmapFont(Gdx.files.internal("fonts/space.fnt"), true);
        font.getData().setScale(0.4f);
    }

    public static void dispose() {
        sheet.dispose();
        explosionSound.dispose();
        bgMusic.dispose();
    }
}
