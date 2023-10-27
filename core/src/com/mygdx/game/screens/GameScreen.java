package com.mygdx.game.screens;

import static com.mygdx.game.TDGame.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.TDMap;
import com.mygdx.game.units.enemies.DrawableUnit;

public class GameScreen implements Screen {
    TDGame game;
    TDMap map; //the map that was chosen in the menu
    Texture backgroundTexture; //background of the map

    public GameScreen(TDGame game, TDMap map){
        this.game=game;
        this.map=map;
    }

    @Override
    public void show() {
        this.backgroundTexture = this.map.getBackgroundTexture();
        map.spawnNextEnemy(); //temporarily here
    }

    @Override
    public void render(float delta) {
        //clear the screen
        ScreenUtils.clear(1, 1, 1, 1);

        //call move method of each enemy
        boolean lostGame=false;
        if(map.updateEnemies()){
            //updateEnemies returns ture if the playerHP reached 0, therefore they lost
            lostGame=true;
        }

        //temporary for checking coordinates
        if(Gdx.input.isTouched()){System.out.println(Gdx.input.getX()+" "+Gdx.input.getY());}

        //drawing begins here
        game.batch.begin();
        //draw the background
        game.batch.draw(backgroundTexture,SCREEN_BOT_LEFT.x(),SCREEN_BOT_LEFT.y());

        //draw each enemy
        for (DrawableUnit enemy:map.getAllEnemyTextures()) {
            game.batch.draw(enemy.texture,enemy.position.x(),enemy.position.y());
        }
        game.batch.end();

        if(lostGame){
            this.dispose();
            game.setScreen(new LostScreen(game));
        }
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

    @Override
    public void dispose() {
        game.batch.dispose();
        game.batch=new SpriteBatch();
        map.dispose();
        backgroundTexture.dispose();
        System.gc();
    }
}
