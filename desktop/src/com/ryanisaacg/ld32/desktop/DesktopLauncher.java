package com.ryanisaacg.ld32.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ryanisaacg.ld33.LD33Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Escape From Science";
		config.width = 1280;
		config.height = 720;
		config.vSyncEnabled = true;
		config.addIcon("icon.png", FileType.Internal);
		config.addIcon("icon32.png", FileType.Internal);
		config.addIcon("icon16.png", FileType.Internal);
		new LwjglApplication(new LD33Game(), config);
	}
}
