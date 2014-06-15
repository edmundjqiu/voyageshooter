
package com.badlogic.gradletest;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //Dat hardcode is bad. rework this later
        config.height = 768;
        config.width = 1024;
		new LwjglApplication(new HelloApp(), config);
	}
}
