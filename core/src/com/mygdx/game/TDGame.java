package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

	//we use .png for every texture, this is here in case i decide to change it
	public static final String TEXTURE_EXTENSION=".png";

	//the player variable stores all data of the player that will be saved
	public static final Player player=new Player();
	
	@Override
	public void create () {
		batch=new SpriteBatch();

		SCREEN_HEIGHT = Gdx.graphics.getHeight();
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_CENTER = new Coordinate(SCREEN_WIDTH/2f,SCREEN_HEIGHT/2f);
		SCREEN_TOP_LEFT = new Coordinate(0f,SCREEN_HEIGHT*1f);
		SCREEN_TOP_RIGHT = new Coordinate(SCREEN_WIDTH*1f,SCREEN_HEIGHT*1f);
		SCREEN_BOT_LEFT = new Coordinate(0f,0f);
		SCREEN_BOT_RIGHT = new Coordinate(SCREEN_WIDTH*1f,0f);

		System.out.println("PLAYER LOADED: "+player);

		//sets the first screen, which is the main menu
		this.setScreen(new MainMenuScreen(this));

		//for quicker testing so we don't have to go through the menu:
		//this.setScreen(new GameScreen(this,new TDMap(0)));
	}

	/**
	 * Returns a texture based on the path and filename.
	 * @param path "path/to/file" (no file extension)
	 */
	public static Texture fetchTexture(String path){
		return new Texture(path+TEXTURE_EXTENSION);
	}
}
