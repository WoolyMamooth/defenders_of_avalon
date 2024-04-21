package com.wooly.avalon.screens;

import static com.wooly.avalon.TDGame.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.IngameMenu;
import com.wooly.avalon.maps.MapLoader;
import com.wooly.avalon.maps.TDMap;

public class GameScreen implements Screen {
    TDGame game;
    TDMap map; //the map that was chosen in the menu
    int mapID;
    Texture backgroundTexture; //background of the map
    Texture frontgroundTexture;
    IngameMenu menu; // contains pause button, player HP etc
    public static boolean paused=false;
    float gametime=0; //keeps track of how much time has passed since the start of the com.wooly.avalon

    /**
     * GameScreen is where we display a map and the player gets to play through it.
     * @param game
     * @param map
     */

    public GameScreen(TDGame game, TDMap map, int mapID){
        this.game=game;
        this.map=map;
        this.mapID=mapID;
        this.menu=new IngameMenu(this.game, this);
    }

    @Override
    public void show() {
        this.backgroundTexture = this.map.getBackgroundTexture();
        this.frontgroundTexture=this.map.getFrontgroundTexture();

        TDGame.musicHandler.playMusic("ingame1");
    }

    @Override
    public void render(float delta) {
        //clear the screen
        ScreenUtils.clear(0, 0, 0, 0);
        int gameState = 0;//1 if player lost, 2 if player won, 0 otherwise

        //temporary for checking coordinates:
        //if(Gdx.input.justTouched()){System.out.println("CLICKED AT "+trueInput());}

        //if the game is paused then we skip updating the map
        if(!paused) {

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

        //draws enemies, towers, hero
        map.draw(game.batch);

        //draw the frontground
        game.batch.draw(frontgroundTexture, SCREEN_BOT_LEFT.x(), SCREEN_BOT_LEFT.y());

        //draw heroes menu above all
        map.drawHeroMenu(game.batch);

        //draw ingame menu
        menu.draw(game.batch);
        if (menu.menuButtonPressed){
            gameState=3;
        }
        game.batch.end();
        //drawing ends here

        //check state of the game
        if (gameState==1) { // exit if player lost the game
            paused=false;
            this.dispose();
            game.setScreen(new LostScreen(game));
        }else if(gameState==2){ // player won the game
            //we give the player an amount of stardust based on the difficulty they played at
            paused=false;
            int difficultyExtraStardust=MapLoader.GAME_DIFFICULTY*10;
            player.gainStardust(80+difficultyExtraStardust);
            this.dispose();
            game.setScreen(new MainMenuScreen(game));
        }else if(gameState==3){ // player exited to menu
            paused=false;
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
        menu.dispose();
        System.gc();
    }

    /**
     * @return [0]=playerHP, [1]=playerGold
     */
    public int[] getPlayerData(){
        return new int[]{map.getPlayerHP(), map.getPlayerGold()};
    }
    public void reloadMap(){
        MapLoader mapLoader=new MapLoader(game);
        map.dispose();
        map=mapLoader.getMap(mapID);
        backgroundTexture=map.getBackgroundTexture();
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
