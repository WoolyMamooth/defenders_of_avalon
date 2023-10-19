package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.TDMap;

public class GameScreen implements Screen {
    TDMap map;

    // TODO
    //display map, implement core gameloop etc.
    //everything below is just a demo
    Texture texture;
    float posX=0;
    float posY=0;
    public static final float SPEED=400;
    TDGame game;

    public GameScreen(TDGame game, TDMap map){
        this.game=game;
        this.map=map;
    }

    @Override
    public void show() {
        texture = new Texture("red_square.jpg");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);


        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            posY+=SPEED*Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            posY-=SPEED*Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            posX-=SPEED*Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            posX+=SPEED*Gdx.graphics.getDeltaTime();
        }

        game.batch.begin();
        game.batch.draw(texture,posX,posY);
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
