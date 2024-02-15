package com.wooly.avalon;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Defenders of Avalon");
		config.setMaximized(true);
		config.setWindowIcon("red_square.jpg");
		//config.setWindowedMode(1920,1024);
		new Lwjgl3Application(new TDGame(), config);
	}
}
