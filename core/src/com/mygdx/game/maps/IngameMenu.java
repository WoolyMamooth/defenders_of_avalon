package com.mygdx.game.maps;

import static com.mygdx.game.TDGame.SCREEN_HEIGHT;
import static com.mygdx.game.TDGame.SCREEN_WIDTH;
import static com.mygdx.game.TDGame.fetchTexture;
import static com.mygdx.game.TDGame.place;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.TDGame;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.buttons.Clickable;
import com.mygdx.game.screens.buttons.LoadScreenButton;
import com.mygdx.game.screens.buttons.PickMapButton;

public class IngameMenu {
    TDGame game;
    GameScreen screen;
    PlayerDataContainer playerData;
    PauseButton pauseButton;
    PauseMenu pauseMenu;
    Texture forBackgrounds=fetchTexture("white_square");

    /**
     * Displays playerHP, playerGold, also has a PauseButton and Hero ability buttons.
     * @param game Necessary for reloading and exiting.
     * @param screen This screens pause() method will be called when the PauseButton is clicked
     */
    public IngameMenu(TDGame game,GameScreen screen){
        this.game=game;
        this.screen=screen;
        this.playerData=new PlayerDataContainer(place(0,SCREEN_HEIGHT));
        this.pauseButton=new PauseButton(place(SCREEN_WIDTH,SCREEN_HEIGHT), TDGame.fetchTexture("buttons/resume_game"),TDGame.fetchTexture("buttons/pause_game"));
        this.pauseMenu=new PauseMenu();
    }
    public void draw(SpriteBatch batch){
        playerData.draw(batch);
        pauseButton.drawCheckClick(batch);
        if(screen.isPaused()) pauseMenu.draw(batch);
    }
    private class PlayerDataContainer{
        int playerHP;
        int playerGold;
        Coordinate position;
        BitmapFont font=new BitmapFont();

        public PlayerDataContainer(Coordinate position) {
            this.position=position;
        }

        public void draw(SpriteBatch batch) {
            updateData();
            font.draw(batch,"HP: "+playerHP, position.x()+20, position.y()-20);
            font.draw(batch,"Gold: "+playerGold,position.x()+20, position.y()-40);
        }
        private void updateData(){
            int[] playerData = screen.getPlayerData();
            playerHP=playerData[0];
            playerGold=playerData[1];
        }
    }

    private class PauseButton extends Clickable{
        public PauseButton(Coordinate position, Texture activeTexture, Texture inactiveTexture) {
            super(position, activeTexture, inactiveTexture);
            Coordinate offset=new Coordinate(inactiveTexture.getWidth()+10,inactiveTexture.getHeight()+10);
            this.position=position.subtract(offset);
        }
        @Override
        public void drawCheckClick(SpriteBatch batch){
            batch.draw((screen.isPaused() ? activeTexture : inactiveTexture), position.x(), position.y(), this.width, this.height);
            if(isActive() && Gdx.input.justTouched()){
                onClick();
            }
        }
        @Override
        public void onClick() {
            screen.pause();
        }
    }

    private class PauseMenu{
        PickMapButton retryButton;
        LoadScreenButton menuButton;

        public PauseMenu() {
            retryButton=new PickMapButton(game,fetchTexture("buttons/pick_map_active"),fetchTexture("buttons/pick_map"),place(200,200),0);//FIXME change button positions to use coordinates
            menuButton=new LoadScreenButton(game,fetchTexture("buttons/menu_active"),fetchTexture("buttons/menu"),place(200,300),"mainMenu");
        }

        public void draw(SpriteBatch batch){
            retryButton.drawCheckClick(batch);
            menuButton.drawCheckClick(batch);
        }
    }

}
