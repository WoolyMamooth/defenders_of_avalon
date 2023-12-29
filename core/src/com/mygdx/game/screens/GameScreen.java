package com.mygdx.game.screens;

import static com.mygdx.game.TDGame.*;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.IngameMenu;
import com.mygdx.game.maps.TDMap;

public class GameScreen implements Screen {
    TDGame game;
    TDMap map; //the map that was chosen in the menu
    Texture backgroundTexture; //background of the map
    IngameMenu menu; // contains pause button, player HP etc
    boolean paused=false;
    float gametime=0; //keeps track of how much time has passed since the start of the game

    /**
     * GameScreen is where we display a map and the player gets to play through it.
     * @param game
     * @param map
     */

    public GameScreen(TDGame game, TDMap map){
        this.game=game;
        this.map=map;
        this.menu=new IngameMenu(this.game, this);
    }

    @Override
    public void show() {
        this.backgroundTexture = this.map.getBackgroundTexture();
    }

    @Override
    public void render(float delta) {
        //clear the screen
        ScreenUtils.clear(0, 0, 0, 0);
        int gameState = 0;//1 if player lost, 2 if player won, 0 otherwise

        //if the game is paused then we skip updating the map
        if(!paused) {
            //temporary for checking coordinates:
            //if(Gdx.input.isTouched()){System.out.println(Gdx.input.getX()+" "+Gdx.input.getY());}

            //update gametime
            gametime += delta;

            //update the map
            gameState = map.update(delta);

            //checks if a new enemy should be spawned and spawns them if they should
            map.trySpawn(delta);

        }
        //drawing begins here
        game.batch.begin();
        //draw the background
        game.batch.draw(backgroundTexture, SCREEN_BOT_LEFT.x(), SCREEN_BOT_LEFT.y());

        //draws all enemies and towers
        map.draw(game.batch);

        //draw ingame menu
        menu.draw(game.batch);

        game.batch.end();
        //drawing ends here

        if (gameState==1) { // exit if player lost the game
            this.dispose();
            game.setScreen(new LostScreen(game));
        }else if(gameState==2){ // player won the game
            this.dispose();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    //clear memory
    @Override
    public void dispose() {
        game.batch.dispose();
        game.batch=new SpriteBatch();
        map.dispose();
        backgroundTexture.dispose();
        System.gc();
    }

    /**
     * @return [0]=playerHP, [1]=playerGold
     */
    public int[] getPlayerData(){
        return new int[]{map.getPlayerHP(), map.getPlayerGold()};
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        paused=!paused;
    }
    public boolean isPaused(){
        return paused;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }
}
