package com.wooly.avalon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.MainMenuScreen;

import java.util.Random;

public class TDGame extends Game {

	//these are coordinates for easy setup of maps
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	public static Coordinate SCREEN_CENTER;
	public static Coordinate SCREEN_TOP_LEFT;
	public static Coordinate SCREEN_TOP_RIGHT;
	public static Coordinate SCREEN_BOT_LEFT;
	public static Coordinate SCREEN_BOT_RIGHT;
	public static float widthOffset=0f,heightOffset=0f;

	//call batch.draw to draw on the screen efficiently
	public SpriteBatch batch;

	//we use .png for every texture, this is here in case i decide to change it
	public static final String TEXTURE_EXTENSION=".png";

	//the player variable stores all data of the player that will be saved
	public static Player player;
	public static class CustomRandom{
		static Random r=new Random();

		/**
		 * The built in Random functions crash the game on android because they apparently don't take parameters,
		 * so this class should be used for generating random numbers.
		 */
		public CustomRandom(){}
		public static int nextInt(int min, int max){
			return r.nextInt()*(max-min)+min;
		}
		public static float nextFloat(float min, float max){
			return r.nextFloat()*(max-min)+min;
		}
		public static boolean nextBoolean(){
			return r.nextBoolean();
		}
	}
	@Override
	public void create () {
		batch=new SpriteBatch();

		int deviceHeight = Gdx.graphics.getHeight();
		int deviceWidth = Gdx.graphics.getWidth();

		SCREEN_HEIGHT = 1024; //the game is optimized for these dimensions
		SCREEN_WIDTH = 1920;

		if(deviceWidth>SCREEN_WIDTH){
			widthOffset=deviceWidth-SCREEN_WIDTH;
			widthOffset/=2;
			//SCREEN_WIDTH+=widthOffset;
		}
		if(deviceHeight>SCREEN_HEIGHT){
			heightOffset=deviceHeight-SCREEN_HEIGHT;
			heightOffset/=2;
		}

		SCREEN_CENTER = place(SCREEN_WIDTH/2f,SCREEN_HEIGHT/2f);
		SCREEN_TOP_LEFT = place(0f,SCREEN_HEIGHT*1f);
		SCREEN_TOP_RIGHT = place(SCREEN_WIDTH*1f,SCREEN_HEIGHT*1f);
		SCREEN_BOT_LEFT = place(0f,0f);
		SCREEN_BOT_RIGHT = place(SCREEN_WIDTH*1f,0f);

		System.out.println(
				"SCREEN DATA:\ndeviceH: "+deviceHeight+" deviceW: "+deviceWidth+
				"\nSCREEN_H: "+SCREEN_HEIGHT+" SCREEN_W: "+SCREEN_WIDTH+
				"\nwidthOffset: "+widthOffset+
				"\nheightOffset: "+heightOffset+
				"\nBOT_L: "+SCREEN_BOT_LEFT);

		player=new Player();
		System.out.println("PLAYER LOADED: "+player);

		//sets the first screen, which is the main menu
		this.setScreen(new MainMenuScreen(this));

		//for quicker testing so we don't have to go through the menu:
		//this.setScreen(new GameScreen(this,new TDMap(0)));
	}

	@Override
	public void dispose() {
		System.out.println("SAVING PLAYER DATA");
		player.saveData();
		System.out.println("EXITING");
		super.dispose();
	}

	/**
	 * Returns a texture based on the path and filename.
	 * @param path "path/to/file" (no file extension)
	 */
	public static Texture fetchTexture(String path){
		return new Texture(path+TEXTURE_EXTENSION);
	}

	/**
	 * Returns a Coordinate which is correctly placed relative to screen size.
	 */
	public static Coordinate place(float x,float y){
		return new Coordinate(x+widthOffset, y+heightOffset);
	}

	/**
	 * Returns a Coordinate which contains the position that was last clicked by the user, corrected for screen size/window size differences.
	 * @return
	 */
	public static Coordinate trueInput(){
		return new Coordinate(Gdx.input.getX(),Gdx.input.getY()-2*heightOffset);
	}

	/**
	 * Returns a RobotoRegular font with the given size. Use .setColor() on it to change color.
	 * @param size
	 */
	public static BitmapFont fetchFont(int size){
		FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto/RobotoRegular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size=size;
		return generator.generateFont(parameter);
	}
}
