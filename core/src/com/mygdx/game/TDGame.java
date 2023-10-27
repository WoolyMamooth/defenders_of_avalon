package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.screens.MainMenuScreen;

public class TDGame extends Game {

	//these are coordinates for easy setup of maps
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	public static Coordinate SCREEN_CENTER;
	public static Coordinate SCREEN_TOP_LEFT;
	public static Coordinate SCREEN_TOP_RIGHT;
	public static Coordinate SCREEN_BOT_LEFT;
	public static Coordinate SCREEN_BOT_RIGHT;

	//call batch.draw to draw on the screen efficiently
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch=new SpriteBatch();

		SCREEN_HEIGHT=1000;
		SCREEN_WIDTH=1920;
		SCREEN_CENTER = new Coordinate(SCREEN_WIDTH/2f,SCREEN_HEIGHT/2f);
		SCREEN_TOP_LEFT = new Coordinate(0f,SCREEN_HEIGHT*1f);
		SCREEN_TOP_RIGHT = new Coordinate(SCREEN_WIDTH*1f,SCREEN_HEIGHT*1f);
		SCREEN_BOT_LEFT = new Coordinate(0f,0f);
		SCREEN_BOT_RIGHT = new Coordinate(SCREEN_WIDTH*1f,0f);
		//sets the first screen, which is the main menu
		this.setScreen(new MainMenuScreen(this));

		//for quicker testing so we don't have to go through the menu:
		//this.setScreen(new GameScreen(this,new TDMap(0)));
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
