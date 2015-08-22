package com.ryanisaacg.ld32.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ryanisaacg.ld33.LD33Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Ludum Dare 33";
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new LD33Game(), config);
	}
}
