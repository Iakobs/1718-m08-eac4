package com.jacobibanez.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jacobibanez.SpaceRace;
import com.jacobibanez.utils.Settings;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        //add minimum configuration for the desktop application
        config.title = "SpaceRace";
        config.width = Settings.GAME_WIDTH * 2;
        config.height = Settings.GAME_HEIGHT * 2;

        new LwjglApplication(new SpaceRace(), config);
    }
}
