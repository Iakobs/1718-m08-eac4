package com.jacobibanez;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        //disable accelerometer and compass
        config.useAccelerometer = false;
        config.useCompass = false;

        //enable immersive mode and wake lock
        config.useImmersiveMode = true;
        config.useWakelock = true;

        initialize(new SpaceRace(), config);
    }
}
