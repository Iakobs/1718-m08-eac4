package com.jacobibanez;

import com.badlogic.gdx.Game;
import com.jacobibanez.helpers.AssetManager;
import com.jacobibanez.screens.SplashScreen;

/**
 * Entry point of the application.
 *
 * @author <a href="mailto:jacobibanez@jacobibanez.com">Jacob Ibáñez Sánchez</a>.
 */
public class SpaceRace extends Game {

    @Override
    public void create() {
        AssetManager.load();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();
    }
}
