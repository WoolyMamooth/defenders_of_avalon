package com.mygdx.game.screens;

import static com.mygdx.game.TDGame.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.TDMap;
import com.mygdx.game.units.DrawableUnit;

public class GameScreen implements Screen {
    TDGame game;
    TDMap map; //the map that was chosen in the menu
    Texture backgroundTexture; //background of the map

    float gametime=0; //keeps track of how much time has passed since the start of the game

    /**
     * GameScreen is where we display a map and the player gets to play through it.
     * @param game
     * @param map
     */

    public GameScreen(TDGame game, TDMap map){
        this.game=game;
        this.map=map;
    }

    @Override
    public void show() {
        this.backgroundTexture = this.map.getBackgroundTexture();
        //map.spawnNextEnemy(); //temporarily here
    }

    @Override
    public void render(float delta) {
        //clear the screen
        ScreenUtils.clear(1, 1, 1, 1);
        //temporary for checking coordinates
        //if(Gdx.input.isTouched()){System.out.println(Gdx.input.getX()+" "+Gdx.input.getY());}

        //update gametime
        gametime+=delta;

        //call move method of each enemy
        boolean lostGame=false;
        if(map.updateEnemies()){
            //updateEnemies returns ture if the playerHP reached 0, therefore they lost
            lostGame=true;
        }

        //checks if a new enemy should be spawned and spawns them if they should
        map.trySpawn(delta);

        //drawing begins here
        game.batch.begin();

        //draw the background
        game.batch.draw(backgroundTexture,SCREEN_BOT_LEFT.x(),SCREEN_BOT_LEFT.y());

        //draws all enemies and towers
        map.draw(game.batch);

        game.batch.end();
        //drawing ends here

        if(lostGame){ // exit if player lost the game
            this.dispose();
            game.setScreen(new LostScreen(game));
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

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
