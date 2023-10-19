package com.mygdx.game.screens;

import static com.mygdx.game.TDGame.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.TDMap;

public class GameScreen implements Screen {

    TDMap map;

    // TODO
    //display map, implement core gameloop etc.
    Texture backgroundTexture;
    Texture redSquare=new Texture("red_square.jpg");
    TDGame game;

    public GameScreen(TDGame game, TDMap map){
        this.game=game;
        this.map=map;
        System.out.println(SCREEN_HEIGHT+" "+SCREEN_WIDTH);
    }

    @Override
    public void show() {
        this.backgroundTexture = this.map.getBackgroundTexture();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        //if(Gdx.input.isTouched()){System.out.println(Gdx.input.getX()+" "+Gdx.input.getY());}
        game.batch.begin();
        game.batch.draw(redSquare,SCREEN_CENTER.x()-redSquare.getWidth()/2,SCREEN_CENTER.y());
        game.batch.draw(redSquare,SCREEN_TOP_LEFT.x(),SCREEN_TOP_LEFT.y()-redSquare.getHeight());
        game.batch.end();
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
    }
}
