package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.ChooseMapScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MainMenuScreen;

import java.awt.Dimension;
import java.awt.Toolkit;

public class TDGame extends Game {
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	public SpriteBatch batch;

	
	@Override
	public void create () {
		batch=new SpriteBatch();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		SCREEN_HEIGHT=screenSize.height;
		SCREEN_WIDTH=screenSize.width;
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {

	}
}
