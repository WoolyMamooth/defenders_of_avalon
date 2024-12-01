package com.wooly.avalon.screens;

import static com.wooly.avalon.TDGame.place;

import com.badlogic.gdx.graphics.Color;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.buttons.Clickable;
import com.wooly.avalon.screens.buttons.DifficultyButton;
import com.wooly.avalon.screens.buttons.ExitButton;
import com.wooly.avalon.screens.buttons.LoadScreenButton;
import com.wooly.avalon.screens.other.HeroMenuScaleSlider;
import com.wooly.avalon.screens.other.TextBubble;
import com.wooly.avalon.screens.other.VolumeSlider;

public class MainMenuScreen extends MenuScreen {
    Clickable playButton,exitButton,shopButton,settingsButton;
    public MainMenuScreen(TDGame game){
        super(game);
        System.out.println("LOADING MainMenuScreen");
        int menuPos=2;
        Coordinate pos=centerButton(menuPos++);
        this.playButton=new LoadScreenButton(this.game,TDGame.fetchTexture("buttons/play"),
                TDGame.fetchTexture("buttons/play"),
                place(pos.x()-60, pos.y()),
                "chooseMap");

        pos=centerButton(menuPos++);
        this.shopButton=new LoadScreenButton(this.game,TDGame.fetchTexture("buttons/store"),
                TDGame.fetchTexture("buttons/store"),
                place(pos.x(), pos.y()),
                "shop");

        pos=centerButton(menuPos++);
        this.settingsButton=new LoadScreenButton(this.game,TDGame.fetchTexture("buttons/settings"),
                TDGame.fetchTexture("buttons/settings"),
                place(pos.x()-30, pos.y()),
                "settings");

        pos=centerButton(menuPos++);
        this.exitButton=new ExitButton(TDGame.fetchTexture("buttons/exit_active"),
                TDGame.fetchTexture("buttons/exit"),
                place(pos.x(), pos.y()));

        TDGame.musicHandler.playMusic("menu1");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();
        renderButton(playButton);
        renderButton(exitButton);
        renderButton(shopButton);
        renderButton(settingsButton);
        game.batch.end();
    }

    @Override
    public void dispose() {
        playButton.dispose();
        exitButton.dispose();
        shopButton.dispose();
    }
}
